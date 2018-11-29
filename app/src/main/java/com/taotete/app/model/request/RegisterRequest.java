package com.taotete.app.model.request;

public class RegisterRequest {

    private String mobile;

    private String password;

    private String code;

    public RegisterRequest(String mobile, String password, String code) {
        this.mobile = mobile;
        this.password = password;
        this.code = code;
    }
}
