package com.taotete.app.ui.user.fragments;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.response.UserInfo;
import com.taotete.app.ui.account.AccountHelper;
import com.taotete.app.ui.account.activity.LoginActivity;
import com.taotete.app.ui.base.fragments.BaseFragment;
import com.taotete.app.ui.settting.SettingActivity;
import com.taotete.app.utils.NetUtils;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户个人界面
 */
public class UserInfoFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.iv_portrait)
    CircleImageView mPortrait;

    @Bind(R.id.tv_nick)
    TextView mTvName;

    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_home;
    }

    @Override
    protected void initData() {
        super.initData();
        requestUserCache();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountHelper.isLogin()) {
            updateView();
        } else {
            hideView();
        }
    }

    /**
     * if user isLogin,request user cache,
     * And then request user info and update user info
     */
    private void requestUserCache() {
        if (AccountHelper.isLogin()) {
            updateView();
            sendRequestData();
        } else {
            hideView();
        }
    }

    /**
     * update the view
     */
    private void updateView() {
        mTvName.setText(AccountHelper.getPhone());
        mTvName.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.GONE);
    }

    private void hideView() {
        mPortrait.setImageResource(R.mipmap.widget_default_face);
        mTvName.setVisibility(View.GONE);
        mBtnLogin.setVisibility(View.VISIBLE);
    }

    /**
     * requestData
     */
    @SuppressLint("CheckResult")
    private void sendRequestData() {
        if (NetUtils.isNetworkAvailable(getActivity()) && AccountHelper.isLogin()) {
            ApiRetrofit.getInstance().getUserInfo(AccountHelper.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean<UserInfo>>() {
                        @Override
                        public void accept(ResultBean<UserInfo> userInfoResultBean) throws Exception {
                            if (userInfoResultBean.isSuccess()) {
                                // 缓存用户信息
                                AccountHelper.update(userInfoResultBean.getResult().getUser().getId(),
                                        userInfoResultBean.getResult().getUser().getNickname(),
                                        userInfoResultBean.getResult().getUser().getAvatar_url(),
                                        userInfoResultBean.getResult().getUser().getNickname());
                                updateView();
                            }
                        }
                    });
        }
    }

    @OnClick({R.id.btn_login, R.id.ly_setting})
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.ly_setting) {
            SettingActivity.show(mContext);
        } else {

            if (!AccountHelper.isLogin()) {
                LoginActivity.show(getActivity());
                return;
            }

            switch (id) {
                case R.id.btn_login:
                    LoginActivity.show(getActivity());
                    break;
            }

        }
    }
}
