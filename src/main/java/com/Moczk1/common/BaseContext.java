package com.Moczk1.common;

import org.apache.ibatis.javassist.convert.TransformReadField;

/**
 *
 */
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setId(Long id) {
        threadLocal.set(id);
    }

    public static long getId() {
        return threadLocal.get();
    }

}
