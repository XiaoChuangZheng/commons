package com.yishan.biz.pack.jsonutil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class JsonUtils {

    private static final ObjectMapper JSON_OBJECT_MAPPER;

    static {
        SimpleModule simpleModule = new SimpleModule("Jackson Module", Version.unknownVersion());
        JSON_OBJECT_MAPPER = new ObjectMapper();
        JSON_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON_OBJECT_MAPPER.registerModule(simpleModule);
        JSON_OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }


    public static ObjectMapper getObjectMapper() {
        return JSON_OBJECT_MAPPER;
    }

    /**
     * 序列化对象为 JSON 字符串
     *
     * @param obj 对象
     */
    public static String toJSON(Object obj) {
        try {
            return JSON_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 反序列化 JSON 字符串为指定类型的对象
     *
     * @param <T>   泛型
     * @param json  　json串
     * @param clazz
     */
    public static <T> T parseJSON(String json, Class<T> clazz) {
        try {
            return JSON_OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 判断给定的字符串是否符合json规范,以"{"开头且以"}"结尾,或者以"["开头且以"]"结尾
     *
     * @param json {@link String} 需要判断的字符串
     * @return {@link Boolean} 目标串为null或者空串则返回false, 不符合json规范则返回false
     */
    public static boolean isJonsStyle(String json) {
        if (null == json || json.trim().length() == 0) {
            return false;
        }
        json = json.trim();
        return json.startsWith("[") && json.endsWith("]") || json.startsWith("{") && json.endsWith("}");
    }

    public static Object clone(Object obj) {
        return parseJSON(toJSON(obj), obj.getClass());
    }

    public static <T> T convertJson2ObjWithTypeRef(String json, TypeReference<T> valueTypeRef)
        throws IOException {
        //noinspection unchecked
        return (T) JSON_OBJECT_MAPPER.readValue(json, valueTypeRef);
    }

    public static JsonNode json2Node(String json) throws IOException {
        return JSON_OBJECT_MAPPER.readValue(json, JsonNode.class);
    }
}