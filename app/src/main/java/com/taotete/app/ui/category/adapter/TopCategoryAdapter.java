package com.taotete.app.ui.category.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotete.app.R;
import com.taotete.app.model.response.CategoryResponse;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;

/**
 * 一级商品分类 Adapter
 */
public class TopCategoryAdapter extends BaseRecyclerAdapter<CategoryResponse.TopBean> {

    public TopCategoryAdapter(Context context) {
        super(context, NEITHER);
        mSelectedPosition = 0;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new TopCategoryHolder(mInflater.inflate(R.layout.layout_top_category_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, CategoryResponse.TopBean item, int position) {
        TopCategoryHolder h = (TopCategoryHolder) holder;
        h.itemView.setBackgroundColor(position == mSelectedPosition ? 0xFFFFFFFF : 0xFFF6F6F6);
        h.mTextName.setTextColor(position == mSelectedPosition ? 0xFFFF5959 : 0xFF333333);
        h.mTextName.setText(item.getName());
        h.mViewSelected.setVisibility(position == mSelectedPosition ? View.VISIBLE : View.INVISIBLE);
    }

    private static class TopCategoryHolder extends RecyclerView.ViewHolder {
        TextView mTextName;
        View mViewSelected;

        public TopCategoryHolder(View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.tv_name);
            mViewSelected = itemView.findViewById(R.id.viewSelected);
        }
    }
}
