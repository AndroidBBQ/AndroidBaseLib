package com.lancoo.cpk12.baselibrary.net;

import com.lancoo.cpk12.baselibrary.R;
import com.lancoo.cpk12.baselibrary.view.EmptyLayout;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;

/**
 * @Author 葛雪磊
 * @Email 1739037474@qq.com
 * @Date 2019/11/28
 * @Description  提示为点击刷新
 */
public abstract class CustomEmptyRefreshThrowable implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {
        advanceHandle();
        EmptyLayout emptyLayout = getEmptyLayout();
        if (throwable instanceof SocketTimeoutException) {
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR_CLICK_REFRESH, R.string.cpbase_empty_network_timeout);
        } else if (throwable instanceof ConnectException) {
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR_CLICK_REFRESH, R.string.cpbase_empty_network_no_network);
        } else {
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR_CLICK_REFRESH, R.string.cpbase_empty_networdk_error);
        }
    }

    /**
     * 首先处理的，做些关闭进度条的操作
     */
    public abstract void advanceHandle();

    protected abstract EmptyLayout getEmptyLayout();

}
