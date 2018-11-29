package com.taotete.app.ui.account.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.inputmethod.InputMethodManager;

import com.jaeger.library.StatusBarUtil;
import com.taotete.app.R;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.utils.UIUtils;


public class AccountBaseActivity extends BaseActivity {

    public static final String ACTION_ACCOUNT_FINISH_ALL = "app.taotete.com.action.finish.all";
    protected LocalBroadcastManager mManager;
    private BroadcastReceiver mReceiver;
    protected InputMethodManager mInputMethodManager;

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected void initData() {
        super.initData();
        registerLocalReceiver();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            hideKeyBoard(getCurrentFocus().getWindowToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (mManager != null) {
            if (mReceiver != null)
                mManager.unregisterReceiver(mReceiver);
        }
    }

    protected boolean sendLocalReceiver() {
        if (mManager != null) {
            Intent intent = new Intent();
            intent.setAction(ACTION_ACCOUNT_FINISH_ALL);
            return mManager.sendBroadcast(intent);
        }

        return false;
    }

    /**
     * register localReceiver
     */
    private void registerLocalReceiver() {
        if (mManager == null)
            mManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ACCOUNT_FINISH_ALL);
        if (mReceiver == null)
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ACTION_ACCOUNT_FINISH_ALL.equals(action)) {
                        finish();
                    }
                }
            };
        mManager.registerReceiver(mReceiver, filter);
    }

    protected void hideKeyBoard(IBinder windowToken) {
        if(windowToken == null){
            return;
        }
        InputMethodManager inputMethodManager = this.mInputMethodManager;
        if (inputMethodManager == null) return;
        boolean active = inputMethodManager.isActive();
        if (active) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * request network error
     *
     * @param throwable throwable
     */
    protected void requestFailureHint(Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
        UIUtils.showToast(R.string.request_error_hint);
    }

}
