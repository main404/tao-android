package com.taotete.app.ui.user.address;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taotete.app.R;
import com.taotete.app.model.response.AddressListResponse;
import com.taotete.app.ui.base.adapter.BaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAddressAdapter extends BaseRecyclerAdapter<AddressListResponse.ListBean> {

    private onOptClickListenter listenter;

    public void setListenter(onOptClickListenter listenter) {
        this.listenter = listenter;
    }

    public MyAddressAdapter(Context context) {
        super(context, NEITHER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new MyAddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_address, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final AddressListResponse.ListBean item, int position) {
        final MyAddressViewHolder vh = (MyAddressViewHolder) holder;
        vh.mTvSetDefault.setSelected(item.getStatus() == 1);
        vh.mTvShipName.setText(item.getName() + "    " +  item.getMobile());
        vh.mTvShipAddress.setText(item.getArea() + item.getAddress());

        vh.mTvSetDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vh.mTvSetDefault.isSelected()) {
                    return;
                }
                if (listenter != null) {
                    listenter.onSetDefault(item);
                }
            }
        });
        vh.mTvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenter != null) {
                    listenter.onEdit(item);
                }
            }
        });
        vh.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenter != null) {
                    listenter.onDelete(item);
                }
            }
        });
    }

    static class MyAddressViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_ship_name)
        TextView mTvShipName;

        @Bind(R.id.tv_ship_address)
        TextView mTvShipAddress;

        @Bind(R.id.tv_set_default)
        TextView mTvSetDefault;

        @Bind(R.id.tv_edit)
        TextView mTvEdit;

        @Bind(R.id.tv_delete)
        TextView mTvDelete;

        public MyAddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 对应操作接口
     */
    interface onOptClickListenter {
        void onSetDefault(AddressListResponse.ListBean address);
        void onEdit(AddressListResponse.ListBean address);
        void onDelete(AddressListResponse.ListBean address);
    }
}
