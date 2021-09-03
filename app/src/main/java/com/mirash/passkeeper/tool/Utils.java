package com.mirash.passkeeper.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Mirash
 */
public final class Utils {
    public static void openLinkExternally(Activity activity, String link) throws ActivityNotFoundException {
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
}
