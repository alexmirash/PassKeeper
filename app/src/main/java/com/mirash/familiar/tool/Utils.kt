package com.mirash.familiar.tool

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.mirash.familiar.BuildConfig
import java.util.Locale
import java.util.concurrent.TimeUnit

const val KEY_ID = "id"
const val KEY_POSITION = "position"
const val KEY_EDIT_RESULT_ACTION = "edit_action"
const val KEY_PIN_CODE = "pin"
const val KEY_INVALID_INPUT_COUNT = "invalid_pin"
const val KEY_USER_ID = "user_id"
const val PIN_CODE_SIZE = 4
const val APP_BAR_USERS_MAX_VISIBLE_COUNT = 5
val BACKGROUND_INACTIVITY_KILL_TIME =
    TimeUnit.SECONDS.toMillis((if (BuildConfig.DEBUG) 300 else 30).toLong()).toInt()
const val INVALID_INPUT_NUKE_COUNT = 5

fun String?.isAlike(query: String): Boolean {
    return this != null && lowercase(Locale.getDefault()).contains(query)
}

fun getAppVersionName(): String {
    return "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
}

fun openLinkExternally(activity: Activity, link: String?) {
    if (link.isNullOrEmpty()) return
    val fixLink = if (!link.startsWith("http://") && !link.startsWith("https://")) {
        "http://$link"
    } else {
        link
    }
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fixLink))
    try {
        activity.startActivity(browserIntent)
    } catch (ignored: ActivityNotFoundException) {
    }
}

fun hideKeyboard(activity: Activity, clearFocus: Boolean) {
    val view = activity.currentFocus
    activity.currentFocus?.let {
        if (clearFocus) {
            it.clearFocus()
        }
        hideKeyboardInternal(activity, it)
    }
}

private fun hideKeyboardInternal(context: Context, focusView: View) {
    context.getSystemService(Context.INPUT_METHOD_SERVICE)?.let {
        if (it is InputMethodManager) {
            it.hideSoftInputFromWindow(focusView.windowToken, 0)
        }
    }
}

fun hideKeyboard(activity: Activity) {
    hideKeyboard(activity, true)
}

fun hideKeyboard(context: Context?) {
    if (context is Activity) {
        hideKeyboard(context, true)
    }
}

fun hideKeyboard(textInputLayout: TextInputLayout) {
    textInputLayout.clearFocus()
    hideKeyboardInternal(textInputLayout.context, textInputLayout)
}

fun share(activity: Activity, data: String?) {
    val sendIntent = Intent()
    sendIntent.setAction(Intent.ACTION_SEND)
    sendIntent.putExtra(Intent.EXTRA_TEXT, data)
    sendIntent.setType("text/*")
    val shareIntent = Intent.createChooser(sendIntent, null)
    activity.startActivity(shareIntent)
}

fun isPinCodeActual(pinCode: String?): Boolean {
    return pinCode != null && pinCode.length == PIN_CODE_SIZE
}

private fun createFile(activity: Activity) {
    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.setType("text/plain")
    intent.putExtra(Intent.EXTRA_TITLE, "test.txt")
    activity.startActivityForResult(intent, 1)
}

fun getSystemVibrator(context: Context): Vibrator? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)?.let {
            if (it is VibratorManager) {
                return it.defaultVibrator
            }
        }
    } else {
        context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE)?.let {
            if (it is Vibrator) {
                return it
            }
        }
    }
    return null
}