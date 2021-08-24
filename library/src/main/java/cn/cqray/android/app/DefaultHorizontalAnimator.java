package cn.cqray.android.app;

import cn.cqray.android.R;

/**
 * 默认横向动画
 * @author Cqray
 */
public class DefaultHorizontalAnimator extends FragmentAnimator {

    public DefaultHorizontalAnimator() {
        super(R.anim._core_horizontal_from_right,
                R.anim._core_horizontal_to_left,
                R.anim._core_horizontal_from_left,
                R.anim._core_horizontal_to_right);
    }
}
