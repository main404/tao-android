package com.taotete.app.ui.settting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.jaeger.library.StatusBarUtil;
import com.taotete.app.R;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.widget.TopBarView;
import com.taotete.app.ui.account.AccountHelper;
import com.taotete.app.widget.dialog.CommonDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.topbar)
    TopBarView mTopBarView;

    @Bind(R.id.setting_line_top)
    View mSettingLineTop;
    @Bind(R.id.setting_line_bottom)
    View mSettingLineBottom;
    @Bind(R.id.fl_exit)
    FrameLayout mCancel;

    public static void show(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);
        mTopBarView.setTopBarToStatus(R.mipmap.btn_back_normal, -1, R.string.topbar_title_setting, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean login = AccountHelper.isLogin();
        if (!login) {
            mCancel.setVisibility(View.INVISIBLE);
            mSettingLineTop.setVisibility(View.INVISIBLE);
            mSettingLineBottom.setVisibility(View.INVISIBLE);
        } else {
            mCancel.setVisibility(View.VISIBLE);
            mSettingLineTop.setVisibility(View.VISIBLE);
            mSettingLineBottom.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.fl_exit})
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.fl_exit:
                onExitClick();
                break;
        }
    }

    /**
     * 举报帖子
     */
    public void onExitClick() {
        CommonDialog dialog = new CommonDialog(this);
        dialog.setCancelable(true);
        dialog.setTitle("提示");
        dialog.setMessage("是否登出账号?");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setNegativeButton(R.string.cancel, null);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AccountHelper.clear();
                UIUtils.showToast(R.string.logout_success_hint);
                mCancel.setVisibility(View.INVISIBLE);
                mSettingLineTop.setVisibility(View.INVISIBLE);
                mSettingLineBottom.setVisibility(View.INVISIBLE);
            }
        });
        dialog.show();
    }

}
