package com.mu.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;


public class JsonUtil {


    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        // 忽略在json字符串中存在, 但在java对象中不存在对应属性的情况, 防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Object 序列化
     *
     * @param obj obj
     * @return java.lang.String
     * @Author mujiangkui
     * @Date 22:21 2021/1/24
     */
    public static <T> String objToStr(T obj) {
        if (null == obj) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.warn("objToStr error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(str) || null == clazz) {
                return null;
            }
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            LOGGER.warn("strToObj error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(str) || null == typeReference) {
            return null;
        }

        try {
            return (String.class.equals(typeReference.getType()) ? (T) str : objectMapper.readValue(str,
                    typeReference));
        } catch (Exception e) {
            LOGGER.error("strToObj error", e);
            return null;
        }
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }
}