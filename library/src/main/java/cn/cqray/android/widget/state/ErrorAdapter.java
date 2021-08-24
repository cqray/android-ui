package cn.cqray.android.widget.state;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.cqray.android.R;

/**
 * 错误界面适配器
 * @author Cqray
 */
public class ErrorAdapter extends StateAdapter {

    public ErrorAdapter() {
        super(R.layout._core_state_layout_error);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);
        ViewGroup parent = (ViewGroup) view;
        TextView btn = (TextView) parent.getChildAt(2);
        btn.setOnClickListener(v -> getRefreshLayout().setBusy());
        setText(getText());
    }

    public void setButtonVisible(final boolean visible) {
        post(() -> {
            ViewGroup parent = (ViewGroup) getView();
            TextView btn = (TextView) parent.getChildAt(2);
            btn.setVisibility(visible ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    protected void show(String text) {
        super.show(text);
        setText(text);
    }

    protected void setText(final String text) {
        post(() -> {
            ViewGroup parent = (ViewGroup) getView();
            TextView tv = (TextView) parent.getChildAt(1);
            tv.setText(TextUtils.isEmpty(text) ? "页面异常" : text);
        });
    }
}
