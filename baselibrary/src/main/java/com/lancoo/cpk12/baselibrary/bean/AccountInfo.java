package com.lancoo.cpk12.baselibrary.bean;

/**
 * @Author: 葛雪磊
 * @Eail: 1739037476@qq.com
 * @Data: 2019-09-15
 * @Description: 保存的账号信息
 */
public class AccountInfo {
    private String accountId;
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
