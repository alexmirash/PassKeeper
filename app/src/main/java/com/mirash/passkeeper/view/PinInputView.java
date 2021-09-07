package com.mirash.passkeeper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.mirash.passkeeper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */
public class PinInputView extends LinearLayout {
    private List<TextView> buttons;
    private TextView buttonBackspaces;
    private PinCallback callback;

    public PinInputView(Context context) {
        super(context);
        init();
    }


    public PinInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PinInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.pin_input_view, this);
        buttons = new ArrayList<>(10);
        @IdRes int[] buttonIds = new int[]{
                R.id.pin_button_0,
                R.id.pin_button_1,
                R.id.pin_button_2,
                R.id.pin_button_3,
                R.id.pin_button_4,
                R.id.pin_button_5,
                R.id.pin_button_6,
                R.id.pin_button_7,
                R.id.pin_button_8,
                R.id.pin_button_9,
        };
        for (int i = 0; i < buttonIds.length; i++) {
            initInputButton(buttonIds[i], i);
        }
        buttonBackspaces = findViewById(R.id.pin_button_backspace);
        buttonBackspaces.setOnClickListener(view -> {
            if (callback != null) callback.onBackspaceClick();
        });
    }

    private void initInputButton(@IdRes int buttonId, int digit) {
        TextView button = findViewById(buttonId);
        buttons.add(button);
        button.setOnClickListener(view -> {
            if (callback != null) callback.onDigitClick(digit);
        });
    }

    public void setCallback(PinCallback callback) {
        this.callback = callback;
    }

    public interface PinCallback {
        void onDigitClick(int digit);

        void onBackspaceClick();
    }
}