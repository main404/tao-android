package com.taotete.app.model.request;

public class SendCodeRequest {

    private String mobile;
    private int intent;

    public SendCodeRequest(String mobile, int intent) {
        this.intent = intent;
        this.mobile = mobile;
    }

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
