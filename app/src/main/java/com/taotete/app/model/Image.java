package com.taotete.app.model;

import android.text.TextUtils;

import net.oschina.common.utils.CollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Image implements Serializable {
    private String thumb;
    private String href;
    private String name;
    private int type;
    private int w;
    private int h;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public static Image create(String href) {
        Image image = new Image();
        image.thumb = href;
        image.href = href;
        return image;
    }

    public static String[] getImagePath(Image[] images) {
        if (images == null || images.length == 0)
            return null;

        List<String> paths = new ArrayList<>();
        for (Image image : images) {
            if (check(image))
                paths.add(image.href);
        }

        return CollectionUtil.toArray(paths, String.class);
    }

    public static boolean check(Image image) {
        return image != null
                && !TextUtils.isEmpty(image.getThumb())
                && !TextUtils.isEmpty(image.getHref());
    }
}
