package com.mirash.familiar.activity.pin.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mirash.familiar.R;

/**
 * @author Mirash
 */
public class LoginView extends LinearLayout {
    private CredentialView[] loginViews;
    private View[] spaces;

    public LoginView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.login_view, this);
        loginViews = new CredentialView[]{
                findViewById(R.id.login_section_0),
                findViewById(R.id.login_section_1),
                findViewById(R.id.login_section_2)
        };
        spaces = new View[]{
                findViewById(R.id.space_top),
                findViewById(R.id.space_bot),
        };
    }

    public void setItems(String... values) {
        int count = 0;
        for (String value : values) {
            boolean isFilled = !TextUtils.isEmpty(value);
            if (isFilled) {
                loginViews[count].setText(value);
                loginViews[count].setVisibility(VISIBLE);
                count++;
            }
        }
        for (int i = count; i < values.length; i++) {
            loginViews[i].setVisibility(GONE);
        }
        spaces[0].setVisibility(count > 1 ? VISIBLE : GONE);
        spaces[1].setVisibility(count > 2 ? VISIBLE : GONE);
    }
}
