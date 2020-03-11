package com.lancoo.cpk12.cplibrary.net;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Author 葛雪磊
 * @Email 1739037474@qq.com
 * @Date 2019/11/29
 * @Description
 */
public class OkhttpProvider {
    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    public static Context mContext;
    //    File httpCacheDirectory = new File(context.getCacheDir(), "responses");
//    //外部存储
//    File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
    public static OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
//            .cache(new Cache(new File(getExternalCacheDir(), "responses"),10*1024*1024))
            //有网时使用的拦截器
//           .addNetworkInterceptor(new UrlInterceptor())//调试用
            //所有情况下使用的拦截器
            .addInterceptor(getHttpLoggingInterceptor())
            .addInterceptor(getHttpCommonInterceptor())
//             .addInterceptor(new RequestHeaderInterceptor())
//            .addInterceptor(new RequestEncryptInterceptor())
//             .addInterceptor(new MyRequestHeaderInterceptory())
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            .build();

    public static HttpCommonInterceptor getHttpCommonInterceptor() {
        HttpCommonInterceptor.Builder interceptorBuild = new HttpCommonInterceptor.Builder();
        interceptorBuild.addHeaderParams("paltform", "android");
        HttpCommonInterceptor httpCommonInterceptor = interceptorBuild.build();
        return httpCommonInterceptor;
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    static public HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (TextUtils.isEmpty(message)) return;
                Log.d("aaaa", "收到响应: " + message);
                saveCrashInfo2File(message);
                if (message.startsWith("{") || message.startsWith("[")) {
//                    LogUtils.d("aaaa","收到响应: " + message);
                }
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }


    /**
     * 保存错误信息到文件中
     * *
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private static void saveCrashInfo2File(String messege) {
        if (mContext == null) {
            return;
        }
        if (TextUtils.isEmpty(messege)) {
            return;
        }

        try {
            String fileName = "log-" + "request" + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory() +
                        "/Android/data/" + mContext.getApplicationInfo().packageName + "/files/logs/";
//                String path = "/sdcard/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                Log.e("aaaaaaaaaaaaa", path + fileName);
                FileWriter writer = new FileWriter(path + fileName, true);
                writer.write(messege);
                writer.close();
            }
            return;
        } catch (Exception e) {
            Log.e("aaaaaaaaaa", "an error occured while writing file...", e);
        }

        return;
    }
}
