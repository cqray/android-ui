package cn.cqray.android.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.widget.CommonToolbar;
import cn.cqray.android.widget.state.StateRefreshLayout;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 基础Activity
 * @author Cqray
 */
public class SupportActivity extends AppCompatActivity implements SupportDelegateProvider {

    public View mContentView;
    public CommonToolbar mToolbar;
    public StateRefreshLayout mRefreshLayout;

    private final SupportDelegate mDelegate = new SupportDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityScreenHook.hookOrientation(this);
        onCreating(savedInstanceState);
    }

    protected void onCreating(@Nullable Bundle savedInstanceState) {}

    @Override
    public void setContentView(View view) {
        mDelegate.setContentView(view);
    }

    @Override
    public void setContentView(int layoutResId) {
        mDelegate.setContentView(layoutResId);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mDelegate.setContentView(view);
    }

    public void setNativeContentView(View view) {
        mDelegate.setNativeContentView(view);
    }

    public void setNativeContentView(int layoutResId) {
        mDelegate.setNativeContentView(layoutResId);
    }

    public void setHeaderView(int layoutResId) {
        mDelegate.setHeaderView(layoutResId);
    }

    public void setHeaderView(View view) {
        mDelegate.setHeaderView(view);
    }

    public void setHeaderFloating(boolean floating) {
        mDelegate.setHeaderFloating(floating);
    }

    public void setFooterView(int layoutResId) {
        mDelegate.setFooterView(layoutResId);
    }

    public void setFooterView(View view) {
        mDelegate.setFooterView(view);
    }

    public void setFooterFloating(boolean floating) {
        mDelegate.setFooterFloating(floating);
    }

    public void setBackgroundColor(int color) {
        mDelegate.getViewDelegate().setBackgroundColor(color);
    }

    public void setBackground(Drawable background) {
        mDelegate.getViewDelegate().setBackground(background);
    }

    @NonNull
    @Override
    public SupportDelegate getSupportDelegate() {
        return mDelegate;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }

    @Override
    public boolean onBackPressedSupport() {
        return false;
    }

    @Override
    public boolean onSwipeBackSupport() {
        return AndroidLibrary.getInstance().isSwipeBackSupport();
    }

    @Override
    public boolean onFragmentPopSupport() {
        return true;
    }

    public void start(Class<? extends SupportDelegateProvider> to) {
        mDelegate.start(to);
    }

    public void startWithPop(Class<? extends SupportDelegateProvider> to, Class<? extends SupportDelegateProvider> popTo) {
        mDelegate.startWithPop(to, popTo, true);
    }

    public void startWithPop(Class<? extends SupportDelegateProvider> to, Class<? extends SupportDelegateProvider> popTo, boolean inclusive) {
        mDelegate.startWithPop(to, popTo, inclusive);
    }

    public void start(NavIntent intent) {
        mDelegate.start(intent);
    }

    public void pop() {
        mDelegate.pop();
    }

    public void popTo(Class<? extends SupportDelegateProvider> clazz) {
        mDelegate.popTo(clazz, true);
    }

    public void popTo(Class<? extends SupportDelegateProvider> clazz, boolean inclusive) {
        mDelegate.popTo(clazz, inclusive);
    }

    public void setIdle() {
        mDelegate.getViewDelegate().setIdle();
    }

    public void setBusy() {
        mDelegate.getViewDelegate().setBusy(null);
    }

    public void setBusy(String text) {
        mDelegate.getViewDelegate().setBusy(text);
    }

    public void setEmpty() {
        mDelegate.getViewDelegate().setEmpty(null);
    }

    public void setEmpty(String text) {
        mDelegate.getViewDelegate().setEmpty(text);
    }

    public void setError() {
        mDelegate.getViewDelegate().setError(null);
    }

    public void setError(String text) {
        mDelegate.getViewDelegate().setError(text);
    }

    public void showError(String text) {
        mDelegate.getToastDelegate().error(text);
    }

    public void showError(String text, int duration) {
        mDelegate.getToastDelegate().error(text, duration);
    }

    public void showInfo(String text) {
        mDelegate.getToastDelegate().info(text);
    }

    public void showInfo(String text, int duration) {
        mDelegate.getToastDelegate().info(text, duration);
    }

    public void showSuccess(String text) {
        mDelegate.getToastDelegate().success(text);
    }

    public void showSuccess(String text, int duration) {
        mDelegate.getToastDelegate().success(text, duration);
    }

    public void showWarning(String text) {
        mDelegate.getToastDelegate().warning(text);
    }

    public void showWarning(String text, int duration) {
        mDelegate.getToastDelegate().warning(text, duration);
    }

    public void addDisposable(Disposable disposable) {
        mDelegate.getDisposablePool().add(disposable);
    }

    public void timer(Consumer<Long> consumer) {
        mDelegate.getDisposablePool().timer(consumer, 0);
    }

    public void timer(Consumer<Long> consumer, long delay) {
        mDelegate.getDisposablePool().timer(consumer, delay);
    }

    public void interval(@NonNull Consumer<Long> consumer, long period) {
        mDelegate.getDisposablePool().interval(consumer, 0, period);
    }
}
