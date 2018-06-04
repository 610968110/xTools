package lbx.xtoollib.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Iterator;
import java.util.List;

import lbx.xtoollib.XTools;


/**
 * Application管理类
 */

public class AppUtil {

    private static AppUtil INSTANCE;

    private static int mMainThreadId;

    public static AppUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (AppUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppUtil();
                }
            }
        }
        return INSTANCE;
    }

    private AppUtil() {
    }

    public String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = XTools.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(XTools.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public String getPackageName(Context context) {
        return context.getPackageName();
    }

    public String getVersionName(Context context) {
        String s1 = "";
        String s2 = context.getPackageName();
        try {
            s1 = context.getPackageManager().getPackageInfo(s2, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return s1;
    }

    public int getVersionCode(Context context) {
        int i = -1;
        String s = context.getPackageName();
        try {
            i = context.getPackageManager().getPackageInfo(s, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }

    public boolean isRunningForeground(Context c) {
        ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List list = am.getRunningAppProcesses();
        if (list != null && list.size() > 0) {
            Iterator ita = list.iterator();
            ActivityManager.RunningAppProcessInfo info;
            do {
                if (!ita.hasNext()) {
                    return false;
                }
                info = (ActivityManager.RunningAppProcessInfo) ita.next();
            } while (info.pid != getCurrentProcessId() || info.importance != 100);
            return true;
        } else {
            return false;
        }
    }

    public int getMainThreadId() {
        return mMainThreadId;
    }

    public String getProcessName(Context context) {
        return getProcessName(context, android.os.Process.myPid());
    }

    public String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = am.getRunningAppProcesses();
        if (list == null) {
            return "";
        } else {
            Iterator ita = list.iterator();
            ActivityManager.RunningAppProcessInfo info;
            do {
                if (!ita.hasNext()) {
                    return "";
                }
                info = (ActivityManager.RunningAppProcessInfo) ita.next();
            } while (info.pid != pid);
            return info.processName;
        }
    }

    public int getCurrentProcessId() {
        return android.os.Process.myPid();
    }


    /**
     * 判断是否运行在主线程
     */
    public boolean isRunOnUIThread() {
        // 获取当前线程id, 如果当前线程id和主线程id相同, 那么当前就是主线程
        int myTid = android.os.Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    /**
     * 运行在子线程
     */
    public void runOnOtherThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程
            new Thread(r).start();
        } else {
            // 如果是子线程
            r.run();
        }
    }

    /**
     * 获取app的apk路径
     *
     * @param context context
     * @return path
     */
    public String getAppPath(Context context) {
        context = context.getApplicationContext();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return applicationInfo.sourceDir;
    }

    public boolean openApp(Context context, String pkn) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(pkn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (intent.resolveActivity(packageManager) != null) {
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    public Intent getApp(Context context, String pkn) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(pkn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }
}
