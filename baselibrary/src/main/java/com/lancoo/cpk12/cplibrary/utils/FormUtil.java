package com.lancoo.cpk12.cplibrary.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 表单工具
 */
public class FormUtil {

    @SuppressWarnings("rawtypes")
    public static HashMap<String, String> convertToForm(Object dataBean) {
        if (dataBean == null)
            return null;
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            Class beanClazz = dataBean.getClass();
            Field[] fields = beanClazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String fieldName = fields[i].getName();
                Object fieldValue = fields[i].get(dataBean);
                String fieldValueJson = GsonUtil.objectToJson(fieldValue);
                map.put(fieldName, clearDirtyDoubleQuotes(fieldValueJson));
            }
            return map;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String clearDirtyDoubleQuotes(String data) {
        if (data != null && "".equals(data) == false) {
            if (data.startsWith("\""))
                data = data.substring(1);
            if (data.endsWith("\""))
                data = data.substring(0, data.length() - 1);
            if ("null".equals(data))
                data = "";
        }
        return data;
    }

}
