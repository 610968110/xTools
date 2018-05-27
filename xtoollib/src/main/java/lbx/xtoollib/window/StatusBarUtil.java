package lbx.xtoollib.window;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import lbx.xtoollib.XTools;


public class StatusBarUtil {

    private static StatusBarUtil INSTANCE;

    public static StatusBarUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (StatusBarUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StatusBarUtil();
                }
            }
        }
        return INSTANCE;
    }

    private StatusBarUtil() {
    }

    public void setWindowStatusBarColor(Activity activity, int colorResId, boolean withBottom) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getColor(colorResId));
                if (withBottom) {
                    //底部导航栏
                    window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getColor(int colorResId) {
        return XTools.ResUtil().getColor(colorResId);
    }

    public void setWindowStatusBarColor(Dialog dialog, int colorResId, boolean withBottom) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getColor(colorResId));
                if (withBottom) {
                    //底部导航栏
                    window.setNavigationBarColor(getColor(colorResId));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}