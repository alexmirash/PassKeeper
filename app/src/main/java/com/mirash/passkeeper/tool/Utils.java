package com.mirash.passkeeper.tool;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

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
}
