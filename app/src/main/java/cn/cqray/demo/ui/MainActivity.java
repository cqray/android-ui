package cn.cqray.demo.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.cqray.android.app.NavActivity;

public class MainActivity extends NavActivity {

    @Override
    public void onCreating(@Nullable Bundle savedInstanceState) {
        super.onCreating(savedInstanceState);
        loadRootFragment(MainFragment.class);


        String s = getIntent().getStringExtra("ww");

        Log.e("数据", "获取大的|" + s);

    }

    @Override
    public boolean onSwipeBackSupport() {
        return true;
    }
}