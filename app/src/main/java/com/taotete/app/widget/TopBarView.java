package com.taotete.app.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taotete.app.R;

/**
 * 顶部导航
 */
public class TopBarView extends LinearLayout {
    private Context mContext ;
    private ImageView mLeftButton;
    private TextView mMiddleButton;
    private ImageView mRightButton;
    private TextView mLeftText;
    private TextView mRightText;
    private OnClickListener mClickListener;

    /**
     * @param context
     */
    public TopBarView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.titlebar_color)));
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_top_bar, this, true);
        mLeftButton = (ImageView) findViewById(R.id.btn_left);
        mMiddleButton = (TextView) findViewById(R.id.btn_middle);
        mRightButton = (ImageView) findViewById(R.id.btn_right);
        mLeftText = (TextView) findViewById(R.id.text_left);
        mRightText = (TextView) findViewById(R.id.text_right);
    }

    /**
     * 显示正在加载Progressba
     */
    public void showTopProgressbar() {
        mRightButton.setVisibility(View.GONE);
        mRightText.setVisibility(View.GONE);
        ((RelativeLayout)findViewById(R.id.top_progressbar)).setVisibility(View.VISIBLE);
    }

    /**
     * 设置TopBarView 右边按钮的背景
     * @param resId
     */
    public void setRightButtonRes(int resId) {
        if(resId == -1) {
            mRightButton.setVisibility(View.GONE);
            return ;
        }
        int padding = getContext().getResources().getDimensionPixelSize(R.dimen.btn_topbar_paddingHorizontal);
        mRightButton.setImageResource(resId);
        mRightButton.setPadding(padding, 0, padding, 0);
    }

    /**
     * 设置右边按钮的显示文字
     * @param text
     */
    public void setRightButtonText(String text) {
        if(text == null) {
            mRightText.setVisibility(View.GONE);
            return ;
        }
        mRightText.setText(text);
    }

    /**
     * 设置TopBarView 顶部更新提示是否显示
     * @param show
     */
    public void setTopbarUpdatePoint(boolean show) {
        View mTopbarUpdatePoint = findViewById(R.id.topbar_update_point);
        if(show) {
            mTopbarUpdatePoint.setVisibility(View.VISIBLE);
            return ;
        }
        mTopbarUpdatePoint.setVisibility(View.GONE);
    }

    /**
     * 设置TopBarView 右侧按钮的显示
     */
    public void setRightVisible() {
        mRightButton.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.VISIBLE);
        ((RelativeLayout)findViewById(R.id.top_progressbar)).setVisibility(View.GONE);
    }

    /**
     * 设置TopBarView RightPoint是否显示
     * @param show
     */
    public void setTopbarRightPoint(boolean show) {
        View mTopbarRightPoint = findViewById(R.id.right_point);
        if(show) {
            mTopbarRightPoint.setVisibility(View.VISIBLE);
            return ;
        }
        mTopbarRightPoint.setVisibility(View.GONE);
    }

    /**
     * @return the mLeftButton
     */
    public ImageView getLeftButton() {
        return mLeftButton;
    }

    /**
     * @return the mRightButton
     */
    public ImageView getRightButton() {
        return mRightButton;
    }

    /**
     * @return the mLeftText
     */
    public TextView getLeftText() {
        return mLeftText;
    }

    /**
     * @return the mRightText
     */
    public TextView getRightText() {
        return mRightText;
    }

    public void setFront() {
        bringToFront();
    }

    /**
     * 设置MiddleButton 的padding
     * @param padding
     */
    public void setMiddleBtnPadding(int padding) {
        if(mMiddleButton == null) {
            return ;
        }
        mMiddleButton.setPadding(padding, 0, padding, 0);
    }

    /**
     * 右侧按钮是否可用
     * @param enabled
     */
    public void setRightBtnEnable(boolean enabled) {
        mRightButton.setEnabled(enabled);
        mRightText.setEnabled(enabled);
    }

    /**
     * 设置TopBarView 标题
     * @param title
     */
    public void setTitle(CharSequence title) {
        if(TextUtils.isEmpty(title)) {
            mMiddleButton.setVisibility(View.INVISIBLE);
            return ;
        }
        mMiddleButton.setText(title);
        mMiddleButton.setVisibility(View.VISIBLE);
        mMiddleButton.setOnClickListener(mClickListener);
    }

    /**
     * 设置TopBarView 标题
     * @param title
     */
    public void setTitle(String title) {
        if(TextUtils.isEmpty(title)) {
            mMiddleButton.setVisibility(View.INVISIBLE);
            return ;
        }
        mMiddleButton.setText(title);
        mMiddleButton.setVisibility(View.VISIBLE);
        mMiddleButton.setOnClickListener(mClickListener);
    }

    /**
     * 设置标题的背景
     * @param resId
     */
    public void setTitleDrawable(int resId) {
        if(resId == -1) {
            mMiddleButton.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resId), null, null, null);
            return ;
        }
        mMiddleButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    /**
     *
     * @param leftResid
     * @param rightResid
     * @param titleRes
     * @param l
     */
    public void setTopBarToStatus(int leftResid, int rightResid,
                                  int titleRes, OnClickListener l) {
        String str = "";
        if (titleRes != -1) {
            str = getResources().getString(titleRes);
        }
        setTopBarToStatus(leftResid, rightResid, null, null, str, l);
    }

    /**
     * 设置纯图片的按钮TopBarView
     * @param leftResid
     * @param rightResid
     * @param title
     * @param l
     */
    public void setTopBarToStatus(int leftResid, int rightResid,
                                  String title, OnClickListener l) {
        setTopBarToStatus(leftResid, rightResid, null, null, title, l);
    }

    /**
     * 重载方法，设置返回、标题、右侧Action按钮
     * @param leftResid
     * @param rightText
     * @param title
     * @param l
     */
    public void setTopBarToStatus(int leftResid, String rightText,
                                  String title, OnClickListener l) {
        setTopBarToStatus(leftResid, -1, null, rightText, title, l);
    }

    /**
     * 设置TopBarView 属性
     * @param leftResid  左边按钮背景
     * @param rightResid  右边按钮背景
     * @param leftText  左边按钮文字
     * @param rightText 右边按钮文字
     * @param title  标题文字
     * @param l
     */
    public void setTopBarToStatus(int leftResid, int rightResid, String leftText, String rightText, String title, OnClickListener l) {
        mClickListener = l;
        findViewById(R.id.common_top_wrapper).setOnClickListener(mClickListener);
        int padding = getContext().getResources().getDimensionPixelSize(R.dimen.btn_topbar_paddingHorizontal);
        if(leftResid <= 0 || leftText != null) {
            mLeftButton.setVisibility(View.GONE);
            if(leftText != null) {
                mLeftButton.setVisibility(View.GONE);
                mLeftText.setText(leftText);
                mLeftText.setVisibility(View.VISIBLE);
                mLeftText.setOnClickListener(l);
            } else {
                mLeftText.setVisibility(View.GONE);
            }

            if(leftResid > 0) {
                mLeftText.setBackgroundResource(leftResid);
                mLeftText.setPadding(padding, 0, padding, 0);
            }
        } else {
            mLeftButton.setImageResource(leftResid);
            mLeftButton.setPadding(padding, 0, padding, 0);
            mLeftButton.setVisibility(View.VISIBLE);
            mLeftButton.setOnClickListener(l);
        }

        if(rightResid <= 0 || rightText != null) {
            mRightButton.setVisibility(View.GONE);

            if(rightText != null) {
                mRightButton.setVisibility(View.GONE);
                mRightText.setText(rightText);
                mRightText.setVisibility(View.VISIBLE);
                mRightText.setOnClickListener(l);
            } else {
                mRightText.setVisibility(View.GONE);
            }

            if(rightResid > 0) {
                mRightText.setBackgroundResource(rightResid);
                mRightText.setPadding(0, 0, 0, 0);
            }

        } else {
            mRightButton.setImageResource(rightResid);
            mRightButton.setPadding(padding, 0, padding, 0);
            mRightButton.setVisibility(View.VISIBLE);
            mRightButton.setOnClickListener(l);
        }

        setTitle(title);
    }

	public TextView getmMiddleButton() {
		return mMiddleButton;
	}

}
