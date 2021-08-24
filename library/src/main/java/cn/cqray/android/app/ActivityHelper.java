package cn.cqray.android.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.Stack;

/**
 * Activity管理辅助类
 * @author Cqray
 */
public final class ActivityHelper {

    /** 单例模式 **/
    private static class Holder {
        static final ActivityHelper INSTANCE = new ActivityHelper();
    }

    /** 前台活动数量 **/
    private int mAliveCount;
    /** Activity堆栈 **/
    private final Stack<Activity> mActivityStack = new Stack<>();
    /** 应用状态 **/
    private final MutableLiveData<ApplicationState> mApplicationState = new MutableLiveData<>();

    private ActivityHelper() {}

    /**
     * 获取应用状态
     */
    public static MutableLiveData<ApplicationState> getApplicationState() {
        return Holder.INSTANCE.mApplicationState;
    }

    /**
     * 通过Application初始化
     * @param application Application
     */
    public static void initialize(Application application) {
        // 防止重复初始化
        synchronized (Holder.INSTANCE.mApplicationState) {
            if (Holder.INSTANCE.mApplicationState.getValue() != null) {
                return;
            }
        }
        // 设置状态为初始化
        Holder.INSTANCE.mApplicationState.postValue(ApplicationState.INITIALIZE);
        // 注册监听事件
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                Holder.INSTANCE.mActivityStack.add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (Holder.INSTANCE.mAliveCount == 0) {
                    Holder.INSTANCE.mApplicationState.postValue(ApplicationState.FOREGROUND);
                }
                Holder.INSTANCE.mAliveCount ++;
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                if (Holder.INSTANCE.mAliveCount == 1) {
                    Holder.INSTANCE.mApplicationState.postValue(ApplicationState.BACKGROUND);
                }
                Holder.INSTANCE.mAliveCount --;
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Holder.INSTANCE.mActivityStack.remove(activity);
                if (Holder.INSTANCE.mActivityStack.size() == 0) {
                    Holder.INSTANCE.mApplicationState.postValue(ApplicationState.EXITED);
                }
            }
        });
    }

    /**
     * 指定Activity出栈
     * @param activity 指定Activity
     */
    public static void pop(@NonNull Activity activity) {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        for (Activity act : activities) {
            if (act == activity && !activity.isFinishing()) {
                activity.finish();
                activities.remove(activity);
                break;
            }
        }
    }

    /**
     * 指定Activity出栈
     * @param clazz 指定Activity
     */
    public static void pop(@NonNull Class<? extends Activity> clazz) {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        for (Activity act : activities) {
            if (clazz == act.getClass() && !act.isFinishing()) {
                act.finish();
                activities.remove(act);
                break;
            }
        }
    }

    /**
     * 除了指定Activity所有Activity出栈
     * @param activity 指定Activity
     */
    public static void popAllExclusive(@NonNull Activity activity) {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        for (Activity act : activities) {
            if (act == activity) {
                continue;
            }
            if (!act.isFinishing()) {
                act.finish();
            }
        }
        activities.clear();
        activities.add(activity);
    }

    /**
     * 除了指定Activity所有Activity出栈
     * @param clazz 指定Activity
     */
    public static void popAllExclusive(Class<? extends Activity> clazz) {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        Activity activity = null;
        for (Activity act : activities) {
            if (act.getClass() == clazz) {
                activity = act;
                continue;
            }
            if (!act.isFinishing()) {
                act.finish();
            }
        }
        activities.clear();
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * 所有Activity出栈
     */
    public static void popAll() {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        for (Activity act : activities) {
            if (!act.isFinishing()) {
                act.finish();
            }
        }
        activities.clear();
    }

    /**
     * 退出Activity直到指定Activity
     * @param clazz 指定Activity
     * @param inclusive 退出是否包含指定Activity
     */
    public static void popTo(Class<? extends Activity> clazz, boolean inclusive) {
        Stack<Activity> activities = Holder.INSTANCE.mActivityStack;
        int count = activities.size();
        for (int i = count - 1; i >= 0; i--) {
            Activity act = activities.get(i);
            if (act.getClass() != clazz) {
                remove(act, i);
            } else {
                if (inclusive) {
                    remove(act, i);
                }
                break;
            }
        }
    }

    /**
     * 移除指定位置的Activity
     * @param act Activity
     * @param index 索引
     */
    private static void remove(@NonNull Activity act, int index) {
        Holder.INSTANCE.mActivityStack.remove(index);
        if (!act.isFinishing()) {
            act.finish();
        }
    }

    /**
     * 获取栈顶Activity，可为null
     */
    @Nullable
    public static Activity peek() {
        return count() > 0 ? Holder.INSTANCE.mActivityStack.lastElement() : null;
    }

    /**
     * 获取栈顶Activity，不为null，会报异常
     */
    @NonNull
    public static Activity requirePeek() {
        return Holder.INSTANCE.mActivityStack.lastElement();
    }

    /**
     * 栈内Activity数量
     */
    public static int count() {
        return Holder.INSTANCE.mActivityStack.size();
    }

    /**
     * 指定Activity是否在栈顶
     * @param activity 指定Activity
     */
    public static boolean isTop(@NonNull Activity activity) {
        return peek() == activity;
    }

    /**
     * 指定Activity是否在栈顶
     * @param clazz 指定Activity
     */
    public static boolean isTop(@NonNull Class<? extends Activity> clazz) {
        Activity act = peek();
        return act != null && clazz.equals(act.getClass());
    }

    /**
     * 是否包含指定Activity
     * @param activity 指定Activity
     */
    public static boolean contains(@NonNull Activity activity) {
        return Holder.INSTANCE.mActivityStack.contains(activity);
    }

    /**
     * 是否包含指定Activity
     * @param clazz 指定Activity
     */
    public static boolean contains(@NonNull Class<? extends Activity> clazz) {
        for (Activity act : Holder.INSTANCE.mActivityStack) {
            if (clazz.getName().equals(act.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

}
