package cn.cqray.android.app;

import androidx.annotation.NonNull;

/**
 * Support类界面代理提供者
 * @author Cqray
 */
public interface SupportDelegateProvider {

    /**
     * 获取界面委托
     * @return 界面委托
     */
    @NonNull
    SupportDelegate getSupportDelegate();

    /**
     * 创建Fragment动画
     * @return Fragment动画
     */
    FragmentAnimator onCreateFragmentAnimator();

    /**
     * 是否支持回退
     * @return true 拦截 false 不拦截
     */
    boolean onBackPressedSupport();

    /**
     * 是否支付侧滑回退
     * @return true 支持 false 不支持
     */
    boolean onSwipeBackSupport();

    /**
     * 针对Fragment,如果不是通过loadRootFragment的方式加载Fragment,
     * 则代表是自定义的加载Fragment方式，则应该返回false区分。
     */
    boolean onFragmentPopSupport();
}
