package com.taotete.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.api.MyApi;
import com.taotete.app.model.response.SendCodeResponse;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.OnTextChange;
import com.taotete.app.utils.UIUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ValidePhoneView extends TextView {

    OnTextChange editPhone;
    String inputPhone = "";

    private int intent = MyApi.REGISTER_INTENT;

    public ValidePhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhoneMessage();
            }
        });
    }

    public void setEditPhone(OnTextChange edit) {
        editPhone = edit;
    }

    public void setPhoneString(String phone) {
        inputPhone = phone;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }

    public void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

        public void onTick(long millisUntilFinished) {
            ValidePhoneView.this.setText(String.format("%d秒", millisUntilFinished / 1000));
            ValidePhoneView.this.setEnabled(false);
        }

        public void onFinish() {
            ValidePhoneView.this.setEnabled(true);
            ValidePhoneView.this.setText("发送验证码");
        }
    };

    public void onStop() {
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    @SuppressLint("CheckResult")
    void sendPhoneMessage() {
        if (inputPhone.isEmpty() && editPhone == null) {
            Log.e("", "editPhone is null");
            return;
        }

        String phoneString;
        if (editPhone != null) {
            phoneString = editPhone.getText().toString();
        } else {
            phoneString = inputPhone;
        }

        if (!InputCheck.checkPhone(getContext(), phoneString)) return;

        ApiRetrofit.getInstance().sendCode(phoneString, intent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SendCodeResponse>() {
                    @Override
                    public void accept(SendCodeResponse sendCodeResponse) throws Exception {
                        if (sendCodeResponse.getCode() == 0) {
                            UIUtils.showToast("验证码已发送");
                        } else {
                            UIUtils.showToast(sendCodeResponse.getMsg());
                            countDownTimer.cancel();
                            countDownTimer.onFinish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getLocalizedMessage());
                        UIUtils.showToast(throwable.getLocalizedMessage());
                        countDownTimer.cancel();
                        countDownTimer.onFinish();
                }
        });
        countDownTimer.start();
    }
}
