package com.taotete.app.ui.product.bean;

public class Rating {
    private String username;
    private long rateTime;
    private int rateType;
    private String text;
    private String avatar;
    private String[] pics;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRateTime() {
        return rateTime;
    }

    public void setRateTime(long rateTime) {
        this.rateTime = rateTime;
    }

    public int getRateType() {
        return rateType;
    }

    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String[] getPics() {
        return pics;
    }
}
