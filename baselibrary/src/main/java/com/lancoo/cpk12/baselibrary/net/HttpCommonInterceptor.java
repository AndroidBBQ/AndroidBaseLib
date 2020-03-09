package com.lancoo.cpk12.baselibrary.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/***
 * OkHttp的拦截器
 */
public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuild = oldRequest.newBuilder();
        newRequestBuild.method(oldRequest.method(), oldRequest.body());
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                newRequestBuild.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = newRequestBuild.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor httpCommonInterceptor;

        public Builder() {
            httpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            httpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, float value) {
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, long value) {
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, double value) {
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public HttpCommonInterceptor build() {
            return httpCommonInterceptor;
        }
    }
}
