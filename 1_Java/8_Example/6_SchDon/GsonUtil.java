package com.eland.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {

    private GsonUtil() {}

    public static <E> E convertToObj(String json, Class<E> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <E> E convertToObj(Object object, Class<E> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), clazz);
    }

    public static <E> List<E> convertToList(Object object, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), type);
    }

}
