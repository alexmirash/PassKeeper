package com.mirash.familiar.activity.pin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.familiar.R;

/**
 * @author Mirash
 */
public class CredentialView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;

    public CredentialView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CredentialView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.credential_view, this);
        imageView = findViewById(R.id.credential_image);
        textView = findViewById(R.id.credential_text);
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.CredentialView);
            int resId = attributes.getResourceId(R.styleable.CredentialView_icon, 0);
            if (resId != 0) {
                setImageRes(resId);
            }
            attributes.recycle();
        }
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setImageRes(@DrawableRes int imageRes) {
        imageView.setImageResource(imageRes);
    }
}
