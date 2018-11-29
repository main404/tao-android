package com.taotete.app.ui.account;

import com.taotete.app.utils.SPUtils;
import com.taotete.app.utils.UIUtils;

/**
 * 账户辅助类，用于更新用户信息和保存当前账户等操作
 */
public class AccountHelper {

    private static final String TAG = AccountHelper.class.getSimpleName();

    private static final String ID = "id";
    private static final String NAME = "nickname";
    private static final String PORTRAIT = "avater_url";
    private static final String PHONE = "mobile";
    private static final String TOKEN = "token";

    public static boolean isLogin() {
        return getId() > 0;
    }

    public static int getId() {
        return SPUtils.getInstance(UIUtils.getContext()).getInt(ID, 0);
    }

    public static String getPhone() {
        return SPUtils.getInstance(UIUtils.getContext()).getString(PHONE, "");
    }

    public static String getName() {
        return SPUtils.getInstance(UIUtils.getContext()).getString(NAME, "");
    }

    public static String getPortrait() {
        return SPUtils.getInstance(UIUtils.getContext()).getString(PORTRAIT, "");
    }


    public static String getToken() {
        return SPUtils.getInstance(UIUtils.getContext()).getString(TOKEN, "");
    }

    public static void save(int id, String name, String portrait, String phone, String token) {
        SPUtils.getInstance(UIUtils.getContext()).putInt(ID, id);
        SPUtils.getInstance(UIUtils.getContext()).putString(NAME, name);
        SPUtils.getInstance(UIUtils.getContext()).putString(PORTRAIT, portrait);
        SPUtils.getInstance(UIUtils.getContext()).putString(PHONE, phone);
        SPUtils.getInstance(UIUtils.getContext()).putString(TOKEN, token);
    }

    public static void update(int id, String name, String portrait, String phone) {
        SPUtils.getInstance(UIUtils.getContext()).putInt(ID, id);
        SPUtils.getInstance(UIUtils.getContext()).putString(NAME, name);
        SPUtils.getInstance(UIUtils.getContext()).putString(PORTRAIT, portrait);
        SPUtils.getInstance(UIUtils.getContext()).putString(PHONE, phone);
    }

    public static void clear() {
        SPUtils.getInstance(UIUtils.getContext()).remove(ID);
        SPUtils.getInstance(UIUtils.getContext()).remove(NAME);
        SPUtils.getInstance(UIUtils.getContext()).remove(PORTRAIT);
        SPUtils.getInstance(UIUtils.getContext()).remove(PHONE);
        SPUtils.getInstance(UIUtils.getContext()).remove(TOKEN);
    }

}
