package com.taotete.app.ui.account.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.MD5;
import com.taotete.app.utils.NetUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.utils.ViewStyleUtil;
import com.taotete.app.widget.LoginEditText;
import com.taotete.app.model.response.ResetPwdResponse;
import com.taotete.app.ui.account.base.AccountBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 密码重置
 */
public class ResetPwdActivity extends AccountBaseActivity implements View.OnClickListener {

    public static final String PHONE_NUMBER_KEY = "phoneNumber";
    public static final String SMS_CODE_KEY = "smsCode";

    @Bind(R.id.ly_reset_bar)
    LinearLayout mLlResetBar;

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Bind(R.id.et_reset_pwd)
    LoginEditText mEtResetPwd;

    @Bind(R.id.et_reset_repwd)
    LoginEditText mEtResetRePwd;

    @Bind(R.id.bt_reset_submit)
    Button mBtResetSubmit;

    private String mPhoneNumber;
    private String mSmsCode;

    /**
     * show the resetPwdActivity
     *
     * @param context context
     */
    public static void show(Context context, String phoneNumber, String smsCode) {
        Intent intent = new Intent(context, ResetPwdActivity.class);
        intent.putExtra(PHONE_NUMBER_KEY, phoneNumber);
        intent.putExtra(SMS_CODE_KEY, smsCode);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTvTitle.setText(R.string.topbar_title_reset_pwd);

        ViewStyleUtil.editTextBindButton(mBtResetSubmit, mEtResetPwd, mEtResetRePwd);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra(PHONE_NUMBER_KEY);
        mSmsCode = intent.getStringExtra(SMS_CODE_KEY);

    }

    @OnClick({R.id.ib_navigation_back, R.id.bt_reset_submit, R.id.lay_reset_container})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_reset_submit:
                requestResetPwd();
                break;
            case R.id.lay_reset_container:
                try {
                    hideKeyBoard(getCurrentFocus().getWindowToken());
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void requestResetPwd() {

        String password = mEtResetPwd.getTextString();
        String repassword = mEtResetRePwd.getTextString();

        if (password.length() < 6) {
            UIUtils.showToast("密码至少为6位");
            return;
        }  else if (64 < password.length()) {
            UIUtils.showToast("密码不能大于64位");
            return;
        } else if (!password.equals(repassword)) {
            UIUtils.showToast("两次输入的密码不一致");
            return;
        }

        if (!NetUtils.isNetworkAvailable(this)) {
            UIUtils.showToast(R.string.tip_network_error);
            return;
        }

        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().resetPwd(mPhoneNumber, MD5.get32MD5Str(password), mSmsCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResetPwdResponse>() {
                    @Override
                    public void accept(ResetPwdResponse resetPwdResponse) throws Exception {
                        MProgressDialog.dismissProgress();
                        int code = resetPwdResponse.getCode();
                        if (code == 0) {
                            LoginActivity.show(ResetPwdActivity.this);
                            finish();
                        } else {
                            UIUtils.showToast(resetPwdResponse.getMsg());
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
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
    }
}
