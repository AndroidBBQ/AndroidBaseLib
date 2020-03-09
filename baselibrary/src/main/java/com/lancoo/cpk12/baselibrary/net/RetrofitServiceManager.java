package com.lancoo.cpk12.baselibrary.net;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * retroift管理类
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 10;
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static volatile OkHttpClient okHttpClient;

    //-----------------------------下面使用了三种转换器，String，gson，xml 没有使用单例会影响性能-----------------------------------------
    public static Retrofit getStringRetrofit(String baseUrl) {
        return getStringBuilder().baseUrl(baseUrl).build();
    }

    public static Retrofit getStringTokenRetrofit(String baseUrl, String token) {
        return getStringBuilder(token).baseUrl(baseUrl).build();
    }

    public static Retrofit getGsonRetrofit(String baseUrl) {
        return getGsonBuilder().baseUrl(baseUrl).build();
    }

    public static <T> T getGsonService(Class<T> service, String baseUrl) {
        return getGsonBuilder().baseUrl(baseUrl).build().create(service);
    }

    public static Retrofit getGsonTokenRetrofit(String baseUrl, final String token) {
        return getGsonBuilder(token).baseUrl(baseUrl).build();
    }

    public static <T> T getGsonTokenService(Class<T> service, String baseUrl, final String token) {
        return getGsonBuilder(token).baseUrl(baseUrl).build().create(service);
    }

    public static Retrofit getXmlRetrofit(String baseUrl) {
        return getXmlBuilder().baseUrl(baseUrl).build();
    }

    //-------------------获取retrofitbuilder---------------------------
    private static Retrofit.Builder getStringBuilder() {
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        retrofitBuild.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuild.addConverterFactory(new StringConverterFactory());
        retrofitBuild.client(initOkHttpClient());
        return retrofitBuild;
    }

    private static Retrofit.Builder getStringBuilder(String token) {
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        retrofitBuild.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuild.addConverterFactory(new StringConverterFactory());
        retrofitBuild.client(initTokenOkhttpClient(token));
        return retrofitBuild;
    }

    private static Retrofit.Builder getGsonBuilder() {
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        retrofitBuild.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuild.addConverterFactory(GsonConverterFactory.create());
        retrofitBuild.client(initOkHttpClient());
        return retrofitBuild;
    }

    private static Retrofit.Builder getGsonBuilder(String token) {
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        retrofitBuild.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuild.addConverterFactory(GsonConverterFactory.create());
        retrofitBuild.client(initTokenOkhttpClient(token));
        return retrofitBuild;
    }

    private static Retrofit.Builder getXmlBuilder() {
        Retrofit.Builder retrofitBuild = new Retrofit.Builder();
        retrofitBuild.addConverterFactory(SimpleXmlConverterFactory.create());
        retrofitBuild.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuild.client(initOkHttpClient());
        return retrofitBuild;
    }

    //------------------------------okhttp----------------------------------
    private static OkHttpClient initTokenOkhttpClient(final String token) {
        return initOkHttpClient(token);
    }

    private static OkHttpClient initOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitServiceManager.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();
                    okhttpbuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
                    okhttpbuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
                    okhttpbuilder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
                    okhttpbuilder.addInterceptor(getHttpCommonInterceptor());
                    okhttpbuilder.addInterceptor(getHttpLoggingInterceptor());
                    okHttpClient = okhttpbuilder.build();
                }
            }
        }
        return okHttpClient;
    }

    private static OkHttpClient initOkHttpClient(String token) {
        OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();
        okhttpbuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        okhttpbuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        okhttpbuilder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        okhttpbuilder.addInterceptor(getHttpCommonTokenInterceptor(token));
        okhttpbuilder.addInterceptor(getHttpLoggingInterceptor());
        okHttpClient = okhttpbuilder.build();
        return okHttpClient;
    }
    //--------------------拦截器---------------------------------

    /**
     * 通用的platform拦截器
     */
    private static HttpCommonInterceptor getHttpCommonInterceptor() {
        HttpCommonInterceptor.Builder interceptorBuild = new HttpCommonInterceptor.Builder();
        interceptorBuild.addHeaderParams("paltform", "android");
        HttpCommonInterceptor httpCommonInterceptor = interceptorBuild.build();
        return httpCommonInterceptor;
    }

    /**
     * 通用的token拦截器
     */
    private static HttpCommonInterceptor getHttpCommonTokenInterceptor(String token) {
        HttpCommonInterceptor.Builder interceptorBuild = new HttpCommonInterceptor.Builder();
        interceptorBuild.addHeaderParams("paltform", "android");
        interceptorBuild.addHeaderParams("Authorization", "X-Token=" + token);
        HttpCommonInterceptor httpCommonInterceptor = interceptorBuild.build();
        return httpCommonInterceptor;
    }

    /**
     * 日志logger
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("gxl", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


}
