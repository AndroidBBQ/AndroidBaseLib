package com.lancoo.cpk12.baselibrary.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lancoo.cpk12.baselibrary.R;

/**
 * create by 葛雪磊
 * time ： 2019-08-22
 * desc ：加载图片的工具类，对glide框架进行封装，项目中使用到的图片加载用这个工具类进行加载图片
 */
public class LoaderImageUtil {
    /**
     * 加载图片,具有默认的预览图片和图片加载失败的效果
     *
     * @param context   上下文
     * @param imgPath   图片路径
     * @param imageView 要加载到的控件
     */
    public static void loadImage(Context context, Object imgPath, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.cpbase_icon_wait);
        options.error(R.drawable.cpbase_icon_download_fail);
        options.centerCrop();
        Glide.with(context)
                .load(imgPath)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载图片，可以指定预览图片和加载失败的图片
     *
     * @param context     上下文
     * @param imgPath     图片路径
     * @param placeholder 预览图片
     * @param error       加载失败图片
     * @param imageView   要加载到的控件
     */
    public static void loadImage(Context context, Object imgPath, @DrawableRes int placeholder, @DrawableRes int error, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        options.error(error);
        options.centerCrop();
        Glide.with(context)
                .load(imgPath)
                .apply(options)
                .into(imageView);
    }

    /**
     * 使用原生的glide进行加载，不要加载过程和加载失败的效果
     *
     * @param context   上下文
     * @param imgPath   图片路径
     * @param imageView 要设置的imageview
     */
    public static void loadImageNoEffect(Context context, Object imgPath, ImageView imageView) {
        Glide.with(context)
                .load(imgPath)
                .into(imageView);
    }
}
