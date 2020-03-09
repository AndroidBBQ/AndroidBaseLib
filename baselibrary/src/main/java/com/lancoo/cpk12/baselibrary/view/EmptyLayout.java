package com.lancoo.cpk12.baselibrary.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lancoo.cpk12.baselibrary.R;


public class EmptyLayout extends LinearLayout implements
        android.view.View.OnClickListener {
    // 加载成功 不显示emptylayout了
    public static final int HIDE_LAYOUT = 4;
    // 网络没有连接
    public static final int NETWORK_ERROR_PULL_REFRESH = 1;
    public static final int NETWORK_ERROR_CLICK_REFRESH = 2;
    // 正在加载数据
    // public static final int NETWORK_LOADING = 2;
    // 没有数据
    public static final int NODATA_PULL_REFRESH = 3;

    public static final int NODATA_CLICK_REFRESH = 5;

    public static final int NODATA_NOHINT = 6;

    private boolean clickEnable = true;
    private final Context context;
    public ImageView img;
    private android.view.View.OnClickListener listener;
    private int mErrorState;
    private String strNoDataContent = "暂无数据";
    private String strNoNETWORK = "网络连接失败";
    private TextView tv;
    LinearLayout mLayout;
    private TextView refreshTV;
    private int mRealHeight;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.cpbase_layout_nodata, null);
        img = (ImageView) view.findViewById(R.id.nodata_img);
        tv = (TextView) view.findViewById(R.id.noData);
        refreshTV = (TextView) view.findViewById(R.id.pulldownrefresh);
        mLayout = (LinearLayout) view.findViewById(R.id.noDataLayout);

        setBackgroundColor(getResources().getColor(R.color.cpbase_color_white));
        setOnClickListener(this);
        // refreshButton.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // if (clickEnable) {
        // // setErrorType(NETWORK_LOADING);
        // if (listener != null)
        // listener.onClick(v);
        // }
        // }
        // });
        addView(view);
        setShouldSubHeight(0);
        setGravity(Gravity.CENTER);
        // changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1) {
        mLayout.setBackgroundColor(getResources().getColor(R.color.cpbase_color_white));
    }

    public void setEmptyLayoutBackgroudColor(int colorID) {
        setBackgroundColor(getResources().getColor(colorID));
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR_PULL_REFRESH;
    }

    /*
     * public boolean isLoading() { return mErrorState == NETWORK_LOADING; }
     */

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onSkinChanged() {
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    /**
     * 新添设置背景
     *
     * @param imgResource 图片的id
     * @param msg         图片下面的textView显示的文字
     */
    public void setErrorImage(int imgResource, String msg) {
        try {
            img.setBackgroundResource(imgResource);
            tv.setText(msg);
        } catch (Exception e) {
        }
    }

    private final String getString(int resId) {
        return getResources().getString(resId);
    }

    public void setErrorType(int i) {
        setErrorType(i, "");
    }

    public void setErrorType(int i, int textId) {
        setErrorType(i, getString(textId));

    }

    public void setErrorType(int i, String text) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR_PULL_REFRESH:
                mErrorState = NETWORK_ERROR_PULL_REFRESH;
                if (TextUtils.isEmpty(text))
                    tv.setText(strNoNETWORK);
                else
                    tv.setText(text);
                img.setBackgroundResource(R.drawable.cpbase_nonetwork_image_view);
                img.setVisibility(View.VISIBLE);
                refreshTV.setText(R.string.cpbase_pull_refresh);
                clickEnable = false;
                break;

            case NETWORK_ERROR_CLICK_REFRESH:
                mErrorState = NETWORK_ERROR_PULL_REFRESH;
                tv.setText(strNoNETWORK);
                img.setBackgroundResource(R.drawable.cpbase_nonetwork_image_view);
                img.setVisibility(View.VISIBLE);
                refreshTV.setText(R.string.cpbase_click_refresh);
                clickEnable = true;
                break;

            case NODATA_PULL_REFRESH:
                mErrorState = NODATA_PULL_REFRESH;
                img.setBackgroundResource(R.drawable.cpbase_global_nodata_image);
                img.setVisibility(View.VISIBLE);
                tv.setText(text);
                refreshTV.setText(R.string.cpbase_pull_refresh);
                clickEnable = false;
                break;
            case NODATA_CLICK_REFRESH:
                mErrorState = NODATA_PULL_REFRESH;
                img.setBackgroundResource(R.drawable.cpbase_global_nodata_image);
                img.setVisibility(View.VISIBLE);
                tv.setText(text);
                refreshTV.setText(R.string.cpbase_click_refresh);
                clickEnable = true;
                break;
            case NODATA_NOHINT:
                mErrorState = NODATA_NOHINT;
                img.setBackgroundResource(R.drawable.cpbase_global_nodata_image);
                img.setVisibility(View.VISIBLE);
                tv.setText(text);
                refreshTV.setText("");
                clickEnable = false;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void showRefreshTV() {
        refreshTV.setVisibility(View.VISIBLE);
    }

    public void dismissRefreshTV() {
        refreshTV.setVisibility(View.GONE);
    }

    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent(String text) {
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else
            tv.setText(text);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(getRealHeight(),
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 获得屏幕高度
     *
     * @param
     * @return
     */
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取标题栏高度
     */
    public int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getContext().getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        int statusBarHeight1 = 0;
        // 获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            // 根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight1;
    }

    public void setMFHeight() {
        int marginTop = getResources().getDimensionPixelSize(
                R.dimen.tab_empty_height1);
        int toolbar_height = getResources().getDimensionPixelSize(
                R.dimen.toolbar_height1);

        mRealHeight = getScreenHeight() - toolbar_height - marginTop;
        //setMeasuredDimension(getMeasuredWidth(),mRealHeight);
        //invalidate();
    }

    public void setShouldSubHeight(int h) {
        int marginTop = getResources().getDimensionPixelSize(
                R.dimen.notice_content_margin_t_activity);
        int toolbar_height = getResources().getDimensionPixelSize(
                R.dimen.toolbar_height1);
        mRealHeight = getScreenHeight() - toolbar_height - h;
        // setMeasuredDimension(getMeasuredWidth(),mRealHeight);
    }

    public int getRealHeight() {
        return mRealHeight;
    }

    public void setRealHeight(int mRealHeight) {
        this.mRealHeight = mRealHeight;
    }

}