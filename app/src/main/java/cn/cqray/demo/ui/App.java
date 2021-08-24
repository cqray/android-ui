package cn.cqray.demo.ui;

import android.app.Application;

import cn.cqray.android.AndroidLibrary;
import cn.cqray.android.anim.DefaultHorizontalAnimator;
import cn.cqray.android.app.SupportDelegateProvider;
import cn.cqray.android.app.SupportHandler;
import cn.cqray.android.state.StateRefreshLayout;
import cn.cqray.android.swipe.SwipeBackDelegate;
import cn.cqray.android.widget.CommonToolbar;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        AndroidLibrary.getInstance()
//                .initialize(this);

        AndroidLibrary.getInstance()
                .initialize(this)
                .fragmentAnimator(new DefaultHorizontalAnimator())
                .swipeBackSupport(true)
                .toolbarHandler(new SupportHandler<CommonToolbar>() {
                    @Override
                    public void onHandle(SupportDelegateProvider provider, CommonToolbar toolbar) {

                    }
                })
                .swipeBackHandler(new SupportHandler<SwipeBackDelegate>() {
                    @Override
                    public void onHandle(SupportDelegateProvider provider, SwipeBackDelegate swipeBackDelegate) {

                    }
                })
                .refreshLayoutHandler(new SupportHandler<StateRefreshLayout>() {
                    @Override
                    public void onHandle(SupportDelegateProvider provider, StateRefreshLayout stateRefreshLayout) {

                    }
                });

    }
}
