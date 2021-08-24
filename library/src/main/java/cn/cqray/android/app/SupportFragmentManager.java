package cn.cqray.android.app;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.HashMap;

import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.util.CheckUtils;

/**
 * Fragment管理器
 * @author Cqray
 */
public final class SupportFragmentManager {

    private final LifecycleOwner mLifecycleOwner;
    private final ViewModelProvider mViewModelProvider;
    private static final HashMap<Object, SupportFragmentManager> FM_MAP = new HashMap<>();

    @NonNull
    public static SupportFragmentManager get(@NonNull SupportDelegateProvider provider) {
        SupportFragmentManager fm = FM_MAP.get(provider);
        if (fm == null) {
            fm = new SupportFragmentManager(provider);
            FM_MAP.put(provider, fm);
        }
        return fm;
    }

    public SupportFragmentManager(@NonNull SupportDelegateProvider provider) {
        CheckUtils.checkDelegateProvider(provider);
        mLifecycleOwner = (LifecycleOwner) provider;
        mLifecycleOwner.getLifecycle().addObserver((LifecycleEventObserver) (owner, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                FM_MAP.remove(owner);
            }
        });
        // 初始化ViewModelProvider
        if (mLifecycleOwner instanceof AppCompatActivity) {
            // Activity获取当前Activity对应的ViewModelProvider
            mViewModelProvider = new ViewModelProvider((ViewModelStoreOwner) mLifecycleOwner);
        } else {
            // Fragment获取当前Fragment父级Fragment或所在Activity对应的ViewModelProvider
            Fragment fragment = (Fragment) mLifecycleOwner;
            Fragment parent = fragment.getParentFragment();
            if (parent != null) {
                // Fragment获取当前Fragment父级Fragment对应的ViewModelProvider
                mViewModelProvider = new ViewModelProvider(parent);
            } else {
                // Fragment获取当前Fragment所在Activity对应的ViewModelProvider
                mViewModelProvider = new ViewModelProvider(fragment.requireActivity());
            }
        }
        // 添加到缓存中
        FM_MAP.put(provider, this);
    }

    public void loadRootFragment(@IdRes int containerId, NavIntent intent) {
        getSupportViewModel().setContainerId(containerId);
        start(intent);
    }

    public void start(@NonNull NavIntent intent) {
        // 获取数据缓存
        SupportViewModel viewModel = getSupportViewModel();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // 是否是初始化Fragment
        boolean initialFragment = viewModel.getBackStackCount() == 0;
        // 是否需要回退
        boolean needPop = intent.getPopToClass() != null;
        // 回退到指定的Fragment
        if (needPop) {
            popTo(intent.getPopToClass(), intent.isPopToInclusive());
        }
        // 如果toClass是Activity, 则直接操作Activity
        if (Activity.class.isAssignableFrom(intent.getToClass())) {
            Activity activity = getActivity();
            Intent actIntent = new Intent(activity, intent.getToClass());
            actIntent.putExtras(intent.getArguments());
            activity.startActivity(actIntent);
            return;
        }
        // 创建Fragment
        Fragment fragment = viewModel.generateFragment(this, intent.getToClass());
        fragment.setArguments(intent.getArguments());
        // 设置动画
        SupportDelegateProvider provider = ((SupportDelegateProvider) fragment);
        FragmentAnimator fa = getFragmentAnimator(provider, intent);
        if (!initialFragment) {
            ft.setCustomAnimations(fa.mEnter, fa.mExit, fa.mPopEnter, fa.mPopExit);
        }
        // 隐藏当前正在显示的Fragment
        Fragment current = fm.getPrimaryNavigationFragment();
        if (current != null) {
            ft.setMaxLifecycle(current, Lifecycle.State.STARTED);
            if (needPop) {
                ft.hide(current);
            }
        }
        // 获取FragmentTag
        String fragmentTag = viewModel.getFragmentTag(fragment);
        // 添加Fragment
        ft.add(viewModel.getContainerId(), fragment, fragmentTag);
        // 设置初始化生命周期
        ft.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
        // 设置当前Fragment
        ft.setPrimaryNavigationFragment(fragment);
        // 加入BackStack
        ft.addToBackStack(fragmentTag);
        // 加入回退栈
        viewModel.addToBackStack(fragmentTag);
        // 提交事务
        ft.setReorderingAllowed(true);
        ft.commit();
    }

    /**
     * 回退到指定的Fragment
     * @param popTo 指定的Fragment
     * @param inclusive 是否包含指定的Fragment
     */
    @SuppressWarnings("unchecked")
    public void popTo(Class<? extends SupportDelegateProvider> popTo, boolean inclusive) {
        SupportViewModel viewModel = getSupportViewModel();
        FragmentManager fm = getFragmentManager();
        // 如果popTo是Activity, 则直接操作Activity
        if (Activity.class.isAssignableFrom(popTo)) {
            ActivityHelper.popTo((Class<? extends Activity>) popTo, inclusive);
            return;
        }
        // 如果是第一个入栈的Fragment并且需要销毁，则父级pop。
        if (viewModel.isFirstFragment(popTo) && inclusive) {
            popParent();
            return;
        }
        // 操作当前Fragment回退栈中的Fragment
        boolean needContinue = true;
        for (int i = 0; i < viewModel.getBackStackCount(); i++) {
            String fragmentTag = viewModel.getFragmentTag(i);
            if (fragmentTag.split("-")[0].equals(popTo.getName())) {
                if (inclusive) {
                    fm.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    viewModel.popBackStack(i);
                    return;
                } else {
                    needContinue = false;
                    continue;
                }
            }
            if (!needContinue) {
                fm.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                viewModel.popBackStack(i);
                return;
            }
        }
    }

    public boolean canPop() {
        return getSupportViewModel().canPop();
    }

    public boolean pop() {
        if (!canPop()) {
            return false;
        }
        if (getFragmentManager().isStateSaved()) {
            return false;
        }
        SupportViewModel viewModel = getSupportViewModel();
        // Fragment回退
        getFragmentManager().popBackStack(viewModel.peekFragmentTag(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // 清除回退栈数据
        viewModel.popBackStack();
        return true;
    }

    @NonNull
    public AppCompatActivity getActivity() {
        if (mLifecycleOwner instanceof AppCompatActivity) {
            return (AppCompatActivity) mLifecycleOwner;
        }
        return (AppCompatActivity) ((Fragment) mLifecycleOwner).requireActivity();
    }

    @NonNull
    public FragmentManager getFragmentManager() {
        if (mLifecycleOwner instanceof AppCompatActivity) {
            return ((AppCompatActivity) mLifecycleOwner).getSupportFragmentManager();
        }
        return ((Fragment) mLifecycleOwner).getParentFragmentManager();
    }

    /**
     * 父级Fragment或Activity退出
     */
    protected void popParent() {
        if (mLifecycleOwner instanceof AppCompatActivity) {
            if (!((AppCompatActivity) mLifecycleOwner).isFinishing()) {
                ((AppCompatActivity) mLifecycleOwner).finish();
            }
        } else {
            Fragment fragment = (Fragment) mLifecycleOwner;
            Fragment parent = fragment.getParentFragment();
            if (parent != null) {
                SupportFragmentManager.get((SupportDelegateProvider) parent).pop();
            } else {
                if (!fragment.requireActivity().isFinishing()) {
                    fragment.requireActivity().finish();
                }
            }
        }
    }

    @NonNull
    private SupportViewModel getSupportViewModel() {
        return mViewModelProvider.get(SupportViewModel.class);
    }

    /**
     * 获取Fragment、Activity或NavIntent对应的FragmentAnimator
     * @param provider Fragment或Activity
     * @param intent NavIntent参数
     */
    @NonNull
    private static FragmentAnimator getFragmentAnimator(@NonNull SupportDelegateProvider provider, @NonNull NavIntent intent) {
        if (intent.getFragmentAnimator() != null) {
            return intent.getFragmentAnimator();
        }
        FragmentAnimator fragmentAnimator = provider.onCreateFragmentAnimator();
        if (fragmentAnimator != null) {
            return fragmentAnimator;
        }
        return getFragmentAnimator(provider);
    }

    /**
     * 获取Fragment或Activity对应的FragmentAnimator
     * @param provider Fragment或Activity
     */
    private static FragmentAnimator getFragmentAnimator(@NonNull SupportDelegateProvider provider) {
        FragmentAnimator fragmentAnimator;
        if (provider instanceof AppCompatActivity) {
            // 获取FragmentAnimator
            fragmentAnimator = AndroidLibrary.getInstance().getFragmentAnimator();
        } else {
            Fragment fragment = (Fragment) provider;
            // 获取父级Activity的FragmentAnimator
            // 因为初始化Fragment没有父级Activity,所有取栈顶的Activity
            Activity act = fragment.getActivity() == null ? ActivityHelper.requirePeek() : fragment.getActivity();
            // 获取FragmentAnimator
            FragmentAnimator parent = getFragmentAnimator((SupportDelegateProvider) act);
            fragmentAnimator = parent != null ? parent : AndroidLibrary.getInstance().getFragmentAnimator();
        }
        return fragmentAnimator;
    }
}
