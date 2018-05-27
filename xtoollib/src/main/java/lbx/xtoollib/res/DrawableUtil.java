package lbx.xtoollib.res;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @author lbx
 */
public class DrawableUtil {

    private static DrawableUtil INSTANCE;

    public static DrawableUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (DrawableUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DrawableUtil();
                }
            }
        }
        return INSTANCE;
    }

    private DrawableUtil() {
    }

    /**
     * 获取一个shape对象
     */
    public GradientDrawable createGradientDrawable(int color, int radius) {
        // xml中定义的shape标签 对应此类
        GradientDrawable shape = new GradientDrawable();
        //矩形
        shape.setShape(GradientDrawable.RECTANGLE);
        //圆角半径
        shape.setCornerRadius(radius);
        //颜色
        shape.setColor(color);
        return shape;
    }

    /**
     * 获取状态选择器
     */
    public StateListDrawable createSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable selector = new StateListDrawable();
        // 按下图片
        selector.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        // 默认图片
        selector.addState(new int[]{}, normalDrawable);
        return selector;
    }

    /**
     * 获取状态选择器
     */
    public StateListDrawable createSelector(int normalColor, int pressColor, int radius) {
        GradientDrawable bgNormal = createGradientDrawable(normalColor, radius);
        GradientDrawable bgPress = createGradientDrawable(pressColor, radius);
        return createSelector(bgNormal, bgPress);
    }


}
