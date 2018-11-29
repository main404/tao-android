package com.taotete.app.ui.account.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.api.MyApi;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.MD5;
import com.taotete.app.utils.NetUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.utils.ViewStyleUtil;
import com.taotete.app.widget.LoginEditText;
import com.taotete.app.widget.ValidePhoneView;
import com.taotete.app.model.response.RegisterResponse;
import com.taotete.app.ui.account.base.AccountBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 手机注册
 */
public class PhoneRegisterActivity extends AccountBaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.et_register_tel)
    LoginEditText mEtRegisterTel;

    @Bind(R.id.et_register_pwd)
    LoginEditText mEtRegisterPwd;

    @Bind(R.id.et_register_auth_code)
    LoginEditText mEtRegisterAuthCode;

    @Bind(R.id.tv_register_sms_call)
    ValidePhoneView mTvRegisterSmsCall;

    @Bind(R.id.bt_register_submit)
    Button mBtRegisterSubmit;

    private String phone = "";

    public static void show(Context context) {
        Intent intent = new Intent(context, PhoneRegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_phone_register;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTvTitle.setText(R.string.topbar_title_phone_register);

        ViewStyleUtil.editTextBindButton(mBtRegisterSubmit, mEtRegisterTel, mEtRegisterPwd, mEtRegisterAuthCode);

        mEtRegisterTel.setText(phone);

        mTvRegisterSmsCall.setIntent(MyApi.REGISTER_INTENT);
        mTvRegisterSmsCall.setPhoneString(phone);

        mEtRegisterTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
                mTvRegisterSmsCall.setPhoneString(phone);
            }
        });
    }

    @OnClick({R.id.ib_navigation_back, R.id.bt_register_submit})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_register_submit:
                requestRegister();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void requestRegister() {

        String phone = mEtRegisterTel.getText().toString().trim();
        String smsCode = mEtRegisterAuthCode.getText().toString().trim();
        String pwd = mEtRegisterPwd.getText().toString().trim();

        if (pwd.length() < 6) {
            UIUtils.showToast("密码至少为6位");
            return;
        }  else if (64 < pwd.length()) {
            UIUtils.showToast("密码不能大于64位");
            return;
        }

        if (!NetUtils.isNetworkAvailable(this)) {
            UIUtils.showToast(R.string.tip_network_error);
            return;
        }

        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().register(phone, MD5.get32MD5Str(pwd), smsCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        MProgressDialog.dismissProgress();
                        int code = registerResponse.getCode();
                        if (code == 0) {
                            UIUtils.showToast(R.string.register_success_hint);
                            sendLocalReceiver();
                            finish();
                        } else {
                            UIUtils.showToast(registerResponse.getMsg());
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
        mTvRegisterSmsCall.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
    }
}
