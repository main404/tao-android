package com.taotete.app.ui.account.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.api.MyApi;
import com.taotete.app.utils.InputCheck;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.NetUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.utils.ViewStyleUtil;
import com.taotete.app.widget.LoginEditText;
import com.taotete.app.widget.ValidePhoneView;
import com.taotete.app.model.response.VerifyCodeResponse;
import com.taotete.app.ui.account.base.AccountBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 找回密码
 */
public class RetrieveActivity extends AccountBaseActivity implements View.OnClickListener {

    @Bind(R.id.ly_bar)
    LinearLayout mLlBar;

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.et_retrieve_tel)
    LoginEditText mEtRetrieveTel;

    @Bind(R.id.et_retrieve_code_input)
    LoginEditText mEtRetrieveCodeInput;

    @Bind(R.id.retrieve_sms_call)
    ValidePhoneView mTvRetrieveSmsCall;

    @Bind(R.id.bt_retrieve_submit)
    Button mBtRetrieveSubmit;

    private boolean mMachPhoneNum;

    private String phone = "";

    /**
     * show the retrieve activity
     *
     * @param context context
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, RetrieveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_retrieve_pwd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTvTitle.setText(R.string.retrieve_pwd_label);

        ViewStyleUtil.editTextBindButton(mBtRetrieveSubmit, mEtRetrieveTel, mEtRetrieveCodeInput);

        mEtRetrieveTel.setText(phone);

        mTvRetrieveSmsCall.setIntent(MyApi.RESET_PWD_INTENT);
        mTvRetrieveSmsCall.setPhoneString(phone);

        mEtRetrieveTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
                mMachPhoneNum = InputCheck.isPhone(phone);
                mTvRetrieveSmsCall.setPhoneString(phone);
            }
        });
    }

    @OnClick({R.id.ib_navigation_back, R.id.bt_retrieve_submit})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_retrieve_submit:
                requestRetrievePwd();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void requestRetrievePwd() {

        final String smsCode = mEtRetrieveCodeInput.getText().toString().trim();
        if (!mMachPhoneNum || TextUtils.isEmpty(smsCode)) {
            return;
        }

        if (!NetUtils.isNetworkAvailable(this)) {
            UIUtils.showToast(R.string.tip_network_error);
            return;
        }

        final String phoneNumber = mEtRetrieveTel.getText().toString().trim();
        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().verifyCode(phoneNumber, smsCode, MyApi.RESET_PWD_INTENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VerifyCodeResponse>() {
                    @Override
                    public void accept(VerifyCodeResponse verifyCodeResponse) throws Exception {
                        MProgressDialog.dismissProgress();
                        int code = verifyCodeResponse.getCode();
                        if (code == 0) {
                            ResetPwdActivity.show(RetrieveActivity.this, phoneNumber, smsCode);
                        } else {
                            UIUtils.showToast(verifyCodeResponse.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getLocalizedMessage());
                        UIUtils.showToast(throwable.getLocalizedMessage());
                        MProgressDialog.dismissProgress();
                    }
                });
    }

    @Override
    protected void onStop() {
        mTvRetrieveSmsCall.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
    }
}
