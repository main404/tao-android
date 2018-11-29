package com.taotete.app.model.request;

public class SearchRequest {
    int order;
    String keyword;

    public SearchRequest(int order, String keyword) {
        this.order = order;
        this.keyword = keyword;
    }
}
