package com.qdaily.frame.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.qdaily.frame.log.QLog;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by yuelinghui on 17/8/9.
 */

public class JsonUtil {

    public static Gson gson = new Gson();

    public static <T> T Json2ObjectThrowExecption(java.lang.String json, java.lang.Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(json, clazz);
    }

    public static <T> T Json2ObjectThrowExecption(java.lang.String json, Type type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }

    public static <T> T Json2Object(java.lang.String json, java.lang.Class<T> clazz) {
        T ob = null;
        try {
            ob = gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            QLog.e("QDJsonUtil", "Json2Object", e);
        }

        if (ob == null) {
            try {
                ob = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ob;
    }

    public static <T> T Json2Object(JsonReader in, java.lang.Class<T> clazz) {
        T ob = null;
        try {
            ob = gson.fromJson(in, clazz);
        } catch (JsonSyntaxException e) {
            QLog.e("QDJsonUtil", "Json2Object", e);
        }
        return ob;
    }

    /**
     * 功能描述：把JSON数据转换成普通字符串列表
     *
     * @param jsonData JSON数据
     * @return
     * @throws Exception
     */
    public static List<String> getStringList(String jsonData) throws Exception {
        List<String> result = gson.fromJson(jsonData, new TypeToken<List<String>>() {
        }.getType());

        return result;
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     *
     * @param object java对象
     * @return
     */
    public static String getJsonString(Object object) {
        return gson.toJson(object);
    }

    public static String toJSONString(java.lang.Object object) {
        return gson.toJson(object);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return
     * @throws Exception
     */
    public static <T> List<T> getBeanList(String jsonData, Class<T> clazz)
            throws Exception {
        List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
        }.getType());

        return result;
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的java对象列表
     *
     * @param jsonData JSON数据
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getBeanMapList(String jsonData)
            throws Exception {
        List<Map<String, Object>> result = gson.fromJson(jsonData, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return result;
    }

    public static Map<String, Object> getBeanMap(String jsonData) throws JsonSyntaxException {
        return gson.fromJson(jsonData, new TypeToken<Map<String, Object>>() {
        }.getType());
    }
}
