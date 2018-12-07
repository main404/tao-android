package com.taotete.app.ui.product.sku.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Sku implements Parcelable {
    private String id;
    private String image;
    private int stock_quantity;
    private boolean in_stock;
    private long origin_price;
    private long selling_price;
    private List<SkuAttribute> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStockQuantity() {
        return stock_quantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stock_quantity = stockQuantity;
    }

    public boolean isInStock() {
        return in_stock;
    }

    public void setInStock(boolean inStock) {
        this.in_stock = inStock;
    }

    public long getOriginPrice() {
        return origin_price;
    }

    public void setOriginPrice(long originPrice) {
        this.origin_price = originPrice;
    }

    public long getSellingPrice() {
        return selling_price;
    }

    public void setSellingPrice(long sellingPrice) {
        this.selling_price = sellingPrice;
    }

    public List<SkuAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SkuAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id='" + id + '\'' +
                ", mainImage='" + image + '\'' +
                ", stockQuantity=" + stock_quantity +
                ", inStock=" + in_stock +
                ", originPrice=" + origin_price +
                ", sellingPrice=" + selling_price +
                ", attributes=" + attributes +
                '}';
    }

    public Sku() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeInt(this.stock_quantity);
        dest.writeByte(this.in_stock ? (byte) 1 : (byte) 0);
        dest.writeLong(this.origin_price);
        dest.writeLong(this.selling_price);
        dest.writeTypedList(this.attributes);
    }

    protected Sku(Parcel in) {
        this.id = in.readString();
        this.image = in.readString();
        this.stock_quantity = in.readInt();
        this.in_stock = in.readByte() != 0;
        this.origin_price = in.readLong();
        this.selling_price = in.readLong();
        this.attributes = in.createTypedArrayList(SkuAttribute.CREATOR);
    }

    public static final Creator<Sku> CREATOR = new Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel source) {
            return new Sku(source);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };
}
