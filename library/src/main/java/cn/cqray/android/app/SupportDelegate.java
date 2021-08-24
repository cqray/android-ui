package cn.cqray.android.app;

import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import cn.cqray.android.util.CheckUtils;
import cn.cqray.android.widget.CommonToolbar;
import cn.cqray.android.widget.state.StateRefreshLayout;
import cn.cqray.android.widget.swipe.SwipeBackHelper;

/**
 * Support类界面代理
 * @author Cqray
 */
public class SupportDelegate {

    private final DisposablePool mDisposablePool;
    private final SupportViewDelegate mViewDelegate;
    private final SupportToastDelegate mToastDelegate;
    private final SupportDelegateProvider mDelegateProvider;

    public SupportDelegate(AppCompatActivity activity) {
        this((Object) activity);
        // Activity和Fragment生命周期调度
        SupportActivityDispatcher.dispatch();
        SupportFragmentDispatcher.dispatch(mDelegateProvider);
    }

    public SupportDelegate(Fragment fragment) {
        this((Object) fragment);
    }

    private SupportDelegate(Object provider) {
        CheckUtils.checkDelegateProvider(provider);
        mDelegateProvider = (SupportDelegateProvider) provider;
        mViewDelegate = new SupportViewDelegate(mDelegateProvider);
        mToastDelegate = new SupportToastDelegate(mDelegateProvider);
        mDisposablePool = new DisposablePool((LifecycleOwner) provider);
    }

    protected void onCreated() {
        if (mDelegateProvider instanceof Fragment) {
            final Fragment supportFragment = (Fragment) mDelegateProvider;
            FragmentActivity supportActivity = supportFragment.requireActivity();
            CheckUtils.checkDelegateProvider(supportActivity);
            OnBackPressedDispatcher dispatcher = supportActivity.getOnBackPressedDispatcher();
            dispatcher.addCallback(supportFragment, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    SupportDelegateProvider fragmentProvider = (SupportDelegateProvider) supportFragment.getParentFragment();
                    SupportDelegateProvider activityProvider = (SupportDelegateProvider) supportFragment.requireActivity();
                    if (fragmentProvider != null) {
                        if (fragmentProvider.onFragmentPopSupport()) {
                            fragmentPop((Fragment) mDelegateProvider);
                        } else {
                            fragmentPop((Fragment) fragmentProvider);
                        }
                    } else if (!activityProvider.onFragmentPopSupport()) {
                        activityPop((FragmentActivity) activityProvider);
                    } else if (!mDelegateProvider.onBackPressedSupport()) {
                        fragmentPop((Fragment) mDelegateProvider);
                    }
                }
            });
        } else {
            SwipeBackHelper.with(getActivity()).onAttachActivity();
        }
    }

    protected void onResumed() {}

    protected void onStarted() {}

    protected void onPaused() {}

    protected void onStopped() {}

    protected void onDestroyed() {}

    public void setContentView(@LayoutRes int id) {
        mViewDelegate.setContentView(id);
    }

    public void setContentView(View view) {
        mViewDelegate.setContentView(view);
    }

    public void setNativeContentView(@LayoutRes int id) {
        mViewDelegate.setNativeContentView(id);
    }

    public void setNativeContentView(View view) {
        mViewDelegate.setNativeContentView(view);
    }

    public void setHeaderView(@LayoutRes int id) {
        mViewDelegate.setHeaderView(id);
    }

    public void setHeaderView(View view) {
        mViewDelegate.setHeaderView(view);
    }

    public void setHeaderFloating(boolean floating) {
        mViewDelegate.setHeaderFloating(floating);
    }

    public void setFooterView(@LayoutRes int id) {
        mViewDelegate.setFooterView(id);
    }

    public void setFooterView(View view) {
        mViewDelegate.setFooterView(view);
    }

    public void setFooterFloating(boolean floating) {
        mViewDelegate.setFooterFloating(floating);
    }

    public void loadRootFragment(@IdRes int containerId, Class<? extends SupportDelegateProvider> fragmentClass) {
        getSupportFragmentManager().loadRootFragment(containerId, new NavIntent(fragmentClass));
    }

    public void start(Class<? extends SupportDelegateProvider> to) {
        getSupportFragmentManager().start(new NavIntent(to));
    }

    public void startWithPop(Class<? extends SupportDelegateProvider> to, Class<? extends SupportDelegateProvider> popTo, boolean inclusive) {
        NavIntent intent = new NavIntent(to).setPopTo(popTo, inclusive);
        start(intent);
    }

    public void start(NavIntent intent) {
        getSupportFragmentManager().start(intent);
    }

    public void pop() {
        if (!getSupportFragmentManager().pop()) {
            getSupportFragmentManager().popParent();
        }
    }

    public void popTo(Class<? extends SupportDelegateProvider> popTo, boolean inclusive) {
        getSupportFragmentManager().popTo(popTo, inclusive);
    }

    public <T extends View> T findViewById(@IdRes int resId) {
        return mViewDelegate.findViewById(resId);
    }

    public View getContentView() {
        return mViewDelegate.getContentView();
    }

    public CommonToolbar getToolbar() {
        return mViewDelegate.getToolbar();
    }

    public StateRefreshLayout getRefreshLayout() {
        return mViewDelegate.getRefreshLayout();
    }

    public AppCompatActivity getActivity() {
        if (mDelegateProvider instanceof AppCompatActivity) {
            return (AppCompatActivity) mDelegateProvider;
        } else {
            return (AppCompatActivity) ((Fragment) mDelegateProvider).requireActivity();
        }
    }

    public DisposablePool getDisposablePool() {
        return mDisposablePool;
    }

    public SupportSwipeDelegate getSwipeDelegate() {
        if (mDelegateProvider instanceof AppCompatActivity) {
            return SwipeBackHelper.with((AppCompatActivity) mDelegateProvider);
        } else {
            return SwipeBackHelper.with((Fragment) mDelegateProvider);
        }
    }

    public SupportViewDelegate getViewDelegate() {
        return mViewDelegate;
    }

    public SupportToastDelegate getToastDelegate() {
        return mToastDelegate;
    }

    public SupportFragmentManager getSupportFragmentManager() {
        return SupportFragmentManager.get(mDelegateProvider);
    }

    private void activityPop(@NonNull FragmentActivity activity) {
        SupportDelegateProvider provider = (SupportDelegateProvider) activity;
        if (!provider.onBackPressedSupport()) {
            SupportFragmentManager sfm = SupportFragmentManager.get(provider);
            if (!sfm.pop()) {
                activity.finish();
            }
        }
    }

    private void fragmentPop(@NonNull Fragment fragment) {
        Fragment parent = fragment.getParentFragment();
        SupportDelegateProvider parentProvider = (SupportDelegateProvider) parent;
        if (parent == null) {
            activityPop(fragment.requireActivity());
        } else if (!parentProvider.onFragmentPopSupport()) {
            fragmentPop(parent);
        } else {
            SupportDelegateProvider provider = (SupportDelegateProvider) parent;
            if (!provider.onBackPressedSupport()) {
                SupportFragmentManager sfm = SupportFragmentManager.get(provider);
                if (!sfm.pop()) {
                    fragmentPop(parent);
                }
            }
        }
    }
}
