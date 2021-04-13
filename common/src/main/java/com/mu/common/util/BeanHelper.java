package com.mu.common.util;

import org.springframework.util.ClassUtils;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * @description bean类型判断
 * @Author mujiangkui
 * @Date 22:18 2021/1/24
 */
public class BeanHelper {

    /**
     * 判断是不是简单数据类型，包括基本数据类型，枚举型，CharSequence,Number,URL,URI,Date,Locale,Class
     *
     * @param clazz clazz
     * @return boolean
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz)
                || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz
                || URL.class == clazz || Locale.class == clazz || Class.class == clazz);
    }
}
