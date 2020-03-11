package com.lancoo.cpk12.cplibrary.net;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author 葛雪磊
 * @Email 1739037474@qq.com
 * @Date 2019/11/29
 * @Description
 */
public class RetrofitBuilderProvider {
    public static retrofit2.Retrofit.Builder getBuilder() {
        return new retrofit2.Retrofit.Builder()
                .client(OkhttpProvider.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
    public static retrofit2.Retrofit.Builder getBuilder(String token) {
        return new retrofit2.Retrofit.Builder()
                .client(OkhttpProvider.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

}
