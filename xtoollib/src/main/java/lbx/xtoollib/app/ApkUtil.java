package lbx.xtoollib.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @author lbx
 * @date 2018/5/27.
 */

public class ApkUtil {

    private static ApkUtil INSTANCE;

    public static ApkUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ApkUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApkUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ApkUtil() {
    }

    /**
     * 判断当前应用是否是debug状态
     */

    public boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
