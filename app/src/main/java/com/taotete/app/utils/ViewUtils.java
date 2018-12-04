package com.taotete.app.utils;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

/**
 * View工具类
 */
public class ViewUtils {

    /***
     * 设置TextView的划线状态
     */
    public static void setTextViewLineFlag(TextView tv, int flags) {
        tv.getPaint().setFlags(flags);
        tv.invalidate();
    }

    public static int generateViewId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return View.generateViewId();
        } else {
            return UUID.randomUUID().hashCode();
        }
    }
}
