package com.mirash.familiar.tool;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * @author Mirash
 */
class LabelString implements CharSequence {
    private final String str;

    public LabelString(String label, String value) {
        str = TextUtils.isEmpty(value) ? "" : label + ": " + value;
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int i) {
        return str.charAt(i);
    }

    @NonNull
    @Override
    public CharSequence subSequence(int i, int i1) {
        return str.subSequence(i, i1);
    }

    @NonNull
    @Override
    public String toString() {
        return str;
    }
}
