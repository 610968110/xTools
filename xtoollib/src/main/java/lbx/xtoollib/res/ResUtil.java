package lbx.xtoollib.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * @author lbx
 * @date 2018/5/4.
 */

public class ResUtil {

    private static ResUtil INSTANCE;
    private static Context mContext;

    public static ResUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ResUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ResUtil(context);
                }
            }
        }
        return INSTANCE;
    }

    private ResUtil() {
    }

    private ResUtil(Context context) {
        mContext = context;
    }

    public String getString(int id) {
        return mContext.getResources().getString(id);
    }

    public String[] getStringArray(int id) {
        return mContext.getResources().getStringArray(id);
    }

    public Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public int getColor(int id) {
        return mContext.getResources().getColor(id);
    }

    public ColorStateList getColorStateList(int id) {
        return mContext.getResources().getColorStateList(id);
    }

    /**
     * 返回具体像素值
     */
    public int getDimen(int... ids) {
        Resources resources = mContext.getResources();
        int dimen = 0;
        for (int id : ids) {
            dimen += resources.getDimensionPixelSize(id);
        }
        return dimen;
    }
}
