package com.lancoo.cpk12.cplibrary.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.lancoo.cpk12.cplibrary.R;

/**
 * 加载进度Dialog
 */
public class ProDialog extends ProgressDialog {

    private static final int PADDING = 8;// 阴影部分padding

    private int proPadding = PADDING;

    public ProDialog(Context context, int padding) {
        super(context, R.style.AlertDialogStyle);
        this.proPadding = padding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化加载进度布局
        initProLayout();
    }

    private void initProLayout() {
        // 主布局
        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // ProgressBar布局
        RelativeLayout proBarLayout = new RelativeLayout(getContext());
        proBarLayout.setBackgroundResource(R.drawable.cpbase_dialog_bg_shape_authentication);
        proBarLayout.setLayoutParams(new LayoutParams(dip2px(85),
                dip2px(85)));
        layout.addView(proBarLayout);
        ProgressBar bar = new ProgressBar(getContext());
        int id = 0x12;
        bar.setId(id);
        bar.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.cpbase_progressbar_authentication));
        proBarLayout.addView(bar);

        TextView textView = new TextView(getContext());
        //textView.setText(mTitle);
        textView.setTextColor(Color.WHITE);
        proBarLayout.addView(textView);

        LayoutParams tvParams = (LayoutParams) textView
                .getLayoutParams();
        tvParams.addRule(RelativeLayout.BELOW, id);
        tvParams.setMargins(0, dip2px(2), 0, 0);
        tvParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LayoutParams proBarParams = (LayoutParams) proBarLayout
                .getLayoutParams();
        proBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LayoutParams barParams = (LayoutParams) bar
                .getLayoutParams();
        barParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        proBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        int pad = dip2px(proPadding);
        proBarLayout.setPadding(pad, pad, pad, pad);
        // 设置布局
        setContentView(layout);
        // 将背景置为透明
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;


        window.setAttributes(params);
        //setProgressStyle(R.style.AlertDialogStyle);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 显示加载Dialog
     *
     * @param context  当前上下文
     * @param padding  阴影部分大小int类型（会转成对应的dp类型）
     * @param isCancel 是否可以cancel
     * @return
     */
    public static ProDialog show(Context context, int padding, boolean isCancel) {
        ProDialog dialog = new ProDialog(context, padding);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(isCancel);
        dialog.show();
        return dialog;
    }

    /**
     * 显示加载Dialog，默认大小
     *
     * @param context  当前上下文
     * @param isCancel 是否可以cancel
     * @return
     */
    public static ProDialog show(Context context, boolean isCancel) {
        return show(context, PADDING, isCancel);
    }

    /**
     * 显示加载Dialog，默认大小，不可cancel
     *
     * @param context 当前上下文
     * @return
     */
    public static ProDialog show(Context context) {
        return show(context, true);
    }

    public GradientDrawable getColorBg() {
        //  int strokeWidth = 5; // 3dp 边框宽度
        int roundRadius = 6; // 3dp 圆角半径
        //	int strokeColor = Color.parseColor("#2E3135");//边框颜色
        int fillColor = Color.parseColor("#95000000");//内部填充颜色

        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        //gd.setCornerRadii(new float[]{roundRadius,roundRadius, roundRadius, roundRadius, roundRadius, roundRadius, roundRadius,roundRadius});
        //gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

}
