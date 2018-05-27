package lbx.xtoollib.phone;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * Created by Administrator on 2017/8/3.
 * <meta-data
 * android:name="GZB_APP_KEY"
 * android:value="ua199" />
 */

public class MeatDataUtil {

    private static MeatDataUtil INSTANCE;

    public static MeatDataUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (MeatDataUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MeatDataUtil();
                }
            }
        }
        return INSTANCE;
    }

    private MeatDataUtil() {
    }

    public String getAppMeatData(Context context, String key) {
        String msg = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String getActivityMeatData(Activity activity, String key) {
        String msg = "";
        try {
            ActivityInfo info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(),
                            PackageManager.GET_META_DATA);
            msg = info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public <T extends Service> String getServiceMeatData(Context c, Class<T> serviceClass, String key) {
        String msg = "";
        try {
            ComponentName cn = new ComponentName(c, serviceClass);
            ServiceInfo info = c.getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            msg = info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public <T extends BroadcastReceiver> String getReceiverMeatData(Context c, Class<T> receiverClass, String key) {
        String msg = "";
        try {
            ComponentName cn = new ComponentName(c, receiverClass);
            ActivityInfo info = c.getPackageManager()
                    .getReceiverInfo(cn, PackageManager.GET_META_DATA);
            msg = info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public boolean updateAppMeatData(Context context, String key, String value) {
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            appInfo.metaData.putString(key, value);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateActivityMeatData(Activity activity, String key, String value) {
        try {
            ActivityInfo info = activity.getPackageManager()
                    .getActivityInfo(activity.getComponentName(),
                            PackageManager.GET_META_DATA);
            info.metaData.putString(key, value);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T extends Service> boolean updateServiceMeatData(Context c, Class<T> serviceClass, String key, String value) {
        try {
            ComponentName cn = new ComponentName(c, serviceClass);
            ServiceInfo info = c.getPackageManager()
                    .getServiceInfo(cn, PackageManager.GET_META_DATA);
            info.metaData.putString(key, value);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T extends BroadcastReceiver> boolean updateReceiverMeatData(Context c, Class<T> receiverClass, String key, String value) {
        try {
            ComponentName cn = new ComponentName(c, receiverClass);
            ActivityInfo info = c.getPackageManager()
                    .getReceiverInfo(cn, PackageManager.GET_META_DATA);
            info.metaData.putString(key, value);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
