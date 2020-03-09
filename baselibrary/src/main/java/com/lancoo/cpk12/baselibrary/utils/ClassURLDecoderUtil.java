package com.lancoo.cpk12.baselibrary.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类url编码工具
 */
public class ClassURLDecoderUtil {

    public static void decode(Object data, String charset) {
        if (data != null && charset != null)
            handle(data, data.getClass(), charset, null, null, null, -1);
    }

    // **************************************************************************
    // 私有方法
    private static boolean isList(String typeName) {
        if ("java.util.ArrayList".equals(typeName))
            return true;
        else
            return false;
    }

    private static boolean isMap(String typeName) {
        if ("java.util.HashMap".equals(typeName))
            return true;
        else
            return false;
    }

    private static boolean isString(String typeName) {
        if ("java.lang.String".equals(typeName))
            return true;
        else
            return false;
    }

    @SuppressWarnings("rawtypes")
    private static boolean isBean(Class clazz) {
        if (clazz.isPrimitive())
            return false;
        else {
            String typeName = clazz.getName();
            if (isMap(typeName) || isList(typeName) || isString(typeName))
                return false;
            else {
                if (typeName.startsWith("java.lang"))
                    return false;
                else
                    return true;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static void handleList(List list, String charset) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Object data = list.get(i);
                if (data != null)
                    handle(data, data.getClass(), charset, null, null, null, -1);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static void handleMap(Map map, String charset) {
        if (map != null && !map.isEmpty()) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Object data = iterator.next();
                if (data != null)
                    handle(data, data.getClass(), charset, null, null, null, -1);
            }
        }
    }

    private static void handleString(Field field, Object bean, String charset) {
        try {
            String value = (String) field.get(bean);
            if (value != null && charset != null) {
                if (!value.contains("%@%") && !TextUtils.isEmpty(charset))
                    value = Uri.decode(value);
                //value = URLDecoder.decode(value, charset);
                field.set(bean, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleString(Object array, int index, Object element,
                                     String charset) {
        try {
            String value = (String) element;
            if (value != null && charset != null) {
                if (!value.contains("%@%") && !TextUtils.isEmpty(charset))
                    value = Uri.decode(value);
                //value = URLDecoder.decode(value, charset);
                Array.set(array, index, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleArray(Object array, String charset) {
        if (array != null) {
            int length = Array.getLength(array);
            for (int i = 0; i < length; i++) {
                Object data = Array.get(array, i);
                if (data != null)
                    handle(data, data.getClass(), charset, null, null, array, i);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static void handleBean(Object dataBean, String charset) {
        try {
            if (dataBean != null) {
                Class beanClazz = dataBean.getClass();
                Field[] fields = beanClazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object data = field.get(dataBean);
                    if (data != null) {
                        Class fieldType = field.getType();
                        handle(data, fieldType, charset, field, dataBean, null,
                                -1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    private static void handle(Object data, Class typeClazz, String charset,
                               Field field, Object bean, Object array, int index) {
        if (typeClazz.isArray())
            handleArray(data, charset);
        else {
            if (typeClazz.isPrimitive())
                return;
            if (isBean(typeClazz))
                handleBean(data, charset);
            else {
                String typeName = typeClazz.getName();
                if (isList(typeName))
                    handleList((List) data, charset);
                else if (isMap(typeName))
                    handleMap((Map) data, charset);
                else if (isString(typeName)) {
                    if (field != null)
                        handleString(field, bean, charset);
                    else if (array != null)
                        handleString(array, index, data, charset);
                } else if (typeName.startsWith("java.lang"))
                    return;
            }
        }
    }

}
