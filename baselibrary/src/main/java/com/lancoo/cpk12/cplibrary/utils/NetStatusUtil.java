package com.lancoo.cpk12.cplibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * create by 葛雪磊
 * time ： 2019-08-02
 * desc ：判断网络状态的工具类
 */
public class NetStatusUtil {
    //手机网络wifi
    public static final int TYPE_WIFI = 1;
    //手机网络手机信号
    public static final int TYPE_MOBILE = 2;
    //有些可能受有线网络，。。虽然少，但确实有可能
    public static final int TYPE_LINE_NET = 3;
    //没有网络
    public static final int TYPE_NO_NET = 0;

    /**
     * 获取手机的网络连接状态。
     *
     * @return TYPE_NO_NET（网络断开），TYPE_WIFI（wifi网络），TYPE_MOBILE（2G、3G、4G网络）,TYPE_LINE_NET(有线网络)
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMan.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) { // 网络可用
            int typeInt = info.getType();
            switch (typeInt) {
                case ConnectivityManager.TYPE_WIFI:
                    return TYPE_WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    return TYPE_MOBILE;
                case ConnectivityManager.TYPE_ETHERNET:
                    return TYPE_LINE_NET;
            }
        }
        // 网络不可用
        return TYPE_NO_NET;
    }

}
