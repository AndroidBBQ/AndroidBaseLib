package com.lancoo.cpk12.cplibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lancoo.cpk12.cplibrary.bean.EventMessage;
import com.lancoo.cpk12.cplibrary.utils.ToastUtil;
import com.lancoo.cpk12.cplibrary.view.ProDialog;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * create by 葛雪磊
 * time ： 2019-07-30
 * desc ：所有的fragment继承至basefragment
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private ProDialog mProDialog;
    protected View mRootView;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            mUnbinder = ButterKnife.bind(this, mRootView);
            if (isRegisteredEventBus()) {
                EventBus.getDefault().register(this);
            }
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract int attachLayoutRes();

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }

    //---------------------------eventbus---------------------------
    public boolean isRegisteredEventBus() {
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

    //---------------------------销毁---------------------------
    @Override
    public void onDestroy() {
        super.onDestroy();
        initLeakCanary();
        if (isRegisteredEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        this.mContext = null;
        this.mRootView = null;
    }

    /**
     * 用来检测所有Fragment的内存泄漏
     */
    private void initLeakCanary() {
        RefWatcher refWatcher = BaseContextApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
    }

    //------------------隐藏软键盘----------------------------
    private InputMethodManager inputManager;// 输入法管理对象

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        if (inputManager == null) {
            inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    //------------------toast提示------------------------------------

    /**
     * toast提示
     *
     * @param resId
     */
    public void toast(int resId) {
        // 使用应用的context，防止出现内存泄漏
        ToastUtil.show(getContext().getApplicationContext(), resId, ToastUtil.LENGTH_LONG);
        // Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast提示
     */
    public void toast(String res) {
        ToastUtil.show(getContext().getApplicationContext(), res, ToastUtil.LENGTH_LONG);
        // Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    //---------------------显示对话框---------------------------
    public void showProcessDialog() {
        if (mProDialog == null) {
            mProDialog = ProDialog.show(getActivity());
        } else if (!mProDialog.isShowing()) {
            mProDialog.show();
        }
    }

    public void dismissProcessDialog() {
        if (mProDialog != null && mProDialog.isShowing()) {
            mProDialog.cancel();
        }
    }
}
