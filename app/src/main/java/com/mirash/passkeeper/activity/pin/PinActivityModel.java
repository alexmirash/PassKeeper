package com.mirash.passkeeper.activity.pin;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.view.PinInputView;

import java.util.Stack;

/**
 * @author Mirash
 */
public class PinActivityModel extends AndroidViewModel implements PinInputView.PinCallback {
    private final MutableLiveData<Integer> pinCodeSizeLiveData;
    private final MutableLiveData<Boolean> pinCodeCorrectLiveData;
    private final Stack<Integer> pinCode;

    public PinActivityModel(@NonNull Application application) {
        super(application);
        pinCode = new Stack<>();
        pinCodeSizeLiveData = new MutableLiveData<>(0);
        pinCodeCorrectLiveData = new MutableLiveData<>(false);
    }

    @Override
    public void onDigitClick(int digit) {
        if (pinCode.size() >= Const.PIN_CODE_SIZE) return;
        pinCode.push(digit);
        int size = pinCode.size();
        pinCodeSizeLiveData.setValue(size);
        log("onDigitClick " + digit);
        if (size == Const.PIN_CODE_SIZE) {
            checkPinCode();
        }
    }

    @Override
    public void onBackspaceClick() {
        if (pinCode.isEmpty()) return;
        pinCode.pop();
        pinCodeSizeLiveData.setValue(pinCode.size());
        log("onBackspaceClick");
    }

    public MutableLiveData<Integer> getPinCodeSizeLiveData() {
        return pinCodeSizeLiveData;
    }

    public MutableLiveData<Boolean> getPinCodeCorrectLiveData() {
        return pinCodeCorrectLiveData;
    }

    private void checkPinCode() {
        StringBuilder builder = new StringBuilder();
        for (Integer digit : pinCode) {
            builder.append(digit);
        }
        String pinCode = builder.toString();
        if (pinCode.equals("123456")) {
            Log.d("LOL", "congrats, pin is correct");
            pinCodeCorrectLiveData.setValue(true);
        }
        log("checkPinCode " + pinCode);
    }

    private void log(String log) {
        Log.d("LOL", log + ": " + TextUtils.join(",", pinCode));
    }
}
