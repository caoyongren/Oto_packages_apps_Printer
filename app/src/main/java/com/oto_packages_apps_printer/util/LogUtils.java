package com.oto_packages_apps_printer.util;

import android.util.Log;
import com.oto_packages_apps_printer.APP;

/**
 * Created by Matthew on 2016/4/12.
 */
public class LogUtils {

    public static void i(String tag, String msg) {
        if(APP.IS_LOGI) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if(APP.IS_LOGD) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if(APP.IS_LOGE) {
            Log.e(tag, msg);
        }
    }
}
