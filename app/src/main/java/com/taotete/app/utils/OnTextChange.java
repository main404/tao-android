package com.taotete.app.utils;

import android.text.Editable;
import android.text.TextWatcher;

public interface OnTextChange {
    public void addTextChangedListener(TextWatcher watcher);

    public boolean isEmpty();

    public Editable getText();
}
