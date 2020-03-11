package com.lancoo.cpk12.cplibrary.bean;

/**
 * @Author: 葛雪磊
 * @Eail: 1739037476@qq.com
 * @Data: 2019-09-11
 * @Description: 新版的接口返回的数据类型
 */
public class BaseResult<T> {
    private Integer StatusCode;
    private String Msg;
    private Integer ErrCode;
    private T Data;

    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Integer getErrCode() {
        return ErrCode;
    }

    public void setErrCode(Integer errCode) {
        ErrCode = errCode;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
