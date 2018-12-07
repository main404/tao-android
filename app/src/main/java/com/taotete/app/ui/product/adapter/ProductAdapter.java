package com.taotete.app.ui.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.taotete.app.R;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;

public class ProductAdapter extends BaseRecyclerAdapter<String> {

    public ProductAdapter(Context context) {
        super(context, NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ProductViewHolder(mInflater.inflate(R.layout.mall_list_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, String item, int position) {
        ProductViewHolder vh = (ProductViewHolder) holder;
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}
