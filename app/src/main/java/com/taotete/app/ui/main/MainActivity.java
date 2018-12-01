package com.taotete.app.ui.main;

import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.taotete.app.R;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.ui.main.nav.NavFragment;
import com.taotete.app.ui.main.nav.NavigationButton;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavFragment.OnNavigationReselectListener {

    @Bind(R.id.activity_main_ui)
    LinearLayout mMainUi;

    private NavFragment mNavBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main_ui;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);
        FragmentManager manager = getSupportFragmentManager();
        mNavBar = ((NavFragment) manager.findFragmentById(R.id.fag_nav));
        mNavBar.setup(this, manager, R.id.main_container, this);
    }

    @Override
    public void onReselect(NavigationButton navigationButton) {

    }
}
