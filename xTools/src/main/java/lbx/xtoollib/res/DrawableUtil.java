package lbx.xtoollib.res;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
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

    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
