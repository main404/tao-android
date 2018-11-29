package com.taotete.app.ui.account.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.utils.MD5;
import com.taotete.app.utils.NetUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.model.response.LoginResponse;
import com.taotete.app.ui.account.AccountHelper;
import com.taotete.app.ui.account.base.AccountBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 */
public class LoginActivity extends AccountBaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    @Bind(R.id.ly_bar)
    LinearLayout mLayBackBar;

    @Bind(R.id.ll_login_username)
    LinearLayout mLlLoginUsername;
    @Bind(R.id.et_login_username)
    EditText mEtLoginUsername;
    @Bind(R.id.iv_login_username_del)
    ImageView mIvLoginUsernameDel;

    @Bind(R.id.ll_login_pwd)
    LinearLayout mLlLoginPwd;
    @Bind(R.id.et_login_pwd)
    EditText mEtLoginPwd;
    @Bind(R.id.iv_login_pwd_del)
    ImageView mIvLoginPwdDel;

    @Bind(R.id.bt_login_submit)
    Button mBtLoginSubmit;

    /**
     * show the login activity
     *
     * @param context context
     */
    public static void show(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString().trim();
                if (username.length() > 0) {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mLlLoginUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
                }

                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

            }
        });

        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    mLlLoginPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                } else {
                    mIvLoginPwdDel.setVisibility(View.INVISIBLE);
                }

                String username = mEtLoginUsername.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    UIUtils.showToast(R.string.message_username_null);
                }
                String pwd = mEtLoginPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }

    @OnClick({R.id.ib_navigation_back, R.id.et_login_username, R.id.et_login_pwd,
            R.id.iv_login_username_del, R.id.iv_login_pwd_del,
            R.id.tv_login_register, R.id.tv_login_forget_pwd,
            R.id.bt_login_submit, R.id.lay_login_container})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.tv_login_forget_pwd:
                // 忘记密码
                RetrieveActivity.show(this);
                break;
            case R.id.bt_login_submit:
                // 用户登录
                loginRequest();
                break;
            case R.id.iv_login_username_del:
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                mEtLoginPwd.setText(null);
                break;
            case R.id.tv_login_register:
                PhoneRegisterActivity.show(this);
                break;
            case R.id.lay_login_container:
                try {
                    hideKeyBoard(getCurrentFocus().getWindowToken());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void loginRequest() {
        String tempUsername = mEtLoginUsername.getText().toString().trim();
        String tempPwd = mEtLoginPwd.getText().toString().trim();

        if (!TextUtils.isEmpty(tempPwd) && !TextUtils.isEmpty(tempUsername)) {
            // 登录成功,请求数据进入用户个人中心页面
            requestLogin(tempUsername, tempPwd);

            if (NetUtils.isNetworkAvailable(this)) {
                requestLogin(tempUsername, tempPwd);
            } else {
                UIUtils.showToast(R.string.footer_type_net_error);
            }
        } else {
            UIUtils.showToast(R.string.login_input_username_hint_error);
        }
    }

    @SuppressLint("CheckResult")
    private void requestLogin(String tempUsername, String tempPwd) {
        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().login(tempUsername, MD5.get32MD5Str(tempPwd))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        MProgressDialog.dismissProgress();
                        if (loginResponse != null && loginResponse.getCode() == 0) {
                            AccountHelper.save(loginResponse.getData().getUsers().getId(),
                                    loginResponse.getData().getUsers().getNickname(),
                                    loginResponse.getData().getUsers().getAvatar_url(),
                                    loginResponse.getData().getUsers().getNickname(),
                                    loginResponse.getData().getToken());
                            logSucceed();
                        } else {
                            String message = loginResponse.getMsg();
                            UIUtils.showToast(message);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        UIUtils.showToast(throwable.getLocalizedMessage());
                        MProgressDialog.dismissProgress();
                    }
                });
    }

    private void logSucceed() {
        View view;
        if ((view = getCurrentFocus()) != null) {
            hideKeyBoard(view.getWindowToken());
        }
        UIUtils.showToast(R.string.login_success_hint);
        setResult(RESULT_OK);
        sendLocalReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
    }
}
