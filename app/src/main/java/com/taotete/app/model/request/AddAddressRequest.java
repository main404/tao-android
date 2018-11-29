package com.taotete.app.model.request;

public class AddAddressRequest {

    private String token;
    private String name;
    private String mobile;
    private String area;
    private String address;

    public AddAddressRequest(String token, String name, String mobile, String area, String address) {
        this.token = token;
        this.name = name;
        this.mobile = mobile;
        this.area = area;
        this.address = address;
    }
}
