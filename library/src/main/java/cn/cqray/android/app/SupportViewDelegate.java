package cn.cqray.android.app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import cn.cqray.android.GlobalHandler;
import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.R;
import cn.cqray.android.util.ButterKnifeUtils;
import cn.cqray.android.util.CheckUtils;
import cn.cqray.android.util.Colors;
import cn.cqray.android.widget.CommonToolbar;
import cn.cqray.android.widget.state.StateRefreshLayout;

/**
 * SupportDelegate中View委托
 * @author Cqray
 */
public final class SupportViewDelegate {

    /** 内容控件 **/
    private View mContentView;
    /** 标题 **/
    private CommonToolbar mToolbar;
    /** 刷新控件 **/
    private StateRefreshLayout mRefreshLayout;
    /** ButterKnife绑定 **/
    private Object mUnBinder;
    /** Fragment、Activity背景 **/
    private final MutableLiveData<Drawable> mBackground;
    /** 委托提供者 **/
    private final SupportDelegateProvider mDelegateProvider;

    public SupportViewDelegate(SupportDelegateProvider provider) {
        CheckUtils.checkDelegateProvider(provider);
        LifecycleOwner owner = (LifecycleOwner) provider;
        mDelegateProvider = provider;
        // 监听背景变化
        Drawable background = new ColorDrawable(Colors.background());
        mBackground = new MutableLiveData<>(background);
        mBackground.observe(owner, drawable -> {
            if (mContentView != null) {
                ViewCompat.setBackground(mContentView, drawable);
            }
        });
        // 销毁监听
        owner.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                ButterKnifeUtils.unbind(mUnBinder);
            }
        });

    }

    protected void initProviderView() {
        final SupportDelegate delegate = mDelegateProvider.getSupportDelegate();
        if (mDelegateProvider instanceof AppCompatActivity) {
            if (mDelegateProvider instanceof SupportActivity) {
                ((SupportActivity) mDelegateProvider).mToolbar = mToolbar;
                ((SupportActivity) mDelegateProvider).mContentView = mContentView;
                ((SupportActivity) mDelegateProvider).mRefreshLayout = mRefreshLayout;
            }
        } else {
            if (mDelegateProvider instanceof SupportFragment) {
                ((SupportFragment) mDelegateProvider).mToolbar = mToolbar;
                ((SupportFragment) mDelegateProvider).mContentView = mContentView;
                ((SupportFragment) mDelegateProvider).mRefreshLayout = mRefreshLayout;
            }
        }
        // 初始化标题栏监听事件
        if (mToolbar != null) {
            mToolbar.setNavListener(v -> delegate.pop());
        }
        // 全局初始化控件
        GlobalHandler handler = AndroidLibrary.getInstance().getGlobalHandler();
        if (handler != null) {
            // 全局初始化Toolbar
            if (mToolbar != null) {
                handler.onHandleToolbar(mDelegateProvider, mToolbar);
            }
            // 全局初始化刷新控件
            if (mRefreshLayout != null) {
                handler.onHandleRefreshLayout(mDelegateProvider, mRefreshLayout);
            }
        }
        // 初始化ButterKnife
        initUnBinder();
    }

    @MainThread
    public void setContentView(@LayoutRes int id) {
        setContentView(inflate(id));
    }

    @MainThread
    public void setContentView(View view) {
        mContentView = inflate(R.layout._core_layout_default);
        mToolbar = findViewById(R.id._core_toolbar);
        mRefreshLayout = findViewById(R.id._core_refresh);
        assert mRefreshLayout != null;
        mRefreshLayout.addView(view);
        if (mDelegateProvider instanceof AppCompatActivity) {
            ((AppCompatActivity) mDelegateProvider).getDelegate().setContentView(mContentView);
        }
        // 初始化控件
        initProviderView();
    }

    @MainThread
    public void setNativeContentView(@LayoutRes int id) {
        setNativeContentView(inflate(id));
    }

    @MainThread
    public void setNativeContentView(View view) {
        mContentView = inflate(R.layout._core_layout_native);
        ((ViewGroup) mContentView).addView(view);
        mToolbar = findViewById(R.id._core_toolbar);
        mRefreshLayout = findViewById(R.id._core_refresh);
        if (mDelegateProvider instanceof AppCompatActivity) {
            ((AppCompatActivity) mDelegateProvider).getDelegate().setContentView(mContentView);
        }
        // 初始化控件
        initProviderView();
    }

    @MainThread
    public void setHeaderView(@LayoutRes int id) {
        setHeaderView(inflate(id));
    }

    @MainThread
    public void setHeaderView(View view) {
        FrameLayout headerLayout = findViewById(R.id._core_header);
        if (headerLayout != null) {
            headerLayout.removeAllViews();
            headerLayout.addView(view);
            // 初始化ButterKnife
            initUnBinder();
        }
    }

    @MainThread
    public void setHeaderFloating(boolean floating) {
        FrameLayout headerLayout = findViewById(R.id._core_header);
        if (headerLayout != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRefreshLayout.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, floating ? R.id._core_toolbar : R.id._core_header);
            mRefreshLayout.requestLayout();
        }
    }

    @MainThread
    public void setFooterView(@LayoutRes int id) {
        setFooterView(inflate(id));
    }

    @MainThread
    public void setFooterView(View view) {
        FrameLayout footerLayout = findViewById(R.id._core_footer);
        if (footerLayout != null) {
            footerLayout.removeAllViews();
            footerLayout.addView(view);
            // 初始化ButterKnife
            initUnBinder();
        }
    }

    @MainThread
    public void setFooterFloating(boolean floating) {
        FrameLayout footerLayout = findViewById(R.id._core_footer);
        if (footerLayout != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRefreshLayout.getLayoutParams();
            params.addRule(RelativeLayout.ABOVE, floating ? 0 : R.id._core_footer);
            mRefreshLayout.requestLayout();
        }
    }

    public void setIdle() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setIdle();
        }
    }

    public void setBusy(String text) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setBusy(text);
        }
    }

    public void setEmpty(String text) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEmpty(text);
        }
    }

    public void setError(String text) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setError(text);
        }
    }

    public void setBackgroundColor(int color) {
        mBackground.postValue(new ColorDrawable(color));
    }

    public void setBackground(Drawable background) {
        mBackground.postValue(background);
    }

    public <T extends View> T findViewById(@IdRes int resId) {
        if (mContentView == null) {
            return null;
        }
        return mContentView.findViewById(resId);
    }

    public View getContentView() {
        return mContentView;
    }

    public CommonToolbar getToolbar() {
        return mToolbar;
    }

    public StateRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    private View inflate(@LayoutRes int resId) {
        AppCompatActivity activity = mDelegateProvider.getSupportDelegate().getActivity();
        ViewGroup root = activity.findViewById(android.R.id.content);
        return LayoutInflater.from(activity).inflate(resId, root, false);
    }

    private void initUnBinder() {
        if (mContentView != null) {
            mUnBinder = ButterKnifeUtils.bind(mDelegateProvider, mContentView);
        }
    }
}
