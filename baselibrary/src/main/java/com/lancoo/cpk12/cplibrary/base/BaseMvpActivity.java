package com.lancoo.cpk12.cplibrary.base;

import android.os.Bundle;

import com.lancoo.cpk12.cplibrary.mvp.BasePresenter;
import com.lancoo.cpk12.cplibrary.mvp.IView;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IView {
    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

}
