package cn.cqray.android.lifecycle;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import cn.cqray.android.util.Dimens;

/**
 * 基础控件对应LiveData
 * @author Cqray
 */
public class ViewLiveData {

    private MutableLiveData<Integer> mVisibility;
    private MutableLiveData<Boolean> mClickable;
    private MutableLiveData<Boolean> mFocusable;
    private MutableLiveData<Boolean> mEnable;
    private MutableLiveData<Integer> mWidth;
    private MutableLiveData<Integer> mHeight;
    private MutableLiveData<View.OnClickListener> mOnClickListener;
    private MutableLiveData<View.OnFocusChangeListener> mOnFocusChangeListener;
    private MutableLiveData<Drawable> mBackground;
    private MutableLiveData<Integer> mBackgroundColor;
    private MutableLiveData<Integer> mGravity;
    private MutableLiveData<int []> mMargin;
    private MutableLiveData<int []> mPadding;
    private MutableLiveData<Object> mExtras;

    public synchronized MutableLiveData<Integer> getVisibility() {
        if (mVisibility == null) {
            mVisibility = new MutableLiveData<>(View.VISIBLE);
        }
        return mVisibility;
    }

    public synchronized MutableLiveData<Boolean> getClickable() {
        if (mClickable == null) {
            mClickable = new MutableLiveData<>(false);
        }
        return mClickable;
    }

    public synchronized MutableLiveData<Boolean> getFocusable() {
        if (mFocusable == null) {
            mFocusable = new MutableLiveData<>(false);
        }
        return mFocusable;
    }

    public synchronized MutableLiveData<Boolean> getEnable() {
        if (mEnable == null) {
            mEnable = new MutableLiveData<>(true);
        }
        return mEnable;
    }

    public synchronized MutableLiveData<Integer> getWidth() {
        if (mWidth == null) {
            mWidth = new MutableLiveData<>(-2);
        }
        return mWidth;
    }

    public synchronized MutableLiveData<Integer> getHeight() {
        if (mHeight == null) {
            mHeight = new MutableLiveData<>(-2);
        }
        return mHeight;
    }

    public synchronized MutableLiveData<View.OnClickListener> getOnClickListener() {
        if (mOnClickListener == null) {
            mOnClickListener = new MutableLiveData<>();
        }
        return mOnClickListener;
    }

    public synchronized MutableLiveData<View.OnFocusChangeListener> getOnFocusChangeListener() {
        if (mOnFocusChangeListener == null) {
            mOnFocusChangeListener = new MutableLiveData<>();
        }
        return mOnFocusChangeListener;
    }

    public synchronized MutableLiveData<Drawable> getBackground() {
        if (mBackground == null) {
            mBackground = new MutableLiveData<>();
        }
        return mBackground;
    }

    public synchronized MutableLiveData<Integer> getBackgroundColor() {
        if (mBackgroundColor == null) {
            mBackgroundColor = new MutableLiveData<>();
        }
        return mBackgroundColor;
    }

    public synchronized MutableLiveData<Integer> getGravity() {
        if (mGravity == null) {
            mGravity = new MutableLiveData<>();
        }
        return mGravity;
    }

    public synchronized MutableLiveData<int [] > getMargin() {
        if (mMargin == null) {
            mMargin = new MutableLiveData<>();
        }
        return mMargin;
    }

    public synchronized MutableLiveData<int []> getPadding() {
        if (mPadding == null) {
            mPadding = new MutableLiveData<>();
        }
        return mPadding;
    }

    public synchronized MutableLiveData<Object> getExtras() {
        if (mExtras == null) {
            mExtras = new MutableLiveData<>();
        }
        return mExtras;
    }

    public void setVisibility(MutableLiveData<Integer> visibility) {
        mVisibility = visibility;
    }

    public void setVisible(boolean visible) {
        getVisibility().postValue(visible ? View.VISIBLE : View.GONE);
    }

    public void setClickable(MutableLiveData<Boolean> clickable) {
        mClickable = clickable;
    }

    public void setClickable(boolean clickable) {
        getClickable().postValue(clickable);
    }

    public void setFocusable(MutableLiveData<Boolean> focusable) {
        mFocusable = focusable;
    }

    public void setFocusable(boolean focusable) {
        getFocusable().postValue(focusable);
    }

    public void setEnable(MutableLiveData<Boolean> enable) {
        mEnable = enable;
    }

    public void setEnable(boolean enable) {
        getEnable().postValue(enable);
    }

    public void setWidth(MutableLiveData<Integer> width) {
        mWidth = width;
    }

    public void setWidth(float width) {
        getWidth().postValue(Dimens.toPx(width));
    }

    public void setHeight(MutableLiveData<Integer> height) {
        mHeight = height;
    }

    public void setHeight(float height) {
        getHeight().postValue(Dimens.toPx(height));
    }

    public void setOnClickListener(MutableLiveData<View.OnClickListener> onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        getOnClickListener().postValue(listener);
    }

    public void setOnFocusChangeListener(MutableLiveData<View.OnFocusChangeListener> onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        getOnFocusChangeListener().postValue(listener);
    }

    public void setBackground(MutableLiveData<Drawable> background) {
        mBackground = background;
    }

    public void setBackground(Drawable background) {
        getBackground().postValue(background);
    }

    public void setBackgroundColor(MutableLiveData<Integer> backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        getBackgroundColor().postValue(backgroundColor);
    }

    public void setGravity(MutableLiveData<Integer> gravity) {
        mGravity = gravity;
    }

    public void setGravity(int gravity) {
        getGravity().postValue(gravity);
    }

    public void setMargin(MutableLiveData<int []> margin) {
        mMargin = margin;
    }

    public void setMargin(float left, float top, float right, float bottom) {
        int [] array = new int [4];
        array[0] = Dimens.toPx(left);
        array[1] = Dimens.toPx(top);
        array[2] = Dimens.toPx(right);
        array[3] = Dimens.toPx(bottom);
        getMargin().postValue(array);
    }

    public void setPadding(MutableLiveData<int []> padding) {
        mPadding = padding;
    }

    public void setPadding(float left, float top, float right, float bottom) {
        int [] array = new int [4];
        array[0] = Dimens.toPx(left);
        array[1] = Dimens.toPx(top);
        array[2] = Dimens.toPx(right);
        array[3] = Dimens.toPx(bottom);
        getPadding().postValue(array);
    }

    public void setExtras(Object extras) {
        getExtras().postValue(extras);
    }

    public void attachView(@NonNull LifecycleOwner owner, @NonNull View view) {
        getVisibility().observe(owner, view::setVisibility);
        getClickable().observe(owner, view::setClickable);
        getFocusable().observe(owner, view::setFocusable);
        getEnable().observe(owner, view::setEnabled);
        getWidth().observe(owner, integer -> {
            view.getLayoutParams().width = integer;
            view.requestLayout();
        });
        getHeight().observe(owner, integer -> {
            view.getLayoutParams().height = integer;
            view.requestLayout();
        });
        getOnClickListener().observe(owner, view::setOnClickListener);
        getOnFocusChangeListener().observe(owner, view::setOnFocusChangeListener);
        getBackground().observe(owner, drawable -> ViewCompat.setBackground(view, drawable));
        getBackgroundColor().observe(owner, view::setBackgroundColor);
        getMargin().observe(owner, ints -> {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(ints[0], ints[1], ints[2], ints[3]);
            view.requestLayout();
        });
        getPadding().observe(owner, ints -> view.setPadding(ints[0], ints[1], ints[2], ints[3]));
    }
}
