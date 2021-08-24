package cn.cqray.android.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RefreshHelper {

    private static volatile RefreshHelper mInstance;

    private DefaultRefreshHeaderCreator defaultHeaderCreator;
    private DefaultRefreshFooterCreator defaultFooterCreator;
    private LinkedHashMap<LifecycleOwner, DefaultRefreshHeaderCreator> headerCreators;
    private LinkedHashMap<LifecycleOwner, DefaultRefreshFooterCreator> footerCreators;

    private RefreshHelper() {
        headerCreators = new LinkedHashMap<>();
        footerCreators = new LinkedHashMap<>();
    }

    public static RefreshHelper get() {
        if (mInstance == null) {
            synchronized (RefreshHelper.class) {
                if (mInstance == null) {
                    mInstance = new RefreshHelper();
                }
            }
        }
        return mInstance;
    }

    public void setRefreshHeaderCreator(@Nullable LifecycleOwner owner, @NonNull DefaultRefreshHeaderCreator creator) {
        if (owner == null) {
            defaultHeaderCreator = creator;
            SmartRefreshLayout.setDefaultRefreshHeaderCreator(creator);
        } else {
            headerCreators.put(owner, creator);
            SmartRefreshLayout.setDefaultRefreshHeaderCreator(creator);
//            owner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
//                @Override
//                public void onDestroy(@NonNull LifecycleOwner owner) {
//                    headerCreators.remove(owner);
//                    if (headerCreators.isEmpty()) {
//                        SmartRefreshLayout.setDefaultRefreshHeaderCreator(defaultHeaderCreator);
//                    } else {
//                        SmartRefreshLayout.setDefaultRefreshHeaderCreator(getLast(headerCreators).getValue());
//                    }
//                }
//            });
        }
    }

    public void setRefreshFooterCreator(@Nullable LifecycleOwner owner, @NonNull DefaultRefreshFooterCreator creator) {
        if (owner == null) {
            defaultFooterCreator = creator;
            SmartRefreshLayout.setDefaultRefreshFooterCreator(creator);
        } else {
            footerCreators.put(owner, creator);
            SmartRefreshLayout.setDefaultRefreshFooterCreator(creator);
//            owner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
//                @Override
//                public void onDestroy(@NonNull LifecycleOwner owner) {
//                    footerCreators.remove(owner);
//                    if (footerCreators.isEmpty()) {
//                        SmartRefreshLayout.setDefaultRefreshFooterCreator(defaultFooterCreator);
//                    } else {
//                        SmartRefreshLayout.setDefaultRefreshFooterCreator(getLast(footerCreators).getValue());
//                    }
//                }
//            });
        }
    }

    private <K, V> Map.Entry<K, V> getLast(@NonNull LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }
}
