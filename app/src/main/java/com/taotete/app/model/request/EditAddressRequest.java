package com.taotete.app.model.request;

public class EditAddressRequest {

    private String token;
    private int id;
    private String name;
    private String mobile;
    private String area;
    private String address;
    private int is_default;

    public EditAddressRequest(String token, int id, String name, String mobile, String area, String address, int is_default) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.area = area;
        this.address = address;
        this.is_default = is_default;
    }
}
