package com.lancoo.cpk12.cplibrary.net;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Time: 2016/4/21;
 * Description: 网络线程通用链
 * 切换线程操作
 *
 * @return Observable转换器
 */
public class NetworkTransformer {


    public static <T> ObservableTransformer<T, T> commonSchedulers() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
