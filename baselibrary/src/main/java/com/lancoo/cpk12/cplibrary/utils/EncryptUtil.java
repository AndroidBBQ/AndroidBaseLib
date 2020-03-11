package com.lancoo.cpk12.cplibrary.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 加密解密工具类
 */
public class EncryptUtil {

    /**
     * 获取进行MD5加密，反转后的密码
     */
    public static String reverseMD5(String password) {
        String finalPassword = new StringBuffer("").append(MD5(password))
                .reverse().toString();
        return finalPassword;
    }

    /**
     * MD5 加密算法
     */
    public static String MD5(String originalPW) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = originalPW.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = new byte[0];
        try {
            md5Bytes = md5.digest(originalPW.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    /**
     * 加密算法
     */
    public static String EncryptCode(String userID, String str) {
        byte[] idByte = null;
        try {
            idByte = MD5(userID).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte S_key = idByte[idByte.length - 1];// 密钥
        byte[] value;
        try {
            value = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            value = str.getBytes();
        }
        int[] valueXOR = new int[value.length];// 异或之后的字符串
        StringBuffer result = new StringBuffer("");// 结果字符串
        for (int i = 0; i < value.length; i++) {
            // value[i]与S_key异或运算
            // Log.i("加密", "异或之后 " + (value[i] ^ S_key));
            valueXOR[i] = (value[i] ^ S_key);
            if (valueXOR[i] < 0) {
                valueXOR[i] += 256;
            }
            result.append(String.format("%03d", valueXOR[i]));// 左侧补充0
        }
        String finalResult = result.reverse().toString();
        return finalResult;
    }

    /**
     * 解密算法
     */
    public static String DecryptCode(String userID, String EncryptInfo) {
        byte[] idByte = new byte[0];// android默认utf-8格式，一个中文字符占用3个字符
        try {
            idByte = MD5(userID).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte S_key = idByte[idByte.length - 1];// 密钥
        String S_source = new StringBuffer("").append(EncryptInfo).reverse()
                .toString();
        byte[] value = new byte[S_source.length() / 3];// 构造value数组

        int temp;
        for (int i = 0, k = 0; i < S_source.length(); i += 3, k++) {
            temp = Integer.parseInt(S_source.substring(i, i + 3));
            value[k] = (byte) (temp ^ S_key);
        }
        String result;
        try {
            result = new String(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            result = new String(value);
        }
        return result;
    }

    /**
     * PC端加密方式
     *
     * @param SourceStr 需要加密的字符串
     * @return 加密之后的字符串
     */
    public static String LgMgr_ParamEncrypt(String SourceStr) {
        String res = "";
        String tmpStr = "";
        int newVal;
        int I, J, K;
        try {
            SourceStr = SourceStr.trim();
            I = SourceStr.length();
            if (0 == I)
                return res;

            StringBuilder tmpStrBuilder = new StringBuilder();
            StringBuilder dStrBuilder = new StringBuilder();
            Random r = new Random();
            for (J = 0; J < I; J++) {
                K = r.nextInt(9);
                newVal = (int) (SourceStr.charAt(J)) + K;
                tmpStrBuilder.append(String.valueOf(newVal));
                dStrBuilder
                        .append(((String.valueOf(newVal).length()) * 10 + K));
            }

            tmpStr = tmpStrBuilder.append(dStrBuilder.toString()).toString();
            J = String.valueOf(I).length();
            K = r.nextInt(9);
            res = (J * 10 + K) + tmpStr.substring(0, K) + I
                    + tmpStr.substring(K);
        } catch (Exception e) {
            res = "";
        }
        return res;

    }

    /**
     * PC端解密方式
     *
     * @param EncryptStr 需要解密的字符串
     * @return 解密之后的字符串
     */
    public static String LgMgr_ParamDecrypt(String EncryptStr) {
        String res = "";
        String tmpStr = "";
        int I, J, K;
        int DataLen;
        if ("" == EncryptStr.trim())
            return res;
        try {
            I = Integer.valueOf(EncryptStr.substring(0, 1));
            J = Integer.valueOf(EncryptStr.substring(1, 2));
            StringBuilder EnStrBuilder = new StringBuilder();
            tmpStr = EnStrBuilder.append(EncryptStr.substring(2)).substring(J,
                    J + I);
            DataLen = Integer.valueOf(tmpStr);
            tmpStr = EnStrBuilder.toString();
            EnStrBuilder.delete(0, tmpStr.length());
            EnStrBuilder.append(tmpStr.substring(0, J));
            EnStrBuilder.append(tmpStr.substring(J + I));
            tmpStr = EnStrBuilder.toString();
            StringBuilder RndStrBuilder = new StringBuilder();
            RndStrBuilder
                    .append(tmpStr.substring(tmpStr.length() - DataLen * 2));
            StringBuilder resBuilder = new StringBuilder();
            for (J = 0; J < DataLen; J++) {
                I = Integer.valueOf(RndStrBuilder.toString().substring(0, 1));
                K = Integer.valueOf(RndStrBuilder.toString().substring(1, 2));
                RndStrBuilder.delete(0, 2);
                resBuilder.append((char) ((Integer.valueOf(EnStrBuilder
                        .substring(0, I)) - K)));
                EnStrBuilder.delete(0, I);
            }
            res = resBuilder.toString();
        } catch (Exception e) {
            res = "";
        }
        return res;
    }

}
