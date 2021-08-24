package cn.cqray.demo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.cqray.android.app.NavIntent;
import cn.cqray.android.app.SupportFragment;

public class MainFragment extends SupportFragment {

    @Override
    protected void onCreating(@Nullable  Bundle savedInstanceState) {
        super.onCreating(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportDelegate().findViewById(R.id.tv)
                .setOnClickListener(v -> {
                    Log.e("数据", "点击");

                    NavIntent intent = new NavIntent(MainFragment2.class);
                    intent.put("ww", "ssssss");
                    start(intent);
//                    showInfo("hello");
//                    setError();

//                    timer(aLong -> setIdle(), 1500);
                });
    }

    @Override
    public boolean onSwipeBackSupport() {
        return false;
    }
}
