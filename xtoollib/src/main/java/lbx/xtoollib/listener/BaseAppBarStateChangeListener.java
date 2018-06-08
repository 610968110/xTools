package lbx.xtoollib.listener;

import android.support.design.widget.AppBarLayout;

/**
 * @author lbx
 *         AppBarLayout 开/关回调
 *         addOnOffsetChangedListener
 */
public abstract class BaseAppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        /**
         * 开
         */
        EXPANDED,
        /**
         * 关
         */
        COLLAPSED,
        /**
         * 中间状态
         */
        IDLE
    }

    /**
     * 当前状态
     */
    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, i);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, i);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, i);
            }
            mCurrentState = State.IDLE;
        }
        onScrollChanged(appBarLayout, i);
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int i);

    public abstract void onScrollChanged(AppBarLayout appBarLayout, int i);
}