package com.taotete.app.model.request;

public class ResetPwdRequest {

    private String mobile;
    private String password;
    private String code;

    public ResetPwdRequest(String mobile, String password, String code) {
        this.mobile = mobile;
        this.password = password;
        this.code = code;
    }
}
