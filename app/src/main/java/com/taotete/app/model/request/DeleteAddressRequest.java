package com.taotete.app.model.request;

public class DeleteAddressRequest {

    private String token;
    private int id;

    public DeleteAddressRequest(String token, int id) {
        this.token = token;
        this.id = id;
    }
}
