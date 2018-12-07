package com.taotete.app.ui.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.response.ProductResponse;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.ui.product.bean.Product;
import com.taotete.app.ui.product.sku.bean.Sku;
import com.taotete.app.ui.product.sku.bean.SkuAttribute;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.NumberUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.utils.ViewUtils;
import com.taotete.app.widget.PicturesLayout;
import com.taotete.app.widget.ShowMaxImageView;
import com.taotete.app.widget.empty.EmptyLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 商品详情
 */
public class ProductActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.lay_sv)
    ScrollView mViewScroller;

    @Bind(R.id.banner)
    BGABanner banner;

    @Bind(R.id.tv_name)
    TextView tvName;

    @Bind(R.id.tv_description)
    TextView tvDescription;

    @Bind(R.id.tv_selling_price)
    TextView tvSellingPrice;

    @Bind(R.id.tv_origin_price)
    TextView tvOriginPrice;

    @Bind(R.id.tv_sku_info)
    TextView tvSkuInfo;

    @Bind(R.id.fl_image)
    PicturesLayout mLayoutFlow;

    @Bind(R.id.ll_medias)
    LinearLayout mLayoutMedias;

    @Bind(R.id.error_layout)
    EmptyLayout mErrorLayout;

    @Bind(R.id.view_action_back_bg)
    View viewActionBackBg;
    @Bind(R.id.view_action_more_bg)
    View viewActionMoreBg;

    private ProductSkuDialog dialog;

    private Product product;

    private String priceFormat;

    public static void show(Context context) {
        Intent intent = new Intent(context, ProductActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_product;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);

        priceFormat = getString(R.string.comm_price_format);
    }

    @Override
    protected void initData() {
        super.initData();
        requsetData();
    }

    @SuppressLint("CheckResult")
    private void requsetData() {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        ApiRetrofit.getInstance().getProductDetail(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBean<ProductResponse>>() {
                    @Override
                    public void accept(ResultBean<ProductResponse> productResponseResultBean) throws Exception {
                        if (productResponseResultBean.isSuccess()) {
                            fillUI(productResponseResultBean.getResult().getProduct());
                            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                        } else {
                            UIUtils.showToast(productResponseResultBean.getMessage());
                            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getLocalizedMessage());
                        UIUtils.showToast(throwable.getLocalizedMessage());
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                    }
                });
    }

    private void fillUI(Product result) {
        product = result;
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                Glide.with(itemView.getContext())
                        .load(model)
                        .into((ImageView) itemView);
            }
        });
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        tvSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(product.getSellingPrice() / 100)));
        if (product.getOriginPrice() > 0) {
            ViewUtils.setTextViewLineFlag(tvOriginPrice, Paint.STRIKE_THRU_TEXT_FLAG
                    | Paint.ANTI_ALIAS_FLAG);
            tvOriginPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(product.getSellingPrice() / 100)));
        }
        banner.setData(product.getPics(), null);
//        mLayoutFlow.setImage(product.getRating().getPics());
        mLayoutMedias.removeAllViews();
        for (String medias : product.getMedias()) {
            ShowMaxImageView imageView = new ShowMaxImageView(this);
            // 设置布局参数
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp);
            Glide.with(this).load(medias).into(imageView);
            mLayoutMedias.addView(imageView);
        }
        updateSkuData();
    }

    private void updateSkuData() {
        Sku firstSku = product.getSkus().get(0);
        if (firstSku.getStockQuantity() > 0) {
            List<SkuAttribute> attributeList = firstSku.getAttributes();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("　");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append("\"" + attribute.getValue() + "\"");
            }
            tvSkuInfo.setText("已选：" + builder.toString());
        } else {
            tvSkuInfo.setText("请选择：" + product.getSkus().get(0).getAttributes().get(0).getKey());
        }
    }

    /**
     * 加载购物车输了
     */
    public void loadCartSize() {

    }

    @OnClick({R.id.fl_action_back, R.id.ll_show_sku, R.id.ll_action_favor, R.id.btn_add_cart})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fl_action_back:
                finish();
                break;
            case R.id.ll_action_favor:
                break;
            case R.id.ll_show_sku:
            case R.id.btn_add_cart:
                showSkuDialog();
                break;
        }
    }

    private void showSkuDialog() {
        if (dialog == null) {
            dialog = new ProductSkuDialog(this);
            dialog.setData(product, new ProductSkuDialog.Callback() {
                @Override
                public void onAdded(Sku sku, int quantity) {
                    List<SkuAttribute> attributeList = sku.getAttributes();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < attributeList.size(); i++) {
                        if (i != 0) {
                            builder.append("　");
                        }
                        SkuAttribute attribute = attributeList.get(i);
                        builder.append("\"" + attribute.getValue() + "\"");
                    }
                    tvSkuInfo.setText("已选：" + builder.toString());
                }
            });
            dialog.setCancelable(true);
        }
        dialog.show();
    }

    /**
     * 加入购物车
     */
    private void addCart() {

    }
}
