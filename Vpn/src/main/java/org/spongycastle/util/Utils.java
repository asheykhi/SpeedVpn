package org.spongycastle.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public abstract class Utils {

    public static void requestFullScreenActivity(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void preventOpenKeyboard(Activity activity, Boolean status) {
        if (status) {

            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        }
    }
}
