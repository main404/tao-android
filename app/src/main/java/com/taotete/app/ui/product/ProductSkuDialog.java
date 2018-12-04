package com.taotete.app.ui.product;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.taotete.app.R;
import com.taotete.app.ui.product.bean.Product;
import com.taotete.app.ui.product.sku.bean.Sku;
import com.taotete.app.ui.product.sku.bean.SkuAttribute;
import com.taotete.app.ui.product.sku.view.OnSkuListener;
import com.taotete.app.ui.product.sku.view.SkuSelectScrollView;
import com.taotete.app.utils.NumberUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择商品
 */
public class ProductSkuDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.iv_sku_logo)
    ImageView ivSkuLogo;

    @Bind(R.id.tv_sku_selling_price)
    TextView tvSkuSellingPrice;

    @Bind(R.id.tv_sku_selling_price_unit)
    TextView tvSkuSellingPriceUnit;

    @Bind(R.id.tv_sku_info)
    TextView tvSkuInfo;

    @Bind(R.id.tv_sku_quantity)
    TextView tvSkuQuantity;

    @Bind(R.id.scroll_sku_list)
    SkuSelectScrollView scrollSkuList;

    @Bind(R.id.btn_sku_quantity_minus)
    TextView btnSkuQuantityMinus;

    @Bind(R.id.tv_sku_quantity_input)
    TextView tvSkuQuantityInput;

    @Bind(R.id.btn_sku_quantity_plus)
    TextView btnSkuQuantityPlus;

    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private Context context;
    private Product product;
    private List<Sku> skuList;
    private Callback callback;

    private Sku selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;

    public ProductSkuDialog(@NonNull Context context) {
        this(context, R.style.sku_dialog);
    }

    @SuppressLint("InflateParams")
    public ProductSkuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        View view = getLayoutInflater().inflate(R.layout.dialog_product_sku,
                null);
        ButterKnife.bind(this, view);
        initView();
        super.setContentView(view);
    }

    private void initView() {
        scrollSkuList.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
                Glide.with(context).load(product.getImage()).into(ivSkuLogo);
                tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
                btnSubmit.setEnabled(false);
                String quantity = tvSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(Sku sku) {
                selectedSku = sku;
                Glide.with(context).load(selectedSku.getImage()).into(ivSkuLogo);
                List<SkuAttribute> attributeList = selectedSku.getAttributes();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getValue() + "\"");
                }
                tvSkuInfo.setText("已选：" + builder.toString());
                tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
                btnSubmit.setEnabled(true);
                String quantity = tvSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });
    }

    public void setData(final Product product, Callback callback) {
        this.product = product;
        this.skuList = product.getSkus();
        this.callback = callback;

        priceFormat = context.getString(R.string.comm_price_format);
        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);

        updateSkuData();
        updateQuantityOperator(1);
    }

    private void updateSkuData() {
        scrollSkuList.setSkuList(product.getSkus());

        Sku firstSku = product.getSkus().get(0);
        if (firstSku.getStockQuantity() > 0) {
            selectedSku = firstSku;
            // 选中第一个sku
            scrollSkuList.setSelectedSku(selectedSku);

            Glide.with(context).load(product.getImage()).into(ivSkuLogo);
            tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSellingPrice() / 100)));
            tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
            btnSubmit.setEnabled(selectedSku.getStockQuantity() > 0);
            List<SkuAttribute> attributeList = selectedSku.getAttributes();
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
            Glide.with(context).load(product.getImage()).into(ivSkuLogo);
            tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(product.getSellingPrice() / 100)));
            tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));
            btnSubmit.setEnabled(false);
            tvSkuInfo.setText("请选择：" + skuList.get(0).getAttributes().get(0).getKey());
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            btnSkuQuantityMinus.setEnabled(false);
            btnSkuQuantityPlus.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                btnSkuQuantityMinus.setEnabled(false);
                btnSkuQuantityPlus.setEnabled(true);
            } else if (newQuantity >= selectedSku.getStockQuantity()) {
                btnSkuQuantityMinus.setEnabled(true);
                btnSkuQuantityPlus.setEnabled(false);
            } else {
                btnSkuQuantityMinus.setEnabled(true);
                btnSkuQuantityPlus.setEnabled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    @OnClick({R.id.ib_sku_close, R.id.btn_sku_quantity_minus, R.id.btn_sku_quantity_plus, R.id.btn_submit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_sku_close:
                dismiss();
                break;
            case R.id.btn_sku_quantity_minus:
                minus();
                break;
            case R.id.btn_sku_quantity_plus:
                plus();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void minus() {
        String quantity = tvSkuQuantityInput.getText().toString();
        if (TextUtils.isEmpty(quantity)) {
            return;
        }
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt > 1) {
            String newQuantity = String.valueOf(quantityInt - 1);
            tvSkuQuantityInput.setText(newQuantity);
            updateQuantityOperator(quantityInt - 1);
        }
    }

    private void plus() {
        String quantity = tvSkuQuantityInput.getText().toString();
        if (TextUtils.isEmpty(quantity) || selectedSku == null) {
            return;
        }
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt < selectedSku.getStockQuantity()) {
            String newQuantity = String.valueOf(quantityInt + 1);
            tvSkuQuantityInput.setText(newQuantity);
            updateQuantityOperator(quantityInt + 1);
        }
    }

    private void submit() {
        String quantity = tvSkuQuantityInput.getText().toString();
        if (TextUtils.isEmpty(quantity)) {
            return;
        }
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt > 0 && quantityInt <= selectedSku.getStockQuantity()) {
            callback.onAdded(selectedSku, quantityInt);
            dismiss();
        } else {
            Toast.makeText(getContext(), "商品数量超出库存，请修改数量", Toast.LENGTH_SHORT).show();
        }
    }

    public interface Callback {
        void onAdded(Sku sku, int quantity);
    }
}
