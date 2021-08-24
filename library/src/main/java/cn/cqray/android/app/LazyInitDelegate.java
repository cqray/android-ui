package cn.cqray.android.app;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 延时初始化委托
 * <p>主要是用于实例初始化，但不确定后续是否使用，避免占用大量资源</p>
 * <p>onLazyInit()调用在LifecycleOwner->onCreate之后</p>
 * @author Cqray
 */
public class LazyInitDelegate implements LifecycleObserver {

    /** 生命周期管理 **/
    protected final LifecycleOwner mOwner;
    /** 延时加载控制器 **/
    private MutableLiveData<Object> mLazyInit;
    /** 任务池 **/
    private volatile DisposablePool mDisposablePool;

    public LazyInitDelegate(LifecycleOwner owner) {
        mOwner = owner;
        mOwner.getLifecycle().addObserver(this);
    }

    public LifecycleOwner getLifecycleOwner() {
        return mOwner;
    }

    /**
     * 获取任务池
     */
    public DisposablePool getDisposablePool() {
        if (mDisposablePool == null) {
            synchronized (LazyInitDelegate.class) {
                if (mDisposablePool == null) {
                    mDisposablePool = new DisposablePool(mOwner);
                }
            }
        }
        return mDisposablePool;
    }

    public void timer(@NonNull Consumer<Long> consumer, long delay) {
        getDisposablePool().timer(consumer, delay);
    }

    public void interval(@NonNull Consumer<Long> consumer, long period) {
        getDisposablePool().interval(consumer, period);
    }

    public void interval(@NonNull Consumer<Long> consumer, long initDelay, long period) {
        getDisposablePool().interval(consumer, initDelay, period);
    }

    public void addDisposable(Disposable disposable) {
        getDisposablePool().add(disposable);
    }

    /**
     * 延时初始化实现
     * @param owner LifecycleOwner
     */
    protected void onLazyInit(@NonNull LifecycleOwner owner) {}

    /**
     * 延时初始化
     */
    protected final void lazyInit() {
        if (mLazyInit == null) {
            synchronized (LazyInitDelegate.class) {
                if (mLazyInit == null) {
                    mLazyInit = new MutableLiveData<>(new Object());
                    mLazyInit.observe(mOwner, new Observer<Object>() {
                        @Override
                        public void onChanged(Object o) {
                            onLazyInit(mOwner);
                        }
                    });
                }
            }
        }
    }
}
