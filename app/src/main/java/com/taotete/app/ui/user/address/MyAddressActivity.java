package com.taotete.app.ui.user.address;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.response.AddressListResponse;
import com.taotete.app.ui.account.AccountHelper;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.widget.TopBarView;
import com.taotete.app.widget.dialog.CommonDialog;
import com.taotete.app.widget.empty.EmptyLayout;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, MyAddressAdapter.onOptClickListenter {

    @Bind(R.id.topbar)
    TopBarView mTopBarView;

    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private MyAddressAdapter mAdapter;

    protected boolean mIsRefresh;

    public static void show(Context context) {
        context.startActivity(new Intent(context, MyAddressActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);
        mTopBarView.setTopBarToStatus(R.mipmap.btn_back_normal, R.mipmap.ic_add, R.string.topbar_title_address, this);
        mAdapter = new MyAddressAdapter(this);
        mAdapter.setListenter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsRefresh = true;
        requestData();
    }

    @SuppressLint("CheckResult")
    private void requestData() {
        ApiRetrofit.getInstance().getAddressList(AccountHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<AddressListResponse>>() {
                    @Override
                    public void accept(ResultBean<AddressListResponse> addressListResponseResultBean) throws Exception {
                        MProgressDialog.dismissProgress();
                        if (addressListResponseResultBean != null
                                && addressListResponseResultBean.isSuccess()
                                && addressListResponseResultBean.getResult().getList() != null
                                && addressListResponseResultBean.getResult().getList().size() > 0) {
                            setListData(addressListResponseResultBean);
                        } else {
                            onLoadingFailure();
                        }
                        onLoadingFinish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        UIUtils.showToast(throwable.getLocalizedMessage());
                        MProgressDialog.dismissProgress();
                        onLoadingFinish();
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mIsRefresh = true;
        requestData();
    }

    private void setListData(ResultBean<AddressListResponse> resultBean) {
        if (mIsRefresh) {
            mAdapter.clear();
            mAdapter.addAll(resultBean.getResult().getList());
        } else {
            mAdapter.addAll(resultBean.getResult().getList());
        }
    }

    private void onLoadingFinish() {
        mRefreshLayout.finishRefresh();
        mIsRefresh = false;
    }

    protected void onLoadingFailure() {
        if (mAdapter.getItems().size() == 0) {
            mErrorLayout.setErrorType(EmptyLayout.NODATA);
        }
    }

    @Override
    public void onSetDefault(AddressListResponse.ListBean address) {
        setDefaultShipAddress(address);
    }

    @Override
    public void onEdit(AddressListResponse.ListBean address) {
        ModifyAddressActivity.show(MyAddressActivity.this, address, ModifyAddressActivity.TYPE_MODIFY);
    }

    @Override
    public void onDelete(final AddressListResponse.ListBean address) {
        CommonDialog dialog = new CommonDialog(MyAddressActivity.this);
        dialog.setCancelable(true);
        dialog.setMessage("是否删除地址?");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setNegativeButton(R.string.cancel, null);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteAddress(address);
            }
        });
        dialog.show();
    }

    @SuppressLint("CheckResult")
    private void setDefaultShipAddress(AddressListResponse.ListBean address) {
        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().editAddress(AccountHelper.getToken(), address.getId(), address.getName(), address.getMobile(), address.getArea(), address.getAddress(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        MProgressDialog.dismissProgress();
                        if (resultBean.isOk()) {
                            mIsRefresh = true;
                            requestData();
                        } else {
                            UIUtils.showToast(resultBean.getMessage());
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

    @SuppressLint("CheckResult")
    private void deleteAddress(final AddressListResponse.ListBean address) {
        MProgressDialog.showProgress(this);
        ApiRetrofit.getInstance().deleteAddress(AccountHelper.getToken(), address.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean>() {
                    @Override
                    public void accept(ResultBean resultBean) throws Exception {
                        MProgressDialog.dismissProgress();
                        if (resultBean.isOk()) {
                            mAdapter.removeItem(address);
                        } else {
                            UIUtils.showToast(resultBean.getMessage());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                ModifyAddressActivity.show(this, null, ModifyAddressActivity.TYPE_ADD);
                break;
        }
    }
}
