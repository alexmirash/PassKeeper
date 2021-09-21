package com.mirash.familiar.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;
import com.mirash.familiar.Const;
import com.mirash.familiar.FamiliarApp;
import com.mirash.familiar.R;
import com.mirash.familiar.model.ICredentials;

/**
 * @author Mirash
 */
public final class Utils {
    public static void openLinkExternally(@NonNull Activity activity, String link) throws ActivityNotFoundException {
        if (link == null) return;
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            link = "http://" + link;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        try {
            activity.startActivity(browserIntent);
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public static void hideKeyboard(@NonNull Activity activity, boolean clearFocus) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            if (clearFocus) {
                view.clearFocus();
            }
            hideKeyboardInternal(activity, view);
        }
    }

    private static void hideKeyboardInternal(@NonNull Context context, @NonNull View focusView) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        hideKeyboard(activity, true);
    }

    public static void hideKeyboard(@Nullable Context context) {
        if (context instanceof Activity) {
            hideKeyboard((Activity) context, true);
        }
    }

    public static void hideKeyboard(@NonNull TextInputLayout textInputLayout) {
        textInputLayout.clearFocus();
        hideKeyboardInternal(textInputLayout.getContext(), textInputLayout);
    }

    public static void share(Activity activity, String data) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/*");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        activity.startActivity(shareIntent);
    }

    public static String createMultiFieldString(CharSequence... items) {
        StringBuilder builder = new StringBuilder();
        for (CharSequence item : items) {
            if (!TextUtils.isEmpty(item)) {
                if (builder.length() != 0) builder.append("\n");
                builder.append(item);
            }
        }
        return builder.toString();
    }

    public static String fromCredentials(ICredentials credentials) {
        Resources res = FamiliarApp.getRes();
        return createMultiFieldString(
                credentials.getTitle(),
                credentials.getLink(),
                new LabelString(res.getString(R.string.login), credentials.getLogin()),
                new LabelString(res.getString(R.string.email), credentials.getEmail()),
                new LabelString(res.getString(R.string.phone), credentials.getPhone()),
                new LabelString(res.getString(R.string.password), credentials.getPassword()),
                new LabelString(res.getString(R.string.pin), credentials.getPin()),
                credentials.getDetails()
        );
    }

    public static boolean isPinCodeActual(String pinCode) {
        return pinCode != null && pinCode.length() == Const.PIN_CODE_SIZE;
    }
}
