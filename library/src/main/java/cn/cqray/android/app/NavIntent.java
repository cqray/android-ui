package cn.cqray.android.app;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 导航意图
 * @author Cqray
 */
public class NavIntent {

    /** 目标Fragment **/
    private Class<? extends SupportDelegateProvider> mTo;
    /** 回退标识Fragment **/
    private Class<? extends SupportDelegateProvider> mPopTo;
    /** 回退是否包含指定标识Fragment **/
    private boolean mPopToInclusive;
    /** Fragment动画 **/
    private FragmentAnimator mFragmentAnimator;
    /** 参数 **/
    private final Bundle mArguments = new Bundle();

    public NavIntent() {}

    public NavIntent(Class<? extends SupportDelegateProvider> cls) {
        mTo = cls;
    }

    public NavIntent(Class<? extends SupportDelegateProvider> cls, Bundle arguments) {
        mTo = cls;
        if (arguments != null) {
            mArguments.putAll(arguments);
        }
    }

    public NavIntent setClass(Class<? extends SupportDelegateProvider> cls) {
        mTo = cls;
        return this;
    }

    public NavIntent setPopTo(Class<? extends SupportDelegateProvider> popTo, boolean inclusive) {
        mPopTo = popTo;
        mPopToInclusive = inclusive;
        return this;
    }

    public NavIntent setFragmentAnimator(FragmentAnimator animator) {
        mFragmentAnimator = animator;
        return this;
    }

    public Class<? extends SupportDelegateProvider> getToClass() {
        return mTo;
    }

    public Class<? extends SupportDelegateProvider> getPopToClass() {
        return mPopTo;
    }

    public boolean isPopToInclusive() {
        return mPopToInclusive;
    }

    public FragmentAnimator getFragmentAnimator() {
        return mFragmentAnimator;
    }

    public NavIntent put(String key, byte value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, char value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, short value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, boolean value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, int value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, float value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, double value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, CharSequence value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, String value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, byte [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, char []value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, short [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, boolean [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, int [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, float [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, double [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, CharSequence [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, String [] value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, Serializable value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, Parcelable value) {
        return put(key, (Object) value);
    }

    public NavIntent put(String key, Parcelable [] value) {
        mArguments.putParcelableArray(key, value);
        return this;
    }

    public NavIntent put(String key, ArrayList<?> value) {
        return put(key, (Object) value);
    }

    public NavIntent putAll(Bundle bundle) {
        mArguments.putAll(bundle);
        return this;
    }

    private NavIntent put(String key, Object value) {
        if (value instanceof Serializable) {
            mArguments.putSerializable(key, (Serializable) value);
        } else if (value instanceof Parcelable) {
            mArguments.putParcelable(key, (Parcelable) value);

        }
        return this;
    }

    public Bundle getArguments() {
        return mArguments;
    }

}
