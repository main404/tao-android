package com.taotete.app.ui.user.data;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.taotete.app.R;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.widget.TopBarView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的资料界面 + 修改功能
 */
public class MyDataActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.topbar)
    TopBarView mTopBarView;

    public static void show(Context context) {
        Intent intent = new Intent(context, MyDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_data;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTopBarView.setTopBarToStatus(R.id.btn_left, -1, R.string.actionbar_title_my_data, this);
    }

    @OnClick({R.id.ll_nickname, R.id.ll_phone_set_password})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.ll_phone_set_password:
                break;
        }
    }
}
