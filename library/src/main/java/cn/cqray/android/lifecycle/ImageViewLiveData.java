package cn.cqray.android.lifecycle;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import cn.cqray.android.AndroidLibrary;

/**
 * 图片的LiveData数据
 * @author Cqray
 */
public class ImageViewLiveData extends ViewLiveData {

    private MutableLiveData<Integer> mTintColor;
    private MutableLiveData<Drawable> mImage;

    public synchronized MutableLiveData<Integer> getTintColor() {
        if (mTintColor == null) {
            mTintColor = new MutableLiveData<>();
        }
        return mTintColor;
    }

    public synchronized MutableLiveData<Drawable> getImage() {
        if (mImage == null) {
            mImage = new MutableLiveData<>();
        }
        return mImage;
    }

    public void setTintColor(MutableLiveData<Integer> tintColor) {
        mTintColor = tintColor;
    }

    public void setTintColor(int color) {
        getTintColor().postValue(color);
    }

    public void setImage(MutableLiveData<Drawable> image) {
        mImage = image;
    }

    public void setImageResource(@DrawableRes int resId) {
        getImage().postValue(ContextCompat.getDrawable(AndroidLibrary.getInstance().getContext(), resId));
    }
}
