package com.lancoo.cpk12.baselibrary.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @Author: 葛雪磊
 * @Eail: 1739037476@qq.com
 * @Data: 2019-09-17
 * @Description:
 */
public class BaseContextApplication extends MultiDexApplication {
    private RefWatcher mRefWatcher;
    private static BaseContextApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
        instance = this;

    }

    /**
     * 检测内存泄漏
     * 对于activity和fragment的内存泄漏检测很好用
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        //默认只能检测activity的内存泄漏，要检查fragment的内存泄漏需要使用引用
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseContextApplication application = (BaseContextApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static BaseContextApplication getInstance() {
        return instance;
    }
}
