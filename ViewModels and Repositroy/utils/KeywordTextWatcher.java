package com.ep.ai.hd.live.wallpaper.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class KeywordTextWatcher implements TextWatcher {

    private EditText editText;
    private boolean isCommaAdded = false;

    public KeywordTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isCommaAdded) {
            String text = s.toString();
            if (!text.isEmpty() && !text.endsWith(",")) {
                editText.removeTextChangedListener(this);
                editText.setText(text + ",");
                editText.setSelection(editText.getText().length());
                isCommaAdded = true;
                editText.addTextChangedListener(this);
            }
        } else {
            isCommaAdded = false;
        }
    }
}
