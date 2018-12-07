package com.taotete.app.ui.product;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.taotete.app.R;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;
import com.taotete.app.ui.product.adapter.ProductAdapter;
import com.taotete.app.widget.TopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ProductsActivity extends BaseActivity implements OnRefreshListener, View.OnClickListener, BaseRecyclerAdapter.OnItemClickListener {

    @Bind(R.id.topbar)
    TopBarView mTopBarView;

    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ProductAdapter mAdapter;

    private boolean mIsRefresh;

    public static void show(Context context) {
        Intent intent = new Intent(context, ProductsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_products;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);
        mTopBarView.setTopBarToStatus(R.mipmap.btn_back_normal, -1, R.string.topbar_title_products, this);
        mAdapter = new ProductAdapter(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        requestData();
    }

    private void requestData() {
        setListData();
    }

    private void setListData() {
        if (mIsRefresh) {
            List<String> list = new ArrayList<>();
            list.add(new String("1"));
            list.add(new String("2"));
            list.add(new String("3"));
            list.add(new String("4"));
            list.add(new String("5"));
            mAdapter.addAll(list);
            mRefreshLayout.finishRefresh();
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int position, long itemId) {
//        mAdapter.getItem(position);
        ProductActivity.show(this);
    }
}
