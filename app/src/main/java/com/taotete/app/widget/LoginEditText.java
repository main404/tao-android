package com.taotete.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.taotete.app.R;
import com.taotete.app.utils.DensityUtil;
import com.taotete.app.utils.OnTextChange;

public class LoginEditText extends FrameLayout implements OnTextChange {
    private static final String TAG = "LoginEditText";

    private EditText editText;
    private View topLine;
    private ImageView imageValidfy;
    private boolean showPassword = false;

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        inflate(context, R.layout.login_edit_text, this);
        editText = (EditText) findViewById(R.id.editText);
        topLine = findViewById(R.id.topLine);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoginEditText, 0, 0);
        try {
            boolean showTopLine = a.getBoolean(R.styleable.LoginEditText_topLine, true);
            topLine.setVisibility(showTopLine ? VISIBLE : GONE);

            String hint = a.getString(R.styleable.LoginEditText_hint);
            if (hint == null) {
                hint = "";
            }
            editText.setHint(hint);

            boolean showCaptcha = a.getBoolean(R.styleable.LoginEditText_captcha, false);
            if (showCaptcha) {
                imageValidfy = (ImageView) findViewById(R.id.imageValify);
                imageValidfy.setVisibility(VISIBLE);
                imageValidfy.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestCaptcha();
                    }
                });
                requestCaptcha();
            }

            boolean showPassword = a.getBoolean(R.styleable.LoginEditText_showPassword, false);
            if (showPassword) {
                imageValidfy = (ImageView) findViewById(R.id.imageValify);
                imageValidfy.setVisibility(VISIBLE);
                imageValidfy.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        togglePassword();
                    }
                });
                MarginLayoutParams params = (MarginLayoutParams) imageValidfy.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.setMargins(DensityUtil.dip2px(context, 15), 0, 0, 0);
                imageValidfy.setLayoutParams(params);
                imageValidfy.setImageResource(R.mipmap.ic_password_normal);
            }

            int inputType = a.getInt(R.styleable.LoginEditText_loginInput, 0);
            if (inputType == 1) {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else if (inputType == 2) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            }

            String digits = a.getString(R.styleable.LoginEditText_digits);
            if (digits != null && !digits.isEmpty()) {
                editText.setFilters(new InputFilter[]{
                        DigitsKeyListener.getInstance(digits)
                });
            }

        } finally {
            a.recycle();
        }
    }

    public void setText(String text) {
        if (text == null) {
            return;
        }

        editText.setText(text);
    }

    private void togglePassword() {
        showPassword = !showPassword;

        if (showPassword) {
            imageValidfy.setImageResource(R.mipmap.ic_password_show);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            imageValidfy.setImageResource(R.mipmap.ic_password_normal);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        editText.setSelection(editText.length());
    }

    public ImageView getCaptcha() {
        return imageValidfy;
    }

    public void requestCaptcha() {
        editText.setText("");
    }

    @Override
    public boolean isEmpty() {
        return editText.getText().length() == 0;
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    @Override
    public Editable getText() {
        return editText.getText();
    }

    public String getTextString() {
        return editText.getText().toString();
    }

}
