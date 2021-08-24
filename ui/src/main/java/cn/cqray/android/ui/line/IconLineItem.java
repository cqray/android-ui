package cn.cqray.android.ui.line;

import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import cn.cqray.android.ui.R;
import cn.cqray.android.util.Colors;
import cn.cqray.android.util.Dimens;

/**
 * 带图标行
 * @author Cqray
 */
@SuppressWarnings("unchecked")
abstract class IconLineItem<T extends IconLineItem<T>> extends LineItem<T> {

    private int mIcon;
    private int mNext;
    private CharSequence mText;
    private int mTextSize;
    private int mTextColor;
    private int mTextColorRes;
    private transient ColorStateList mTextColorStateList;

    public IconLineItem(CharSequence text) {
        super();
        mText = text;
        mTextColor = Colors.text();
        mTextSize = Dimens.h3();
        mNext = R.drawable.def_line_next;
    }

    public T icon(@DrawableRes int resId) {
        mIcon = resId;
        return (T) this;
    }

    public T next(@DrawableRes int resId) {
        mNext = resId;
        return (T) this;
    }

    public T text(@StringRes int resId) {
        mText = getContext().getString(resId);
        return (T) this;
    }

    public T text(CharSequence text) {
        mText = text;
        return (T) this;
    }

    public T textColor(int color) {
        mTextColor = color;
        mTextColorStateList = null;
        return (T) this;
    }

    public T textColor(String color) {
        mTextColor = Color.parseColor(color);
        mTextColorStateList = null;
        return (T) this;
    }

    public T textColorRes(@ColorRes int resId) {
        mTextColorRes = resId;
        mTextColorStateList = null;
        return (T) this;
    }

    public T textSize(float size) {
        mTextSize = Dimens.toPx(size);
        return (T) this;
    }

    public T textSizeRes(@DimenRes int resId) {
        mTextSize = Dimens.get(resId);
        return (T) this;
    }

    public int getIconRes() {
        return mIcon;
    }

    public int getNextRes() {
        return mNext;
    }

    public CharSequence getText() {
        return mText;
    }

    public ColorStateList getTextColor() {
        if (mTextColorStateList == null) {
            if (mTextColorRes == 0) {
                mTextColorStateList = ColorStateList.valueOf(mTextColor);
            } else {
                mTextColorStateList = Colors.getStateList(mTextColorRes);
            }
        }
        return mTextColorStateList;
    }

    public int getTextSize() {
        return mTextSize;
    }
}

