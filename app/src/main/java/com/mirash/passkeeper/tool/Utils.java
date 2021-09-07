package com.mirash.passkeeper.tool;

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
import com.mirash.passkeeper.Const;
import com.mirash.passkeeper.PassKeeperApp;
import com.mirash.passkeeper.R;
import com.mirash.passkeeper.db.RepositoryProvider;
import com.mirash.passkeeper.model.ICredentials;

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

    public static String fromCredentials(ICredentials credentials) {
        Resources res = PassKeeperApp.getRes();
        StringBuilder builder = new StringBuilder();
        String item = credentials.getTitle();
        if (!TextUtils.isEmpty(item)) {
            builder.append(item).append("\n");
        }
        item = credentials.getLink();
        if (!TextUtils.isEmpty(item)) {
            builder.append(item).append("\n");
        }
        builder.append(res.getString(R.string.login)).append(":").append(credentials.getLogin()).append("\n");
        item = credentials.getPassword();
        if (!TextUtils.isEmpty(item)) {
            builder.append(res.getString(R.string.password)).append(":").append(item).append("\n");
        }
        item = credentials.getPin();
        if (!TextUtils.isEmpty(item)) {
            builder.append(res.getString(R.string.pin)).append(":").append(item).append("\n");
        }
        item = credentials.getDetails();
        if (!TextUtils.isEmpty(item)) {
            builder.append(item).append("\n");
        }
        return builder.toString();
    }

    public static boolean isPinCodeActual(String pinCode){
        return pinCode != null && pinCode.length() == Const.PIN_CODE_SIZE;
    }

    public static void nukeData() {
        RepositoryProvider.getCredentialsRepository().nuke();
    }
}
