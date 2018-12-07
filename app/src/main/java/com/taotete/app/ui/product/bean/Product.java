package com.taotete.app.ui.product.bean;

import com.taotete.app.ui.product.sku.bean.Sku;

import java.util.List;

public class Product {
    private String id;
    private String name;
    private String description;
    private String status;
    private String image;
    private long selling_price;
    private long origin_price;
    private String currency_unit;
    private String measurement_unit;
    private int sale_quantity;
    private int stock_quantity;
    private List<String> pics;
    private List<String> medias;
    private List<Sku> sku;
    private Rating rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getSellingPrice() {
        return selling_price;
    }

    public void setSellingPrice(long sellingPrice) {
        this.selling_price = sellingPrice;
    }

    public long getOriginPrice() {
        return origin_price;
    }

    public void setOriginPrice(long originPrice) {
        this.origin_price = originPrice;
    }

    public String getCurrencyUnit() {
        return currency_unit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currency_unit = currencyUnit;
    }

    public String getMeasurementUnit() {
        return measurement_unit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurement_unit = measurementUnit;
    }

    public int getSaleQuantity() {
        return sale_quantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.sale_quantity = saleQuantity;
    }

    public int getStockQuantity() {
        return stock_quantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stock_quantity = stockQuantity;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }

    public List<Sku> getSkus() {
        return sku;
    }

    public void setSkus(List<Sku> skus) {
        this.sku = skus;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
