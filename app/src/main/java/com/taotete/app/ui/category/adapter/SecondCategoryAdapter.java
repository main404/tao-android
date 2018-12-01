package com.taotete.app.ui.category.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taotete.app.R;
import com.taotete.app.model.response.CategoryResponse;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;

/**
 * 二级商品分类Adapter
 */
public class SecondCategoryAdapter extends BaseRecyclerAdapter<CategoryResponse.TopBean.SecondBean> {

    public SecondCategoryAdapter(Context context) {
        super(context, NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new SecondCategoryHolder(mInflater.inflate(R.layout.layout_second_category_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, CategoryResponse.TopBean.SecondBean item, int position) {
        SecondCategoryHolder h = (SecondCategoryHolder) holder;
        h.mSecondCategoryNameTv.setText(item.getName());
        Glide.with(mContext).load(item.getImgUrl()).into(h.mCategoryIconIv);
    }

    private static class SecondCategoryHolder extends RecyclerView.ViewHolder {
        TextView mSecondCategoryNameTv;
        ImageView mCategoryIconIv;

        public SecondCategoryHolder(View itemView) {
            super(itemView);
            mCategoryIconIv = itemView.findViewById(R.id.mCategoryIconIv);
            mSecondCategoryNameTv = itemView.findViewById(R.id.mSecondCategoryNameTv);
        }
    }
}
