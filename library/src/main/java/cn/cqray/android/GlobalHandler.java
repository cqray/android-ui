package cn.cqray.android;

import androidx.annotation.NonNull;

import cn.cqray.android.app.SupportDelegateProvider;
import cn.cqray.android.app.SupportSwipeDelegate;
import cn.cqray.android.app.SupportToastDelegate;
import cn.cqray.android.widget.CommonToolbar;
import cn.cqray.android.widget.state.StateRefreshLayout;

/**
 * 全局处理器
 * @author Cqray
 */
public class GlobalHandler {

    /**
     * 处理标题栏
     * @param toolbar 标题栏
     */
    public void onHandleToolbar(@NonNull SupportDelegateProvider provider, @NonNull CommonToolbar toolbar) {}

    /**
     * 处理刷新控件
     * @param refreshLayout 刷新控件
     */
    public void onHandleRefreshLayout(@NonNull SupportDelegateProvider provider, @NonNull StateRefreshLayout refreshLayout) {}

    /**
     * 处理侧滑委托
     * @param delegate 侧滑委托
     */
    public void onHandleSwipeDelegate(@NonNull SupportDelegateProvider provider, @NonNull SupportSwipeDelegate delegate) {}

    /**
     * 处理侧滑委托
     * @param delegate 侧滑委托
     */
    public void onHandleToastDelegate(@NonNull SupportDelegateProvider provider, @NonNull SupportToastDelegate delegate) {}

}
