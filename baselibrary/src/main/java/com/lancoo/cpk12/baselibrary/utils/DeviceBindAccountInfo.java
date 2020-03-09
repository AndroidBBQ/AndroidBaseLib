package com.lancoo.cpk12.baselibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lancoo.cpk12.baselibrary.bean.AccountInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @Author: 葛雪磊
 * @Eail: 1739037476@qq.com
 * @Data: 2019-09-15
 * @Description: 设备保存的账号信息
 */
public class DeviceBindAccountInfo {
    public static final String ACCOUNT_INFO_NAME = ".bindaccountinfo";

    /**
     * 获取账号信息的文件夹
     */
    private static File getAccountDir(Context context) {
        File mCropFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File cropdir = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "lancoo" + File.separator + ".authen"
                    + File.separator + ".devices");
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, ACCOUNT_INFO_NAME);
        } else {
            File cropdir = new File(context.getFilesDir(), "/.devices/");
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, ACCOUNT_INFO_NAME);
        }
        return mCropFile;
    }

    // 读取文件内容
    private static String readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bs = new byte[fis.available()];
        fis.read(bs);
        fis.close();
        return new String(bs);
    }

    /**
     * 保存账号信息
     */
    public static void saveAccountInfo(Context context, AccountInfo accountInfo) {
        File file = getAccountDir(context);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            Gson gson = new Gson();
            String str = gson.toJson(accountInfo);
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本机设备绑定的账号信息
     */
    public static AccountInfo getAccountInfo(Context context) throws IOException {
        File accountInfoFile = getAccountDir(context);
        if (!accountInfoFile.exists()) {
            return null;
        } else {
            String accountStr = readFile(accountInfoFile);
            if (TextUtils.isEmpty(accountStr)) {
                return null;
            }
            Gson goson = new Gson();
            AccountInfo accountInfo = goson.fromJson(accountStr, AccountInfo.class);
            return accountInfo;
        }
    }

    /**
     * 直接将文件移除
     */
    public static void removeAccountInfo(Context context) {
        File accountDir = getAccountDir(context);
        if (accountDir.exists()) {
            accountDir.delete();
        }
    }

}
