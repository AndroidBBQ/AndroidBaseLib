package com.lancoo.cpk12.baselibrary.utils;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lancoo.cpk12.baselibrary.R;


/**
 * PopupWindow工具类
 */
public class DialogUtil {

    /**
     * 提示确定对话框
     *
     * @param context
     * @param message      要显示的消息
     * @param sureListener 确认监听
     */
    public static void showHintDialog(Context context, String message, final SureClickListener sureListener) {
        showHintDialog(context, "提示", message, "取消", "确认", sureListener);
    }

    public static void showHintNoTitleDialog(Context context, String message, final SureClickListener sureListener) {
        showHintNoTitleDialog(context, message, "取消", "确认", sureListener);
    }

    public static void showHintNoCancelDialog(Context context, String message, final SureClickListener sureListener) {
        showHintDialog(context, "提示", message,
                "取消", "确认", true, false, sureListener);
    }


    public interface SureClickListener {
        void sure(AlertDialog dialog);
    }

    /**
     * 显示提示对话框
     */
    public static void showHintDialog(Context context, String title, String message, String cancelText,
                                      String sureText, final SureClickListener sureListener) {
        showHintDialog(context, title, message, cancelText, sureText, true, true, sureListener);
    }

    /**
     * 显示提示对话框
     */
    public static void showNoCancelHintDialog(Context context, String title, String message, String cancelText,
                                              String sureText, final SureClickListener sureListener) {
        showHintDialog(context, title, message, cancelText, sureText, true, false, sureListener);
    }

    /**
     * 显示提示对话框
     */
    public static void showHintNoTitleDialog(Context context, String message, String cancelText,
                                             String sureText, final SureClickListener sureListener) {
        showHintDialog(context, "提示", message, cancelText, sureText, false, true, sureListener);
    }

    private static void showHintDialog(Context context, String title, String message, String cancelText,
                                       String sureText, boolean isShowTitle, boolean isCancel, final SureClickListener sureListener) {
        View view = View.inflate(context, R.layout.cpbase_dialog_hint_cancel_sure, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        //设置边距
        //设置边距
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            //去除系统自带的margin
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置dialog在界面中的属性
            window.setLayout(context.getResources().getDisplayMetrics().widthPixels
                    - context.getResources().getDimensionPixelSize(com.lancoo.cpk12.baselibrary.R.dimen.dp_88), WindowManager.LayoutParams.WRAP_CONTENT);
        }
        //如果
        if (!isCancel) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        if (isShowTitle) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        TextView tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setText(message);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setText(cancelText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = view.findViewById(R.id.tv_sure);
        tvSure.setText(sureText);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureListener.sure(dialog);
            }
        });
    }

    /**
     * 显示自定义view的对话框
     *
     * @param context
     * @param customView   自定义view
     * @param sureListener 确定点击事件
     */
    public static void showHintCustomViewDialog(Context context, View customView, final SureClickListener sureListener) {
        View view = View.inflate(context, R.layout.cpbase_dialog_hint_custom_cancel_sure, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        //设置边距
        //设置边距
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            //去除系统自带的margin
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置dialog在界面中的属性
            window.setLayout(context.getResources().getDisplayMetrics().widthPixels
                    - context.getResources().getDimensionPixelSize(com.lancoo.cpk12.baselibrary.R.dimen.dp_88), WindowManager.LayoutParams.WRAP_CONTENT);
        }

//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LinearLayout llCustomView = view.findViewById(R.id.ll_custom_view);
        llCustomView.removeAllViews();
        llCustomView.addView(customView);
        //点击取消事件
        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvSure = view.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sureListener.sure(dialog);
            }
        });
    }


    /**
     * 获取从底部弹出的dialog
     *
     * @param context 上下文
     * @param view
     */
    public static Dialog getBottomDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.BottomDialogTheme);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        int screenHeight = DensityUtil.getScreenHeight(context);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (screenHeight * 2) / 3);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
