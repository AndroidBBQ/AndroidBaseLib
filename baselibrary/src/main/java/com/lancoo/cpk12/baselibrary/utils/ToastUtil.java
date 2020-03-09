package com.lancoo.cpk12.baselibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义的Toast，不管频繁出现多少个Toast，仅出现一个的Toast
 *
 * @author JimChen
 */
public abstract class ToastUtil {
    /**
     * Show the view or text notification for a short period of time. This time could be user-definable. This is the
     * default.
     */
    public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;

    /**
     * Show the view or text notification for a long period of time. This time could be user-definable.
     */
    public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;

    /**
     * 系统自带的Toast
     */
    private static android.widget.Toast toast;
    private static Handler handler = new Handler();

    /**
     * 在Toast指定的显示时间到了之后强制Toast消失
     */
    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    private static void toast(Context ctx, CharSequence msg, int duration) {
        handler.removeCallbacks(run);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时�?
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概�?s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概�?s
                duration = 3000;
                break;
            default:
                break;
        }
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = android.widget.Toast.makeText(ctx, msg, duration);
        }
        handler.postDelayed(run, duration);
        toast.show();
    }

    /**
     * 弹出Toast
     *
     * @param ctx      弹出Toast的上下文
     * @param msg      弹出Toast的内�?
     * @param duration 弹出Toast的持续时�?
     */
    public static void show(Context ctx, CharSequence msg, int duration) throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        if (0 > duration) {
            duration = LENGTH_SHORT;
        }
        toast(ctx, msg, duration);
    }

    /**
     * 弹出Toast
     *
     * @param ctx      弹出Toast的上下文
     * @param resId      弹出Toast的内容的资源ID
     * @param duration 弹出Toast的持续时
     */
    public static void show(Context ctx, int resId, int duration) throws NullPointerException {
        if (null == ctx) {
            throw new NullPointerException("The ctx is null!");
        }
        if (0 > duration) {
            duration = LENGTH_SHORT;
        }
        toast(ctx, ctx.getResources().getString(resId), duration);
    }

    public static void toast(Context context, int msgId) {//xwt
        try {
            if (toast == null) {
                toast = Toast.makeText(context, msgId, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msgId);
            }
            toast.setGravity(Gravity.CENTER, 0, 30);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toast(Context context, String msg) {
        /*if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
