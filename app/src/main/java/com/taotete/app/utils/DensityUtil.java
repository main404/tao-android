package com.taotete.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 尺寸工具
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context
     * @return
     * @Description: (获取屏幕宽度)
     */
    public static int screenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * @param context
     * @return
     * @Description: (获取屏幕高度)
     */
    public static int screenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }
}
