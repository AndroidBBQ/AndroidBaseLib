package com.lancoo.cpk12.baselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lancoo.cpk12.baselibrary.R;
import com.lancoo.cpk12.baselibrary.bean.EventMessage;
import com.lancoo.cpk12.baselibrary.global.ActivityManageUtils;
import com.lancoo.cpk12.baselibrary.utils.EventBusUtils;
import com.lancoo.cpk12.baselibrary.utils.StatusBarUtil;
import com.lancoo.cpk12.baselibrary.utils.ToastUtil;
import com.lancoo.cpk12.baselibrary.view.AutoBgImageView;
import com.lancoo.cpk12.baselibrary.view.ProDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 葛雪磊
 * @version 基础的baseactivity
 */
public class BaseActivity extends AppCompatActivity {
    private LinearLayout rootLayout;
    private Toolbar toolbar;
    private int mActionBarHei;// actionbar高度
    private Context mContext;
    private InputMethodManager inputManager;// 输入法管理对象
    private ProDialog mProDialog;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.cpbase_activity_root_base);
        if (isRegisteredEventBus()) {
            EventBusUtils.register(this);
        }
        mUnbinder = ButterKnife.bind(this);
        this.mContext = this;
        addActivity();
        //护眼模式
        protectEyeStyle();
    }

    /**
     * 护眼模式
     */
    private void protectEyeStyle() {
        SharedPreferences sp_eye_health = getSharedPreferences("sp_eye_health", Context.MODE_PRIVATE);
        boolean isPortectEye = sp_eye_health.getBoolean("key_eye_protect", false);
        if (isPortectEye) {
            openHealthEye();
        } else {
            closeHealthEye();
        }
    }

    //----------------------------toolbar------------------------------------------
    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) {
            return;
        }
        if (isAddToolbar()) {
            //让状态栏沉浸式
            StatusBarUtil.setTransparentForWindow(this);
            View toolbarView = View.inflate(BaseActivity.this, R.layout.cpbase_activity_toolbar, null);
            rootLayout.addView(toolbarView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initToolbar(toolbarView);
        } else {
            rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            StatusBarUtil.setTransparentForWindow(this);
        }
    }

    /**
     * 是否添加 toolbar 默认是添加的
     */
    protected boolean isAddToolbar() {
        return true;
    }

    protected void initToolbar(View toolbarView) {
        toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mActionBarHei = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mActionBarHei + getStatusBarHeight());
        toolbar.setLayoutParams(layoutParams);
        if (toolbar != null) {
            View toolbarContent = View.inflate(this, R.layout.cpbase_main_actionbar, null);
            toolbar.addView(toolbarContent);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //默认处理左边的点击事件为finish
            initLeftEvent();
        }
    }

    private void initLeftEvent() {
        if (toolbar != null) {
            ImageView ivActionBarLeft = toolbar.findViewById(R.id.ivActionBarLeft);
            ivActionBarLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    //--------------------------------自定义toolbar布局-----------------------------------------------------

    /**
     * 自定义toolbar 布局
     */
    protected void initToolBar(int layoutID) {
        if (!isAddToolbar()) {
            return;
        }

        if (null != toolbar) {
            try {
                toolbar.removeAllViews();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mActionBarHei = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mActionBarHei + getStatusBarHeight());
        toolbar.setLayoutParams(layoutParams);
        if (toolbar != null) {
            View toolbarContent = View.inflate(this, layoutID, null);
            toolbar.addView(toolbarContent);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    //------------------------------自定义布局事件----------------------------------------------------

    /**
     * 设置返回监听
     *
     * @param listener
     */
    public void setLeftEvent(OnClickListener listener) {
        View ivActionBarLeft = toolbar.findViewById(R.id.ivActionBarLeft);
        ivActionBarLeft.setVisibility(View.VISIBLE);
        ivActionBarLeft.setOnClickListener(listener);
    }

    /**
     * 设置左边的图片
     *
     * @param ResId
     */
    public void setLeftImageView(@DrawableRes int ResId) {
        AutoBgImageView ivActionBarLeft = (AutoBgImageView) toolbar.findViewById(
                R.id.ivActionBarLeft);
        ivActionBarLeft.setVisibility(View.VISIBLE);
        ivActionBarLeft.setImageResource(ResId);
    }

    /**
     * 设置头部背景
     */
    public void setRootViewBackground(@DrawableRes int res) {
        if (!isAddToolbar()) {
            return;
        }
        if (toolbar != null) {
            toolbar.setBackgroundResource(res);
        }
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setCenterTitle(int resId) {
        TextView tvActionBarCenter = (TextView) toolbar.findViewById(R.id.tvActionBarCenter);
        tvActionBarCenter.setText(resId);
    }

    /**
     * 设置标题
     *
     * @param str
     */
    public void setCenterTitle(String str) {
        TextView tvActionBarCenter = (TextView) toolbar.findViewById(R.id.tvActionBarCenter);
        tvActionBarCenter.setText(str);
    }

    public void setCenterListener(OnClickListener listener) {
        TextView tvActionBarCenter = (TextView) toolbar.findViewById(R.id.tvActionBarCenter);
        tvActionBarCenter.setVisibility(View.VISIBLE);
        tvActionBarCenter.setOnClickListener(listener);
    }

    /**
     * 设置toolbar背景透明
     */
    public void setToolbarBackgroundNull() {
        if (toolbar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                toolbar.setBackground(null);
            }
        }
    }

    /**
     * 设置 toolbar 颜色
     */
    public void setToolbarBackgroundColor(int res) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(res);
        }
    }

    /**
     * 设置右边的 icon
     */
    public void setRightIcon(@DrawableRes int rightIcon) {
        if (toolbar != null) {
            AutoBgImageView ivActionbarRight = (AutoBgImageView) toolbar.findViewById(R.id.ivActionBarRight);
            ivActionbarRight.setVisibility(View.VISIBLE);
            ivActionbarRight.setBackgroundResource(rightIcon);
        }
    }

    public void setRightIconClickListener(OnClickListener listener) {
        if (toolbar != null) {
            AutoBgImageView ivActionbarRight = (AutoBgImageView) toolbar.findViewById(R.id.ivActionBarRight);
            ivActionbarRight.setVisibility(View.VISIBLE);
            ivActionbarRight.setOnClickListener(listener);
        }
    }

    public void setRightText(String text, OnClickListener listener) {
        if (toolbar != null) {
            TextView tvActionBarRight = toolbar.findViewById(R.id.tvActionBarRight);
            tvActionBarRight.setVisibility(View.VISIBLE);
            tvActionBarRight.setText(text);
            tvActionBarRight.setOnClickListener(listener);
        }
    }

    public void setRightText(String text) {
        if (toolbar != null) {
            TextView tvActionBarRight = toolbar.findViewById(R.id.tvActionBarRight);
            tvActionBarRight.setVisibility(View.VISIBLE);
            tvActionBarRight.setText(text);
        }
    }

    public void setRightTextVisible(int visible) {
        if (toolbar != null) {
            TextView tvActionBarRight = toolbar.findViewById(R.id.tvActionBarRight);
            tvActionBarRight.setVisibility(visible);
        }
    }
    //-------------------------页面跳转-----------------------------------

    /**
     * [页面跳转]
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    //--------------activity的管理----------------------------------------------------
    // 添加Activity方法
    public void addActivity() {
        ActivityManageUtils.getInstance().addActivity(this);// 调用myApplication的添加Activity方法
    }

    // 销毁当个Activity方法
    public void finishActivity() {
        ActivityManageUtils.getInstance().finishActivity(this);// 调用myApplication的销毁单个Activity方法
    }

    // 销毁所有Activity方法
    public void removeALLActivity() {
        ActivityManageUtils.getInstance().finishAllActivity();// 调用myApplication的销毁所有Activity方法
    }
    //-----------------------------打印toast-------------------------------------------

    /**
     * toast提示
     */
    public void toast(int resId) {
        ToastUtil.toast(getApplicationContext(), resId);
    }

    /**
     * toast提示
     */
    public void toast(String str) {
        ToastUtil.toast(getApplicationContext(), str);
    }
    //-------------------------------隐藏键盘--------------------------------------------

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        if (inputManager == null) {
            inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //------------------------------eventbus事件--------------------------------------------------

    /**
     * 是否注册事件分发 * * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

    /**
     * 接收到分发的事件 * * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
    }

    /**
     * 接受到分发的粘性事件 * * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }

    //------------------------------显示和隐藏加载框---------------------------------------------------
    public void showProcessDialog() {
        if (mProDialog == null) {
            mProDialog = ProDialog.show(this);
        } else {
            mProDialog.show();
        }
    }

    public void dismissProcessDialog() {
        if (mProDialog != null && mProDialog.isShowing()) {
            mProDialog.cancel();
        }
    }

    //------------------------------解绑---------------------------------------------------
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
        finishActivity();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }

    //-------------------护眼模式---------------------------
    private FrameLayout frameLayout;

    public void openHealthEye() {
        //打开 dailog 窗口 对 dailog 初始化
        ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
        frameLayout = new FrameLayout(getApplicationContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(frameLayout, params);
        getData();
    }

    public void closeHealthEye() {
        ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
        decorView.removeView(frameLayout);
    }


    public void getData() {
        //获取 存储 sharePrefrence 保存的三原色值
        int realFilter = 20;
        int a = (int) (realFilter / 80f * 180);
        int r = (int) (200 - (realFilter / 80f) * 190);
        int g = (int) (180 - (realFilter / 80f) * 170);
        int b = (int) (60 - realFilter / 80f * 60);
        frameLayout.setBackgroundColor(Color.argb(a, r, g, b));
    }
    //------------------获取一些属性-------------------

    public Context getContext() {
        return mContext;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
