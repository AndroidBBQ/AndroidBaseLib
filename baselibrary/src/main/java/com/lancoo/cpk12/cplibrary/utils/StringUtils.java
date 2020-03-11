package com.lancoo.cpk12.cplibrary.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 */
public class StringUtils {

    /**
     * 正则表达式检测邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "(\\S)+@(\\S)+\\.[a-zA-Z]{2,3}";
        // \S表示非空字符
        if (!Pattern.matches(regex, email)) {
            return false;
        } else {
            regex = "([a-zA-Z0-9]+[_|\\-|\\.]*)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\-|\\.]*)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}";
            return Pattern.matches(regex, email);
        }
    }

    /**
     * 用户快捷用户名检测 长度为3-15字符，只能由字母、数字、下划线组成。
     *
     * @param shortName
     * @return
     */
    public static int checkShortName(String shortName) {
        String regex = "([0-9_a-zA-Z]){3,15}";
        if (!Pattern.matches(regex, shortName)) {
            return 1; // 格式错误
        } else {
            regex = "(admin)";
            if (Pattern.matches(regex, shortName)) {
                return 2; // 与管理员ID具有相同格式
            } else {
                return 0;
            }
        }
    }

    /**
     * 用户身份证号检测 长度只能是18位，只能由纯数字或数字与1位大写字母组成。
     *
     * @param idCard
     * @return
     */
    public static boolean checkIDCard(String idCard) {
        String regex = "(([0-9]{18})|([0-9]{17}[A-Za-z]))";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 用户姓名检测（搜索之用！参照教务的规则）
     *
     * @param userName 用户姓名由1-20位的汉字、字母、数字、空格组成。
     * @return
     */
    public static boolean checkUserName(String userName) {
        return Pattern.matches("[a-zA-Z0-9\u4e00-\u9fa5• •.\\s]{1,20}",
                userName);
    }

    /**
     * 联系电话检测 联系电话可以为手机号、电话号码，用“/”号分隔。
     *
     * @param phone
     * @return
     */
    public static boolean checkTelephone(String phone) {
        return Pattern.matches("([0-9\\/-]){1,50}", phone);
    }

    /**
     * 联系地址名检测 应该包括中文、英文、数字、分隔线-
     *
     * @param address
     * @return
     */
    public static boolean checkAddress(String address) {
        return Pattern.matches("([-0-9a-zA-Z\u4e00-\u9fa5]){1,150}", address);
    }

    /**
     * QQ号检测
     *
     * @param qq
     * @return
     */
    public static boolean checkQQ(String qq) {
        return Pattern.matches("(0|[1-9]\\d*){6,12}", qq);
    }

    /**
     * 微信号检测
     *
     * @param weixin
     * @return
     */
    public static boolean checkWeiXin(String weixin) {
        return Pattern.matches("[a-zA-Z][0-9a-zA-Z_-]{5,19}", weixin);
    }

    /**
     * 新浪微博号检测
     *
     * @param sina
     * @return
     */
    public static boolean checkSina(String sina) {
        return Pattern.matches(
                "([0-9a-zA-Z-_\u4E00-\u9FA5\uF900-\uFA2D]*){4,24}", sina);
    }

    /**
     * 人人网账号检测
     *
     * @param renren
     * @return
     */
    public static boolean checkRenren(String renren) {
        return Pattern.matches("([-－_\\w\u4E00-\u9FA5\uF900-\uFA2D]){1,24}",
                renren);
    }

    /**
     * 将string转成int
     */
    public static int safeStringToInt(String text) {
        int result = Integer.MAX_VALUE;
        try {
            result = Integer.valueOf(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 当无法正常截取字符串是将返回空串"" ，
     */
    public static String safeSubString(String content, int start, int end) {
        String result = "";
        if (TextUtils.isEmpty(content)) {
            return result;
        }
        if (start >= 0 && start <= end && end <= content.length()) {
            result = content.substring(start, end);
        }
        return result;
    }
}
