package cn.cqray.android.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

/**
 * SupportFragment生命周期调度器
 * 管理SupportFragment生命周期
 * @author Cqray
 */
class SupportFragmentDispatcher extends FragmentManager.FragmentLifecycleCallbacks {

    private static class Holder {
        static final SupportFragmentDispatcher INSTANCE = new SupportFragmentDispatcher();
    }

    private SupportFragmentDispatcher() {}

    static void dispatch(SupportDelegateProvider provider) {
        final AppCompatActivity act = (AppCompatActivity) provider;
        act.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                act.getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(Holder.INSTANCE);
            }
        });
        act.getSupportFragmentManager().registerFragmentLifecycleCallbacks(Holder.INSTANCE, true);
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onCreated();
        }
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStarted(fm, f);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onStarted();
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onResumed();
        }
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onPaused();
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStopped(fm, f);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onStopped();
        }
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        if (f instanceof SupportDelegateProvider) {
            SupportDelegate delegate = ((SupportDelegateProvider) f).getSupportDelegate();
            delegate.onDestroyed();
        }
    }

}
