package com.taotete.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.maning.mndialoglibrary.MToast;
import com.taotete.app.app.BaseApp;

/**
 * 和ui相关的工具类
 */
public class UIUtils {

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApp.context();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }


    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static void showToast(String message) {
        MToast.makeTextShort(getContext(), message);
    }

    public static void showToast(int message) {
        MToast.makeTextShort(getContext(), getContext().getString(message));
    }

    public static void closeKeyboard(EditText view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void openKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null) return;
        View mFocusView = view;

        Context context = view.getContext();
        if (context != null && context instanceof Activity) {
            Activity activity = ((Activity) context);
            mFocusView = activity.getCurrentFocus();
        }
        if (mFocusView == null) return;
        mFocusView.clearFocus();
        InputMethodManager manager = (InputMethodManager) mFocusView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
