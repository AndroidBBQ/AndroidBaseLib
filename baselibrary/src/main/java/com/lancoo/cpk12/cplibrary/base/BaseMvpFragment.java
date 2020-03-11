package com.lancoo.cpk12.cplibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lancoo.cpk12.cplibrary.mvp.BasePresenter;
import com.lancoo.cpk12.cplibrary.mvp.IView;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IView {
    protected abstract P createPresenter();

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }
}
