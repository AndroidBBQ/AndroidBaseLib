package com.lancoo.cpk12.baselibrary.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttachmentUtil {
    /**
     * 获取附件大的名称
     */
    public static String getAttachmentName(String name) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        int pos = name.lastIndexOf(".");
        String type = "";
        if (pos != -1) type = name.substring(pos);
        String attachmentName = simpleFormat.format(new Date())
                + (int) (Math.random() * 900 + 100)
                + type;
        return attachmentName;
    }

    public static String getSizeStrWithKb(float kbSize) {
        DecimalFormat df = new DecimalFormat("0.00");
        String value;
        if (kbSize >= 256) {
            float mbSize = kbSize / 1024;
            value = df.format(mbSize) + "MB";
        } else
            value = df.format(kbSize) + "KB";
        return value;
    }

    /**
     * 将byte转成 kb 或 mb
     */
    public static String getSizeStrWithByte(long byteSize) {
        float kbSize = (float) byteSize / 1024;
        return getSizeStrWithKb(kbSize);
    }

    public static float getFileKbSize(long byteSize) {
        float kbSize = byteSize / 1024;
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.valueOf(df.format(kbSize));
    }

    /*
     * 对下载的本地同文件重命名 xwt
     * 传人文件绝对路径
     */
    public static String getRenameFilePath(String fileNamePath) {
        String fileName;
        String fileType;
        if (fileNamePath.contains(".")) {
            fileName = fileNamePath.substring(0, fileNamePath.lastIndexOf("."));
            fileType = fileNamePath.substring(fileNamePath.lastIndexOf("."));
        } else {
            fileName = fileNamePath;
            fileType = "";
        }
        return chooseUniqueFilename(fileName, fileType);
    }


    private static String chooseUniqueFilename(String fileName, String fileType) {
        int suffix_no = 1;
        File downloadFile = new File(fileName + fileType);
        //if(!downloadFile.exists()) return fileName+fileType;
        String nameString = fileName + fileType;
        while (downloadFile.exists()) {
            nameString = fileName + "(" + suffix_no + ")"
                    + fileType;
            downloadFile = new File(nameString);
            suffix_no++;
            if (suffix_no > 10000) //防止出错陷入死循环
                break;
        }
        return nameString;
    }


}
