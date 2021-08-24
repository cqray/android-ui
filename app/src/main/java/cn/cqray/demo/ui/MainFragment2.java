package cn.cqray.demo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.cqray.android.app.SupportFragment;

public class MainFragment2 extends SupportFragment {

    @Override
    protected void onCreating(@Nullable  Bundle savedInstanceState) {
        super.onCreating(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportDelegate().findViewById(R.id.tv)
                .setOnClickListener(v -> {
                    Log.e("数据", "点击");
                    pop();
                });
    }

    @Override
    public boolean onSwipeBackSupport() {
        return true;
    }
}
