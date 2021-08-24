package cn.cqray.android.widget.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import cn.cqray.android.R;
import cn.cqray.android.widget.LoadingView;

/**
 * 忙碌界面适配器
 * @author Cqray
 */
public class BusyAdapter extends StateAdapter {

    public BusyAdapter() {
        super(R.layout._core_state_layout_busy);
    }

    @Override
    protected void onViewCreated(@NonNull View view) {
        super.onViewCreated(view);
        Context context = view.getContext();
        int primary = ContextCompat.getColor(context, R.color.colorPrimary);
        int accent = ContextCompat.getColor(context, R.color.colorAccent);
        ViewGroup parent = (ViewGroup) view;
        LoadingView lv = (LoadingView) parent.getChildAt(0);
        lv.setArcCount(4);
        lv.setArcShakeRatio(0.2f);
        lv.setArcStrokeWidth(5);
        lv.setArcColors(primary, accent);
    }
}
