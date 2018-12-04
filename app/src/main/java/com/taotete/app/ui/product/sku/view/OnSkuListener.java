package com.taotete.app.ui.product.sku.view;

import com.taotete.app.ui.product.sku.bean.Sku;
import com.taotete.app.ui.product.sku.bean.SkuAttribute;

public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttribute unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttribute selectAttribute);

    /**
     * sku选中
     *
     * @param sku
     */
    void onSkuSelected(Sku sku);
}