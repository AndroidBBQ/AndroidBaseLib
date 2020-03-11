package com.lancoo.cpk12.cplibrary.utils;

/**
 * create by 葛雪磊
 * time ： 2019-07-30
 * desc ：管理 aroute的跳转
 */
public class ARouteUtil {
    //几个模块的主界面，如果有新模块加入，在这个方法中要进行配置
    public static final String INDEX_STUDENT_HOME = "/index/student/HomeActivity";
    public static final String AUTHENTICATION_HOME = "/authentication/LoginActivity";
    public static final String COMMUNICATE_STUDENT_HOME = "/communicate/student/StudentCommunicateActivity";
    public static final String FAVORITE_STUDENT_HOME = "/favorite/student/StudentFavoriteActivity";
    public static final String PERDISK_STUDENT_HOME = "/persondisk/student/StudentPerondiskActivity";
    public static final String SCHEDULE_STUDENT_HOME = "/schedule/student/StudentScheduleActivity";
    public static final String USERINFO_STUDENT_HOME = "/userinfo/student/StudentUserInfoActivity";

    //预览视频，音频，图片，pdf,网页的activity
    public static final String PREVIEW_VIDEO = "/preview/VideoPlayActivity";
    public static final String PREVIEW_AUDIO = "/preview/AudioPlayActivity";
    public static final String PREVIEW_IMAGE = "/preview/PreviewImageActivity";
    public static final String PREVIEW_PDF = "/preview/PreviewPDFActivity";
    public static final String PREVIEW_HTML = "/preview/PreviewHtmlActivity";

    //传递的url和标题的key
    public static final String KEY_FILE_URL = "key_file_url";
    public static final String KEY_FILE_NAME = "key_file_name";
}
