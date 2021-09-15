package com.mirash.familiar.activity.pin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mirash.familiar.Const;
import com.mirash.familiar.view.PinInputView;

import java.util.Stack;

/**
 * @author Mirash
 */
public class PinActivityModel extends AndroidViewModel implements PinInputView.PinCallback {
    private final MutableLiveData<Integer> pinCodeSizeLiveData;
    private final MutableLiveData<String> pinCodeCheckLiveData;
    private final Stack<Integer> pinCode;

    public PinActivityModel(@NonNull Application application) {
        super(application);
        pinCode = new Stack<>();
        pinCodeSizeLiveData = new MutableLiveData<>(0);
        pinCodeCheckLiveData = new MutableLiveData<>(null);
    }

    @Override
    public void onDigitClick(int digit) {
        if (pinCode.size() >= Const.PIN_CODE_SIZE) return;
        pinCode.push(digit);
        int size = pinCode.size();
        pinCodeSizeLiveData.setValue(size);
        if (size == Const.PIN_CODE_SIZE) {
            checkPinCode();
        }
    }

    @Override
    public void onBackspaceClick() {
        if (pinCode.isEmpty()) return;
        pinCode.pop();
        pinCodeSizeLiveData.setValue(pinCode.size());
    }

    public MutableLiveData<Integer> getPinCodeSizeLiveData() {
        return pinCodeSizeLiveData;
    }

    public MutableLiveData<String> getPinCodeCheckLiveData() {
        return pinCodeCheckLiveData;
    }

    private void checkPinCode() {
        StringBuilder builder = new StringBuilder();
        for (Integer digit : pinCode) {
            builder.append(digit);
        }
        String pinCode = builder.toString();
        pinCodeCheckLiveData.setValue(pinCode);
    }
}
