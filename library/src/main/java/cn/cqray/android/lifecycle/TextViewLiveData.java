package cn.cqray.android.lifecycle;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import cn.cqray.android.util.Colors;
import cn.cqray.android.util.Dimens;

/**
 * 文本LiveData
 * @author LeiJue
 */
public class TextViewLiveData extends ViewLiveData {

    /** 文本内容 **/
    private MutableLiveData<String> mText;
    /** 文本颜色 **/
    private MutableLiveData<Integer> mTextColor;
    /** 文本大小 **/
    private MutableLiveData<Float> mTextSize;
    /** 输入类型 **/
    private MutableLiveData<Integer> mInputType;

    public synchronized MutableLiveData<String> getText() {
        if (mText == null) {
            mText = new MutableLiveData<>();
        }
        return mText;
    }

    public synchronized MutableLiveData<Integer> getTextColor() {
        if (mTextColor == null) {
            mTextColor = new MutableLiveData<>(Colors.text());
        }
        return mTextColor;
    }

    public synchronized MutableLiveData<Float> getTextSize() {
        if (mTextSize == null) {
            mTextSize = new MutableLiveData<>(Dimens.dpBody());
        }
        return mTextSize;
    }

    public synchronized MutableLiveData<Integer> getInputType() {
        if (mInputType == null) {
            mInputType = new MutableLiveData<>(EditorInfo.TYPE_CLASS_TEXT);
        }
        return mInputType;
    }

    public void setText(MutableLiveData<String> text) {
        mText = text;
    }

    public void setText(String text) {
        getText().postValue(text);
    }

    public void setTextColor(MutableLiveData<Integer> textColor) {
        mTextColor = textColor;
    }

    public void setTextColor(int color) {
        getTextColor().postValue(color);
    }

    public void setTextSize(MutableLiveData<Float> textSize) {
        mTextSize = textSize;
    }

    public void setTextSize(float textSize) {
        getTextSize().postValue(textSize);
    }

    public void setInputType(MutableLiveData<Integer> inputType) {
        mInputType = inputType;
    }

    public void setInputText() {
        getInputType().postValue(EditorInfo.TYPE_CLASS_TEXT);
    }

    public void setInputPassword() {
        getInputType().postValue(EditorInfo.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void setInputVisiblePassword() {
        getInputType().postValue(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }

    public boolean isInputPassword() {
        int inputType = getInputType().getValue() == null ? 0 : getInputType().getValue() ;
        return inputType == (EditorInfo.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    public void attachView(@NonNull LifecycleOwner owner, @NonNull View view) {
        super.attachView(owner, view);
        if (view instanceof TextView) {
            getText().observe(owner, ((TextView) view)::setText);
            getTextColor().observe(owner, ((TextView) view)::setTextColor);
            getTextSize().observe(owner, ((TextView) view)::setTextSize);
            getInputType().observe(owner, ((TextView) view)::setInputType);
        }
    }
}
