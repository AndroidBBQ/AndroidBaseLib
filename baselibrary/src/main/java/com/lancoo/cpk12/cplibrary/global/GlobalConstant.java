package com.lancoo.cpk12.cplibrary.global;

import android.os.Environment;

import java.io.File;

/**
 * 常量,定义了一些常用的常量
 */
public class GlobalConstant {

    /**
     * *******************************文件目录****************************
     */
    public static final String PACKAGENAME = "com.lancoo.cpk12";
    // 根目录
    public static final String ROOT = Environment.getExternalStorageDirectory()
            + File.separator + "Android" + File.separator + "data" +
            File.separator + PACKAGENAME + File.separator;


    //  /Android/data/com.lancoo.cpk12/download
    public static final String Extra_All_PATH = ROOT + "extra"
            + File.separator;
    // 下载路径------------
    // 图片路径
    public static final String TEMP_PATH = Extra_All_PATH + "temp"
            + File.separator;

    // 头像路径
    public static final String HEAD_PATH = Extra_All_PATH + "head"
            + File.separator;
    //------------------------
    public static final String DOWN_PATH = Extra_All_PATH + "down"
            + File.separator;
    // app下载地址
    public static final String STUDY_APP_APK = DOWN_PATH + "apk" + File.separator;

    // 个人网盘资源下载存储路径
    public static final String PERSONAL_RES_DOWNLOAD = DOWN_PATH
            + "personaldisk" + File.separator;

    // 个人网盘资源视频资源存储路径
    public static final String PERSONAL_MOVIES_RES = PERSONAL_RES_DOWNLOAD +
            File.separator + "movies" + File.separator;

    public static final String THIRD_HEAD = DOWN_PATH + "third"
            + File.separator;
    //db文件
    public static final String HINATA_DB = "HinataDB";

    //------------------------------------------------------------------------------
    // web base url
    public static String mWebBaseUrl = "";
    //----------------一些SharedPreferences文件的名字---------------
    public static final String SP_KEY_PRODUCT_TYPE = "productType";
    //以后通用的存储的东西都放到这个里面
    public static final String SP_CPK12 = "sp_cpk12";

    //-------------------eventbus------------------------------
    public static final int INFOCENTER_UPDATE_MESSAGE_CODE = 0x0044;
    public static final int WS_UPDATE_MESSAGE_CODE = 0x0055;
    //消息中心的tab的可以  值 0代表代表事项，1代表通知，2代表系统消息
    public static final String KEY_INFOCENTER_TAB = "Data";
}
