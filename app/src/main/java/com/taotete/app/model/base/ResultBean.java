package com.taotete.app.model.base;

public class ResultBean<T> {
    public static final int RESULT_SUCCESS = 0;

    private T data;
    private int code;
    private String message;

    public T getResult() {
        return data;
    }

    public void setResult(T result) {
        this.data = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return code == RESULT_SUCCESS;
    }

    public boolean isSuccess() {
        return code == RESULT_SUCCESS && data != null;
    }

    @Override
    public String toString() {
        return "code:" + code
                + " + message:" + message
                + " + result:" + (data != null ? data.toString() : null);
    }
}
