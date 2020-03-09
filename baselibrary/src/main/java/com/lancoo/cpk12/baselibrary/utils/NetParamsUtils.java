package com.lancoo.cpk12.baselibrary.utils;

import android.net.Uri;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by tyw on 2017/8/14.
 * 生成网络请求参数的辅助类
 */

public class NetParamsUtils {
    public static String getRequestPm(String[] params) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < params.length - 1; i++) {
            stringBuffer.append(params[i]).append("|");
        }
        stringBuffer.append(params[params.length - 1]);
        return stringBuffer.toString();
    }

    public static String getRequestPmEncode(String[] params) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < params.length - 1; i++) {
            stringBuffer.append(encode(params[i])).append("|");
        }
        stringBuffer.append(encode(params[params.length - 1]));
        return stringBuffer.toString();
    }

    private static String encode(String content) {
        return Uri.encode(content);
    }

    /**
     * 传一个参数map返回一个requestbody
     *
     * @param paramsMap
     * @return
     */
    public static RequestBody buildRequestBody(Map<String, Object> paramsMap) {
        Gson gson = new Gson();
        String paramsStr = gson.toJson(paramsMap);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        paramsStr);
        return requestBody;
    }
}
