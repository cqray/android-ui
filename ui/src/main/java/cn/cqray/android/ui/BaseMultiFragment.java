package cn.cqray.android.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import cn.cqray.android.app.SupportDelegateProvider;
import cn.cqray.android.app.SupportFragment;

/**
 * 包含多个Fragment的Activity
 * @author Cqray
 */
public class BaseMultiFragment extends SupportFragment {

    private static final String TAG = "MultiFragment";
    private int mCurIndex = 0;
    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreating(@Nullable Bundle savedInstanceState) {
        super.onCreating(savedInstanceState);
        mFragmentManager = getChildFragmentManager();
    }

    @Override
    public boolean onFragmentPopSupport() {
        return false;
    }

    public void loadMultiFragments(int containerId, Fragment... fragments) {
        if (mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state.");
            return;
        }
        if (fragments == null || fragments.length == 0) {
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mFragments = new Fragment[fragments.length];
        for (int i = 0; i < fragments.length; i++) {
            if (!(fragments[i] instanceof SupportDelegateProvider)) {
                throw new IllegalArgumentException(fragments[i].getClass().getSimpleName() + " must extends SupportDelegateProvider.");
            }
            ft.add(containerId, fragments[i]);
            ft.setMaxLifecycle(fragments[i], i == mCurIndex ? Lifecycle.State.RESUMED : Lifecycle.State.CREATED);
            mFragments[i] = fragments[i];
        }
        ft.commitAllowingStateLoss();
    }

    public void loadMultiFragments(int containerId, Class<? extends Fragment>... fragmentClasses) {
        if (mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state.");
            return;
        }
        if (fragmentClasses == null || fragmentClasses.length == 0) {
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mFragments = new Fragment[fragmentClasses.length];
        for (int i = 0; i < fragmentClasses.length; i++) {
            if (!(SupportDelegateProvider.class.isAssignableFrom(fragmentClasses[i]))) {
                throw new IllegalArgumentException(fragmentClasses[i].getSimpleName() + " must extends SupportDelegateProvider.");
            }
            Fragment fragment = instantiateFragment(fragmentClasses[i]);
            ft.add(containerId, fragment);
            ft.setMaxLifecycle(fragment, i == mCurIndex ? Lifecycle.State.RESUMED : Lifecycle.State.CREATED);
            mFragments[i] = fragment;
        }
        ft.commit();
    }

    public void showFragment(int index) {
        if (mFragments == null) {
            return;
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragments[index];
        if (index == mCurIndex) {
            Lifecycle.State state = fragment.getLifecycle().getCurrentState();
            if (!state.isAtLeast(Lifecycle.State.RESUMED)) {
                ft.show(fragment);
                ft.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
                ft.commitAllowingStateLoss();
            }
        } else {
            Fragment cur = mFragments[mCurIndex];
            ft.hide(cur);
            ft.setMaxLifecycle(cur, Lifecycle.State.STARTED);
            ft.show(fragment);
            ft.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            ft.commitAllowingStateLoss();
        }
        mCurIndex = index;
    }

    public int getCurrentIndex() {
        return mCurIndex;
    }

    @NonNull
    protected Fragment instantiateFragment(@NonNull Class<? extends Fragment> clazz) {
        return getChildFragmentManager().getFragmentFactory()
                .instantiate(requireContext().getClassLoader(), clazz.getName());
    }

}
