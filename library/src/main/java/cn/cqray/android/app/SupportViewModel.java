package cn.cqray.android.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.Stack;

/**
 * Support界面数据缓存
 * @author Cqray
 */
public final class SupportViewModel extends ViewModel {

    /** 当前FragmentId **/
    private int mFragmentId;
    /** 子FragmentId计数器 **/
    private int mIdCounter;
    /** 容器Id **/
    private int mContainerId;
    /** 回退栈 **/
    private final Stack<String> mBackStack;

    public SupportViewModel() {
        mContainerId = 100;
        mBackStack = new Stack<>();
    }

    @NonNull
    String getFragmentTag(@NonNull Fragment fragment) {
        return fragment.getClass().getName() + "-" + mFragmentId;
    }

    @NonNull
    String getFragmentTag(int index) {
        return mBackStack.get(index);
    }

    @NonNull
    String peekFragmentTag() {
        return mBackStack.peek();
    }

    int getBackStackCount() {
        return mBackStack.size();
    }

    void addToBackStack(String name) {
        mBackStack.add(name);
    }

    void popBackStack() {
        mBackStack.pop();
    }

    void popBackStack(int index) {
        if (mBackStack.size() > index) {
            mBackStack.subList(index, mBackStack.size()).clear();
        }
    }

    @NonNull
    Fragment generateFragment(@NonNull SupportFragmentManager sfm, @NonNull Class<?> clazz) {
        SupportDelegateProvider support = (SupportDelegateProvider) sfm.getFragmentManager()
                .getFragmentFactory()
                .instantiate(sfm.getActivity().getClassLoader(), clazz.getName());
        mFragmentId = mIdCounter;
        mIdCounter ++;
        return (Fragment) support;
    }

    int getContainerId() {
        return mContainerId;
    }

    boolean canPop() {
        return mBackStack.size() > 1;
    }

    /**
     * 是否是栈顶的Fragment
     * @param cls Fragment对应Class
     */
    boolean isLastFragment(Class<? extends SupportDelegateProvider> cls) {
        if (mBackStack.isEmpty()) {
            return false;
        }
        String backStackName = mBackStack.peek();
        return backStackName.split("-")[0].equals(cls.getName());
    }

    /**
     * 是否是栈底的Fragment
     * @param cls Fragment对应Class
     */
    boolean isFirstFragment(Class<? extends SupportDelegateProvider> cls) {
        if (mBackStack.isEmpty()) {
            return false;
        }
        String backStackName = mBackStack.firstElement();
        return backStackName.split("-")[0].equals(cls.getName());
    }

    void setContainerId(int containerId) {
        mContainerId = containerId;
    }

}
