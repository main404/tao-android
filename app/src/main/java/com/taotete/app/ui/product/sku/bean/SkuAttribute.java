package com.taotete.app.ui.product.sku.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SkuAttribute implements Parcelable {
    private String k;
    private String v;

    public SkuAttribute() {
    }

    public SkuAttribute(String key, String value) {
        this.k = key;
        this.v = value;
    }

    public String getKey() {
        return k;
    }

    public void setKey(String key) {
        this.k = key;
    }

    public String getValue() {
        return v;
    }

    public void setValue(String value) {
        this.v = value;
    }

    @Override
    public String toString() {
        return "SkuAttribute{" +
                "key='" + k + '\'' +
                ", value='" + v + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.k);
        dest.writeString(this.v);
    }

    protected SkuAttribute(Parcel in) {
        this.k = in.readString();
        this.v = in.readString();
    }

    public static final Creator<SkuAttribute> CREATOR = new Creator<SkuAttribute>() {
        @Override
        public SkuAttribute createFromParcel(Parcel source) {
            return new SkuAttribute(source);
        }

        @Override
        public SkuAttribute[] newArray(int size) {
            return new SkuAttribute[size];
        }
    };
}
