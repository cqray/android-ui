package cn.cqray.android;

import android.app.Application;
import android.content.Context;

import cn.cqray.android.app.ActivityHelper;
import cn.cqray.android.app.DefaultVerticalAnimator;
import cn.cqray.android.app.FragmentAnimator;

/**
 * Android库入口
 * @author Cqray
 */
public class AndroidLibrary {

    private static final class Holder {
        static final AndroidLibrary INSTANCE = new AndroidLibrary();
    }

    /** 反射所得Application **/
    private Application mApplication;
    /** Fragment切换动画 **/
    private FragmentAnimator mFragmentAnimator;
    /** 全局处理器 **/
    private GlobalHandler mGlobalHandler;
    /** 是否支持策划返回 **/
    private boolean mSwipeBackSupport;


    private AndroidLibrary() {
        mFragmentAnimator = new DefaultVerticalAnimator();
    }

    public static AndroidLibrary getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 初始化AndroidLibrary
     * @param application Application
     */
    public AndroidLibrary initialize(Application application) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        int count = elements.length;
        for (int i = 0; i < count; i++) {
            StackTraceElement element = elements[i];
            if (getClass().getName().equals(element.getClassName())) {
                if (i + 1 < count) {
                    StackTraceElement parentElement = elements[i + 1];
                    try {
                        Class<?> clazz = Class.forName(parentElement.getClassName());
                        if (Application.class.isAssignableFrom(clazz)) {
                            mApplication = application;
                            ActivityHelper.initialize(application);
                            return this;
                        }
                    } catch (ClassNotFoundException ignored) {}
                }
            }
        }
        throw new RuntimeException("You must initialize AndroidLibrary in class which extends Application.");
    }

    public AndroidLibrary fragmentAnimator(FragmentAnimator animator) {
        if (animator != null) {
            mFragmentAnimator = animator;
        }
        return this;
    }

    public AndroidLibrary globalHandler(GlobalHandler handler) {
        mGlobalHandler = handler;
        return this;
    }

    public AndroidLibrary swipeBackSupport(boolean support) {
        mSwipeBackSupport = support;
        return this;
    }

    public Application getApplication() {
        if (mApplication == null) {
            throw new RuntimeException("You should call AndroidLibrary.getInstance().initialize() first.");
        }
        return mApplication;
    }

    public Context getContext() {
        return getApplication().getApplicationContext();
    }

    public FragmentAnimator getFragmentAnimator() {
        return mFragmentAnimator;
    }

    public GlobalHandler getGlobalHandler() {
        return mGlobalHandler;
    }

    public boolean isSwipeBackSupport() {
        return mSwipeBackSupport;
    }

}
