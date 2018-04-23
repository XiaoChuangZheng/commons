package com.yishan.biz.pack.httpclient.domain;

/**
 * <p>功能描述,该部分必须以中文句号结尾。</p>
 * <p>
 * 创建日期 2018年04月23日}
 *
 * @author zhengxiaochuang(zhengxiaochuang @ eefung.com)
 */
public class HttpResult {
    private int statusCode;
    private String message;
    private String result;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
