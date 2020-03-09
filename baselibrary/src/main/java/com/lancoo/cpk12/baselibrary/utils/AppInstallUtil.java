package com.lancoo.cpk12.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;


/**
 * create by 葛雪磊
 * time ： 2019-08-02
 * desc ：App安装工具类，兼容8.0
 */
public class AppInstallUtil {
    /**
     * 安装app
     *
     * @param context 上下文
     * @param apkPath apk路径
     */
    public static void install(Context context, String apkPath) {
        File apkFile = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authen = AppUtils.getPackageName(context) + ".fileprovider";
            Uri contentUri = FileProvider.getUriForFile(
                    context
                    , authen
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 获取安装apk的intent
     *
     * @param context 上下文
     * @param apkPath apk路径
     * @return
     */
    public static Intent getInstallIntent(Context context, String apkPath) {
        File apkFile = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authen = AppUtils.getPackageName(context) + ".fileprovider";
            Uri contentUri = FileProvider.getUriForFile(
                    context
                    , authen
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * 打开安装intall的设置页面
     *
     * @param context     上下文
     * @param requestCode 请求吗
     */
    public static void openInstallSetting(Activity context, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {
                //权限没有打开则提示用户去手动打开
                Uri packageURI = Uri.parse("package:" + AppUtils.getPackageName(context));
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                context.startActivityForResult(intent, requestCode);
            }
        }
    }
}
