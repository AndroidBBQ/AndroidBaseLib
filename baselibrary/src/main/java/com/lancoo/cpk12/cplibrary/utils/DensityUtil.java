package com.lancoo.cpk12.cplibrary.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * DensityUtil
 */
public final class DensityUtil {

    private static float density = -1F;
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    private DensityUtil() {
    }

    public static float getDensity(Context context) {
        if (density <= 0F) {
            density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5F);
    }

    public static int getScreenWidth(Context context) {
        if (widthPixels <= 0) {
            widthPixels = context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight(Context context) {
        if (heightPixels <= 0) {
            heightPixels = context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }

    /**
     * 获取状态栏高度
     */

    public static int getStatusHeight(Activity activity) {
        int statusHeight = -1;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusHeight;

    }

    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

}
