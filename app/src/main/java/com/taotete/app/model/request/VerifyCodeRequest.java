package com.taotete.app.model.request;

public class VerifyCodeRequest {

    private String mobile;

    private String code;

    private int intent;

    public VerifyCodeRequest(String mobile, String code, int intent) {
        this.mobile = mobile;
        this.code = code;
        this.intent = intent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }
}
