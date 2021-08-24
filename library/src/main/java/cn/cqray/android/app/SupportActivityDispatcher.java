package cn.cqray.android.app;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.cqray.android.AndroidLibrary;

/**
 * SupportActivity生命周期调度器
 * 管理SupportActivity生命周期
 * @author Caray
 */
final class SupportActivityDispatcher extends ActivityLifecycleCallbacks {

    private static volatile SupportActivityDispatcher sInstance;
    private static boolean sRegister;
    private SupportActivityDispatcher() {}

    static void dispatch() {
        // 初始化单例
        if (sInstance == null) {
            synchronized (SupportActivityDispatcher.class) {
                if (sInstance == null) {
                    sInstance = new SupportActivityDispatcher();
                }
            }
        }
        // 注册生命周期监听
        synchronized (SupportActivityDispatcher.class) {
            if (!sRegister) {
                AndroidLibrary.getInstance().getApplication().registerActivityLifecycleCallbacks(sInstance);
                sRegister = true;
            }
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity act, @Nullable Bundle bundle) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onCreated();
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity act) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onStarted();
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity act) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onResumed();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity act) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onPaused();
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity act) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onStopped();
        }
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity act) {
        if (act instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) act).getSupportDelegate();
            delegate.onDestroyed();
        }
    }

}
