package com.taotete.app.ui.category;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.response.CategoryResponse;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;
import com.taotete.app.ui.base.fragments.BaseFragment;
import com.taotete.app.ui.category.adapter.SecondCategoryAdapter;
import com.taotete.app.ui.category.adapter.TopCategoryAdapter;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 分类
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.rv_top_category)
    RecyclerView mRvTopCategory;
    @Bind(R.id.rv_secont_category)
    RecyclerView mRvSecontCategory;
    private TopCategoryAdapter mAdapterTopCategory;
    private SecondCategoryAdapter mAdapterSecondCategory;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRvTopCategory.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSecontCategory.setLayoutManager(new GridLayoutManager(mContext, 3));
        mAdapterTopCategory = new TopCategoryAdapter(mContext);
        mAdapterSecondCategory = new SecondCategoryAdapter(mContext);
        mRvTopCategory.setAdapter(mAdapterTopCategory);
        mRvSecontCategory.setAdapter(mAdapterSecondCategory);
        mAdapterTopCategory.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                mAdapterTopCategory.setSelectedPosition(position);
                mAdapterSecondCategory.resetItem(mAdapterTopCategory.getItem(position).getSecond());
            }
        });
        mAdapterSecondCategory.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {

            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        super.initData();
        ApiRetrofit.getInstance().getCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<CategoryResponse>>() {
                    @Override
                    public void accept(ResultBean<CategoryResponse> categoryResponseResultBean) throws Exception {
                        if (categoryResponseResultBean.getCode() == 0 && categoryResponseResultBean.getResult() != null) {
                            mAdapterTopCategory.addAll(categoryResponseResultBean.getResult().getTop());
                            mAdapterSecondCategory.resetItem(categoryResponseResultBean.getResult().getTop().get(0).getSecond());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
        }
    }
}
