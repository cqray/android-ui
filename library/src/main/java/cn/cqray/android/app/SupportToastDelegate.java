package cn.cqray.android.app;

import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.GlobalHandler;
import cn.cqray.android.util.CheckUtils;

/**
 * Toast弹窗委托
 * @author Cqray
 */
public class SupportToastDelegate {

    private volatile ToastAdapter mToastAdapter;

    public SupportToastDelegate(SupportDelegateProvider provider) {
        CheckUtils.checkDelegateProvider(provider);
        GlobalHandler handler = AndroidLibrary.getInstance().getGlobalHandler();
        if (handler != null) {
            handler.onHandleToastDelegate(provider, this);
        }
    }

    public void error(String text) {
        getToastAdapter().error(text, 1500);
    }

    public void error(String text, int duration) {
        getToastAdapter().error(text, duration);
    }

    public void info(String text) {
        getToastAdapter().info(text, 1500);
    }

    public void info(String text, int duration) {
        getToastAdapter().info(text, duration);
    }

    public void success(String text) {
        getToastAdapter().success(text, 1500);
    }

    public void success(String text, int duration) {
        getToastAdapter().success(text, duration);
    }

    public void warning(String text) {
        getToastAdapter().warning(text, 1500);
    }

    public void warning(String text, int duration) {
        getToastAdapter().warning(text, duration);
    }

    public void setToastAdapter(ToastAdapter adapter) {
        synchronized (SupportToastDelegate.class) {
            mToastAdapter = adapter;
        }
    }

    private ToastAdapter getToastAdapter() {
        if (mToastAdapter == null) {
            synchronized (SupportToastDelegate.class) {
                if (mToastAdapter == null) {
                    mToastAdapter = new DefaultToastAdapter();
                }
            }
        }
        return mToastAdapter;
    }
}
