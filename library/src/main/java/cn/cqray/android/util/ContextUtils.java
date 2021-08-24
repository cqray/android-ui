package cn.cqray.android.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;

public final class ContextUtils {

    private static Application sApplication;

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    @NonNull
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId, "");
    }

    @NonNull
    private static Application getApplication() {
        if (sApplication != null) {
            return sApplication;
        }
        synchronized (ContextUtils.class) {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThread = Class.forName("android.app.ActivityThread");
                Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
                Object app = activityThread.getMethod("getApplication").invoke(thread);
                sApplication = (Application) app;
            } catch (NoSuchMethodException ignored) {
            } catch (IllegalAccessException ignored) {
            } catch (InvocationTargetException ignored) {
            } catch (ClassNotFoundException ignored) {
            }
//            if (sApplication == null) {
//                sApplication = AndroidSupport.getApplication();
//            }
        }
        return sApplication;
    }
}
