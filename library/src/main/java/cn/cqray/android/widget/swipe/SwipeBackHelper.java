package cn.cqray.android.widget.swipe;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Stack;

import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.GlobalHandler;
import cn.cqray.android.app.SupportSwipeDelegate;
import cn.cqray.android.util.CheckUtils;
import cn.cqray.android.app.SupportDelegateProvider;

/**
 * 侧滑返回辅助类
 * @author Cqray
 */
public class SwipeBackHelper {

    /** Activity相关堆 **/
    private static final Stack<SupportSwipeDelegate> ACTIVITY_DELEGATES = new Stack<>();
    /** Fragment相关堆 **/
    private static final Stack<SupportSwipeDelegate> FRAGMENT_DELEGATES = new Stack<>();

    public static void addSwipeDelegate(@NonNull SupportSwipeDelegate delegate) {
        SupportDelegateProvider provider = delegate.getSupportDelegateProvider();
        GlobalHandler handler = AndroidLibrary.getInstance().getGlobalHandler();
        if (provider instanceof Activity) {
            if (!ACTIVITY_DELEGATES.contains(delegate)) {
                ACTIVITY_DELEGATES.push(delegate);
                if (handler != null) {
                    handler.onHandleSwipeDelegate(provider, delegate);
                }
            }
        } else {
            if (!FRAGMENT_DELEGATES.contains(delegate)) {
                FRAGMENT_DELEGATES.push(delegate);
                if (handler != null) {
                    handler.onHandleSwipeDelegate(provider, delegate);
                }
            }
        }
    }

    public static void removeSwipeDelegate(@NonNull SupportSwipeDelegate delegate) {
        ACTIVITY_DELEGATES.remove(delegate);
        FRAGMENT_DELEGATES.remove(delegate);
    }

    @Nullable
    private static SupportSwipeDelegate getSwipeDelegate(@NonNull SupportDelegateProvider provider) {
        for (SupportSwipeDelegate delegate : ACTIVITY_DELEGATES) {
            if (delegate.getSupportDelegateProvider() == provider) {
                return delegate;
            }
        }
        return null;
    }

    @Nullable
    static SupportSwipeDelegate getPreSwipeDelegate(@NonNull SupportDelegateProvider provider) {
        SupportSwipeDelegate delegate;
        if ((delegate = getSwipeDelegate(provider)) != null) {
            int preIndex = ACTIVITY_DELEGATES.indexOf(delegate) - 1;
            return preIndex >= 0 ? ACTIVITY_DELEGATES.get(preIndex) : null;
        }
        return null;
    }

    @NonNull
    public static SupportSwipeDelegate with(AppCompatActivity activity) {
        CheckUtils.checkDelegateProvider(activity);
        SupportDelegateProvider provider = (SupportDelegateProvider) activity;
        SupportSwipeDelegate delegate = getSwipeDelegate(provider);
        if (delegate == null) {
            delegate = new SupportSwipeDelegate(provider);
        }
        return delegate;
    }

    @NonNull
    public static SupportSwipeDelegate with(Fragment fragment) {
        CheckUtils.checkDelegateProvider(fragment);
        SupportDelegateProvider provider = (SupportDelegateProvider) fragment;
        SupportSwipeDelegate delegate = getSwipeDelegate(provider);
        if (delegate == null) {
            delegate = new SupportSwipeDelegate(provider);
        }
        return delegate;
    }
}
