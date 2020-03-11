package com.lancoo.cpk12.cplibrary.net;

import com.lancoo.cpk12.cplibrary.R;
import com.lancoo.cpk12.cplibrary.base.BaseContextApplication;
import com.lancoo.cpk12.cplibrary.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;


/**
 * @Author 葛雪磊
 * @Email 1739037474@qq.com
 * @Date 2019/11/28
 * @Description
 */
public abstract class CustomToastThrowable implements Consumer<Throwable> {
    @Override
    public void accept(Throwable t) {
        advanceHandle();
        if (t instanceof SocketTimeoutException) {
            ToastUtil.show(BaseContextApplication.getInstance(), R.string.cpbase_toast_network_timeout, ToastUtil.LENGTH_SHORT);
        } else if (t instanceof ConnectException) {
            ToastUtil.show(BaseContextApplication.getInstance(), R.string.cpbase_toast_network_no_network, ToastUtil.LENGTH_SHORT);
        } else {
            ToastUtil.show(BaseContextApplication.getInstance(), R.string.cpbase_toast_networdk_error, ToastUtil.LENGTH_SHORT);
        }
    }

    /**
     * 首先处理的，做些关闭进度条的操作
     */
    public abstract void advanceHandle();
}
