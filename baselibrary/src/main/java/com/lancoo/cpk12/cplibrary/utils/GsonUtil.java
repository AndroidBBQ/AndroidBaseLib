package com.lancoo.cpk12.cplibrary.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Gson工具类
 */
public class GsonUtil {

    private static Gson mGson = null;

    static {
        //mGson = new Gson();
        mGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                // 过滤掉类名包含Set的类，可以排除Set集合等
                return false;//clazz.getName().contains("Set");
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                // 过滤掉包含@NotToJson的域
                NotToJson notToJson = field.getAnnotation(NotToJson.class);
                if (notToJson != null) {
                    return true;
                }
                return false;
            }
        }).create();
    }

    /**
     * 用于Gson进行filed排除
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NotToJson {
    }

    /**
     * 将一个对象转化成json数据
     *
     * @param obj 与json数据相对应的java类对象
     * @return
     */
    public static String objectToJson(Object obj) {
        return mGson.toJson(obj);
    }

    /**
     * 将json数据转化成一个对象
     *
     * @param json     json数据
     * @param classOfT 与json数据相对应的java类
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    /**
     * 将json数据转化成一个对象集合
     *
     * @param json     json数据
     * @param classOfT 与json数据相对应的java类
     * @return
     */
    public static <T> ArrayList<T> jsonToList(String json, Class<T> classOfT) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjs = mGson.fromJson(json, type);

        ArrayList<T> listOfT = new ArrayList<T>();
        for (JsonObject jsonObj : jsonObjs)
            listOfT.add(mGson.fromJson(jsonObj, classOfT));

        return listOfT;
    }

}
