package lbx.xtoollib.window;

import android.content.Context;
import android.view.WindowManager;

/**
 * @author lbx
 * @date 2018/5/4.
 */

public class WindowUtil {

    private static WindowUtil INSTANCE;
    private static Context mContext;
    private static WindowManager mWindowManager;

    public static WindowUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (WindowUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WindowUtil(context);
                }
            }
        }
        return INSTANCE;
    }

    private WindowUtil() {
    }

    private WindowUtil(Context context) {
        mContext = context;
    }

    public int dip2px(float dip) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public float px2dip(int px) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return px / density;
    }

    public int px2sp(int px) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public int sp2px(int px) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    private WindowManager getWindowManager() {
        return mWindowManager == null ? mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE) : mWindowManager;
    }

    public int getScreenWidth() {
        return getWindowManager().getDefaultDisplay().getWidth();
    }

    public int getScreenHeight() {
        return getWindowManager().getDefaultDisplay().getHeight();
    }

}
