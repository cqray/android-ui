package cn.cqray.android.app;

import android.content.Context;
import android.widget.Toast;

import cn.cqray.android.AndroidLibrary;

/**
 * 默认Toast适配器
 * @author Cqray
 */
class DefaultToastAdapter implements ToastAdapter {

    @Override
    public void error(String text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }

    @Override
    public void info(String text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }

    @Override
    public void success(String text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }

    @Override
    public void warning(String text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }

    private Context getContext() {
        return AndroidLibrary.getInstance().getContext();
    }

}
