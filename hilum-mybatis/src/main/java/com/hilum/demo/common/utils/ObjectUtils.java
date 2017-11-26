package com.hilum.demo.common.utils;

import java.util.Collection;
import java.util.Map;


public abstract class ObjectUtils {

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isBlank(Collection<?> collection) {
        return (collection == null || collection.size() == 0);
    }

    public static boolean isBlank(Map<?, ?> map) {
        return (map == null || map.size() == 0);
    }

    public static boolean isBlank(Object[] objs) {
        return (objs == null || objs.length == 0);
    }

    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isBlank((String) obj);
        }
        if (obj instanceof Collection<?>) {
            return isBlank((Collection<?>) obj);
        }
        if (obj instanceof Map<?, ?>) {
            return isBlank((Map<?, ?>) obj);
        }
        if (obj instanceof Object[]) {
            return isBlank((Object[]) obj);
        }
        return false;
    }

    public static boolean isNotBlank(String str) {
        return (str != null && str.trim().length() > 0);
    }

    public static boolean isNotBlank(Collection<?> collection) {
        return (collection != null && collection.size() > 0);
    }

    public static boolean isNotBlank(Map<?, ?> map) {
        return (map != null && map.size() > 0);
    }

    public static boolean isNotBlank(Object[] objs) {
        return (objs != null && objs.length > 0);
    }

    public static boolean isNotBlank(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return isNotBlank((String) obj);
        }
        if (obj instanceof Collection<?>) {
            return isNotBlank((Collection<?>) obj);
        }
        if (obj instanceof Map<?, ?>) {
            return isBlank((Map<?, ?>) obj);
        }
        if (obj instanceof Object[]) {
            return isNotBlank((Object[]) obj);
        }
        return false;
    }
}
