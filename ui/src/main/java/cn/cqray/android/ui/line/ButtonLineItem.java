package cn.cqray.android.ui.line;

import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;

import cn.cqray.android.util.Colors;
import cn.cqray.android.util.Dimens;

/**
 * 按钮行
 * @author Cqray
 */
public class ButtonLineItem extends LineItem<ButtonLineItem> {

    private CharSequence mText;
    private int mTextSize;
    private int mTextColor;
    private int mTextColorRes;
    private transient ColorStateList mTextColorStateList;

    public ButtonLineItem(CharSequence text) {
        mText = text;
        mTextColor = Colors.text();
        mTextSize = Dimens.h3();
        dividerHeight(0);
    }

    public ButtonLineItem text(@StringRes int resId) {
        mText = getContext().getString(resId);
        return this;
    }

    public ButtonLineItem text(CharSequence text) {
        mText = text;
        return this;
    }

    public ButtonLineItem textColor(int color) {
        mTextColor = color;
        mTextColorStateList = null;
        return this;
    }

    public ButtonLineItem textColor(String color) {
        mTextColor = Color.parseColor(color);
        return this;
    }

    public ButtonLineItem textColorRes(@ColorRes int resId) {
        mTextColorRes = resId;
        mTextColorStateList = null;
        return this;
    }

    public ButtonLineItem textSize(float size) {
        mTextSize = Dimens.toPx(size);
        return this;
    }

    public ButtonLineItem textSizeRes(@DimenRes int resId) {
        mTextSize = Dimens.get(resId);
        return this;
    }

    public CharSequence getText() {
        return mText;
    }

    public ColorStateList getTextColor() {
        if (mTextColorStateList == null) {
            if (mTextColorRes != 0) {
                mTextColorStateList = Colors.getStateList(mTextColorRes);
            }
            mTextColorStateList = ColorStateList.valueOf(mTextColor);
        }
        return mTextColorStateList;
    }

    public int getTextSize() {
        return mTextSize;
    }

    @Override
    public int getItemType() {
        return LineItem.BUTTON;
    }
}
