/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.application.authentication.framework.config.model.graph.graaljs.remote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.graalvm.polyglot.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Consolidated property path navigator for the remote JS engine.
 * Handles navigation through nested objects using "::" separated path segments.
 *
 * Supports traversal of:
 * - ProxyObject (getMember)
 * - ProxyArray (get by numeric index)
 * - Map (get by key)
 * - POJO (reflection getter fallback)
 *
 * Special segments:
 * - "__keys__" triggers member key enumeration (edge case #12)
 * - Numeric segments use ProxyArray.get() not getMember() (edge case #6)
 * - OSGi classloader fallback via reflection for ProxyArray (edge case #7)
 *
 * This class extracts the common navigation logic from:
 * - RemoteJsEngine.getContextProperty()
 * - RemoteJsEngine.getHostRefProperty()
 * - RemoteJsEngine.getProxyObjectProperty()
 * - RemoteJsEngine.setContextProperty()
 * - RemoteJsEngine.setHostRefProperty()
 * - RemoteJsEngine.reconstructFromContextProxy()
 */
public final class PropertyPathNavigator {

    private static final Log log = LogFactory.getLog(PropertyPathNavigator.class);

    private PropertyPathNavigator() {
        // Utility class
    }

    /**
     * Check if a string consists of only ASCII digit characters.
     * Avoids compiling a new regex on every call (unlike String.matches("\\d+")).
     *
     * @param s The string to check
     * @return true if non-empty and all characters are digits
     */
    static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Navigate a property path starting from a root object.
     *
     * @param pathParts  The path segments (already split by "::")
     * @param startIndex Index to start navigating from (0 for full path, 1 to skip refId)
     * @param root       The root object to navigate from
     * @return The resolved value, or null if the path cannot be resolved
     */
    public static Object navigatePath(String[] pathParts, int startIndex, Object root) {
        Object current = root;

        for (int i = startIndex; i < pathParts.length; i++) {
            String part = pathParts[i];
            if (current == null) {
                return null;
            }

            // Edge case #12: __keys__ triggers member key enumeration
            if (RemoteEngineConstants.KEYS_PROPERTY.equals(part)) {
                return getMemberKeys(current);
            }

            current = navigateSingleStep(current, part);
        }

        return current;
    }

    /**
     * Navigate to the parent object and set the final property value.
     *
     * @param pathParts  The path segments (already split by "::")
     * @param startIndex Index to start navigating from
     * @param root       The root object to navigate from
     * @param value      The value to set at the end of the path
     * @return true if the property was successfully set, false otherwise
     */
    public static boolean setProperty(String[] pathParts, int startIndex, Object root, Object value) {
        if (pathParts.length == 0) {
            return false;
        }

        // Navigate to the parent (all segments except the last)
        Object parent = root;
        for (int i = startIndex; i < pathParts.length - 1; i++) {
            String part = pathParts[i];
            if (parent == null) {
                log.warn("[PropertyPathNavigator] Null encountered at path segment: " + part);
                return false;
            }
            parent = navigateSingleStepForSet(parent, part);
        }

        if (parent == null) {
            log.warn("[PropertyPathNavigator] Parent is null, cannot set final property");
            return false;
        }

        // Set the final property
        String finalPart = pathParts[pathParts.length - 1];
        return setFinalProperty(parent, finalPart, value);
    }

    /**
     * Navigate a single step in the property path (for GET operations).
     * Handles ProxyArray, ProxyObject, Map, and reflection getter.
     */
    private static Object navigateSingleStep(Object current, String part) {
        // Edge case #6: Numeric segments on ProxyArray use get(index), not getMember()
        if (isNumeric(part) && current instanceof org.graalvm.polyglot.proxy.ProxyArray) {
            int index = Integer.parseInt(part);
            Object result = ((org.graalvm.polyglot.proxy.ProxyArray) current).get(index);
            if (log.isDebugEnabled()) {
                log.debug("[PropertyPathNavigator] Accessed array index " + index + " -> " +
                        (result != null ? result.getClass().getSimpleName() : "null"));
            }
            return result;
        }

        // Numeric on ProxyObject - try getMember with string key
        if (isNumeric(part) && current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            return ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
        }

        // Standard member access on ProxyObject
        if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            Object result = ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
            if (log.isDebugEnabled()) {
                log.debug("[PropertyPathNavigator] Accessed member '" + part + "' on proxy -> " +
                        (result != null ? result.getClass().getSimpleName() : "null"));
            }
            return result;
        }

        // Map access
        if (current instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) current;
            return map.get(part);
        }

        // Reflection getter as last resort
        return invokeGetter(current, part);
    }

    /**
     * Navigate a single step for SET operations.
     * Includes the OSGi classloader reflection fallback (edge case #7)
     * for ProxyArray where instanceof may not match across bundles.
     */
    private static Object navigateSingleStepForSet(Object current, String part) {
        if (isNumeric(part)) {
            int index = Integer.parseInt(part);

            // Try ProxyArray.get first
            if (current instanceof org.graalvm.polyglot.proxy.ProxyArray) {
                return ((org.graalvm.polyglot.proxy.ProxyArray) current).get(index);
            }

            // Edge case #7: OSGi classloader fallback - instanceof fails across bundles,
            // so try reflection on get(long) directly
            try {
                java.lang.reflect.Method getMethod = current.getClass().getMethod("get", long.class);
                Object result = getMethod.invoke(current, (long) index);
                if (log.isDebugEnabled()) {
                    log.debug("[PropertyPathNavigator] Used reflection get(" + index +
                            ") on " + current.getClass().getSimpleName());
                }
                return result;
            } catch (NoSuchMethodException nsme) {
                // Not a ProxyArray-like object, try ProxyObject.getMember
                if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
                    return ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
                }
                log.warn("[PropertyPathNavigator] Cannot navigate numeric segment: " + part +
                        " on type: " + current.getClass().getName());
                return null;
            } catch (Exception e) {
                log.warn("[PropertyPathNavigator] Reflection get() failed for segment: " + part, e);
                return null;
            }
        }

        // Non-numeric segment
        if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            return ((org.graalvm.polyglot.proxy.ProxyObject) current).getMember(part);
        }

        if (current instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) current;
            return map.get(part);
        }

        // Reflection getter fallback
        return invokeGetter(current, part);
    }

    /**
     * Set the final property value on the parent object.
     * Tries ProxyObject.putMember, reflection putMember, Map.put, and reflection setter.
     */
    private static boolean setFinalProperty(Object parent, String finalPart, Object value) {
        // Try ProxyObject.putMember with GraalVM Value wrapping
        if (parent instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            try {
                Value wrappedValue = Value.asValue(value);
                ((org.graalvm.polyglot.proxy.ProxyObject) parent).putMember(finalPart, wrappedValue);
                if (log.isDebugEnabled()) {
                    log.debug("[PropertyPathNavigator] Set property via putMember: " + finalPart);
                }
                return true;
            } catch (Exception e) {
                log.warn("[PropertyPathNavigator] Direct putMember failed: " + e.getMessage());
            }
        } else {
            // Edge case #7: Reflection fallback for OSGi classloader issues
            try {
                java.lang.reflect.Method putMethod = parent.getClass().getMethod(
                        "putMember", String.class, Value.class);
                Value wrappedValue = Value.asValue(value);
                putMethod.invoke(parent, finalPart, wrappedValue);
                if (log.isDebugEnabled()) {
                    log.debug("[PropertyPathNavigator] Set property via reflection putMember: " + finalPart);
                }
                return true;
            } catch (NoSuchMethodException nsme) {
                // No putMember method — fall through
            } catch (Exception e) {
                log.warn("[PropertyPathNavigator] Reflection putMember failed: " + e.getMessage());
            }
        }

        // Try Map.put
        if (parent instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) parent;
            map.put(finalPart, value);
            if (log.isDebugEnabled()) {
                log.debug("[PropertyPathNavigator] Set property in map: " + finalPart);
            }
            return true;
        }

        // Try reflection setter
        try {
            String setterName = "set" + Character.toUpperCase(finalPart.charAt(0)) + finalPart.substring(1);
            java.lang.reflect.Method[] methods = parent.getClass().getMethods();
            for (java.lang.reflect.Method method : methods) {
                if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                    method.invoke(parent, value);
                    if (log.isDebugEnabled()) {
                        log.debug("[PropertyPathNavigator] Set property via setter: " + finalPart);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            log.debug("[PropertyPathNavigator] Setter failed for: " + finalPart, e);
        }

        log.warn("[PropertyPathNavigator] Could not set property: " + finalPart);
        return false;
    }

    /**
     * Get member keys from an object.
     * Handles ProxyObject (getMemberKeys) and POJO (bean introspection).
     */
    public static Object getMemberKeys(Object current) {
        if (current instanceof org.graalvm.polyglot.proxy.ProxyObject) {
            Object keys = ((org.graalvm.polyglot.proxy.ProxyObject) current).getMemberKeys();
            if (log.isDebugEnabled()) {
                log.debug("[PropertyPathNavigator] __keys__ on proxy -> " +
                        (keys != null ? keys.getClass().getSimpleName() : "null"));
            }
            return keys;
        }

        // For POJOs, return list of property names via bean introspection
        if (current != null) {
            try {
                List<String> keys = new ArrayList<>();
                for (java.lang.reflect.Method m : current.getClass().getMethods()) {
                    if (m.getParameterCount() == 0 && m.getName().startsWith("get") &&
                            !"getClass".equals(m.getName())) {
                        String prop = Character.toLowerCase(m.getName().charAt(3)) +
                                m.getName().substring(4);
                        keys.add(prop);
                    }
                }
                if (!keys.isEmpty()) {
                    return keys;
                }
            } catch (Exception e) {
                log.debug("[PropertyPathNavigator] Error getting keys for object: " + e.getMessage());
            }
        }

        return null;
    }

    /**
     * Invoke a getter method via reflection.
     *
     * @param target   The object to invoke the getter on
     * @param property The property name (e.g., "username" invokes "getUsername()")
     * @return The getter result, or null if not found
     */
    private static Object invokeGetter(Object target, String property) {
        try {
            String getterName = "get" + Character.toUpperCase(property.charAt(0)) + property.substring(1);
            java.lang.reflect.Method getter = target.getClass().getMethod(getterName);
            Object result = getter.invoke(target);
            if (log.isDebugEnabled()) {
                log.debug("[PropertyPathNavigator] Accessed property '" + property + "' via " + getterName +
                        " -> " + (result != null ? result.getClass().getSimpleName() : "null"));
            }
            return result;
        } catch (NoSuchMethodException e) {
            log.debug("[PropertyPathNavigator] No getter for property: " + property +
                    " on class: " + target.getClass().getName());
            return null;
        } catch (IllegalAccessException e) {
            log.debug("[PropertyPathNavigator] Getter not accessible for property: " + property +
                    " on class: " + target.getClass().getName());
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
            log.error("[PropertyPathNavigator] Getter threw exception for property: " + property +
                    " on class: " + target.getClass().getName(), e.getCause());
            return null;
        }
    }
}
