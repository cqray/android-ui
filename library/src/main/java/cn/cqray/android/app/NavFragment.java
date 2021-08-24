package cn.cqray.android.app;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.cqray.android.R;

/**
 * 导航Fragment入口Fragment
 * @author Cqray
 */
public class NavFragment extends SupportFragment {

    @Override
    public void onCreating(@Nullable Bundle savedInstanceState) {
        super.onCreating(savedInstanceState);
        setNativeContentView(R.layout._core_layout_nav);
    }

    public void loadRootFragment(Class<? extends SupportFragment> root) {
        getSupportDelegate().loadRootFragment(R.id._core_content, root);
    }
}
