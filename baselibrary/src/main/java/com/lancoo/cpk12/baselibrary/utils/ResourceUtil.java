package com.lancoo.cpk12.baselibrary.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * create by 葛雪磊
 * time ： 2019-08-27
 * desc ：
 */
public class ResourceUtil {
    /**
     * 获取资源string
     *
     * @param context 上下文
     * @param resId   资源id
     * @return
     */
    public static String getString(Context context, @StringRes int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取颜色
     *
     * @param context 上下文
     * @param resId   资源id
     * @return
     */
    public static int getColor(Context context, @ColorRes int resId) {
        return context.getResources().getColor(resId);
    }
}
