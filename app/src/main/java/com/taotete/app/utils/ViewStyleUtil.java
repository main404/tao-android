package com.taotete.app.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class ViewStyleUtil {

    private static InputRequest sNoEmptyRequest = new InputRequest() {
        @Override
        public boolean isCurrectFormat(String s) {
            return s.length() > 0;
        }
    };

    /**
     * 只有所有 EditText 都填写了，Button 才是可点击状态
     * @param button
     * @param edits
     */
    public static void editTextBindButton(View button, OnTextChange... edits) {
        editTextBindButton(button, sNoEmptyRequest, edits);
    }

    public static void editTextBindButton(final View button, final InputRequest request, final OnTextChange... edits) {
        for (OnTextChange edit : edits) {
            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    updateStyle(button, request, edits);
                }
            });
        }

        updateStyle(button, request, edits);
    }

    private static void updateStyle(View button, InputRequest request, OnTextChange[] edits) {
        for (OnTextChange item : edits) {
            if (item instanceof View) {
                View v = (View) item;
                if (v.getVisibility() != View.VISIBLE) {
                    continue;
                }
            }

            String input = item.getText().toString();
            if (!request.isCurrectFormat(input)) {
                button.setEnabled(false);
                return;
            }
        }
        button.setEnabled(true);
    }
}
