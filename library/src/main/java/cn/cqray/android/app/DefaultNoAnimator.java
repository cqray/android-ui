package cn.cqray.android.app;

import cn.cqray.android.R;

/**
 * 默认无动画
 * @author Cqray
 */
public class DefaultNoAnimator extends FragmentAnimator {

    public DefaultNoAnimator() {
        super(R.anim._core_no_anim,
                R.anim._core_no_anim,
                R.anim._core_no_anim,
                R.anim._core_no_anim);
    }
}
