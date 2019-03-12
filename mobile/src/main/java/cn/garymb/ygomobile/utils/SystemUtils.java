package cn.garymb.ygomobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

public class SystemUtils {
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return "?";
    }

    public static int getVersion(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static Point getHasVirtualScreenSize(Activity context) {
        Point size = new Point();
        Class<?> c;
        try {
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            display.getRealMetrics(dm);
            size.x = dm.widthPixels;
            size.y = dm.heightPixels;
        } catch (Throwable e) {
            context.getWindowManager().getDefaultDisplay().getRealSize(size);
        }
        return size;
    }
}
