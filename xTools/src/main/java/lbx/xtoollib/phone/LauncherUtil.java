package lbx.xtoollib.phone;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2018/8/14.
 */

public class LauncherUtil {


    private static LauncherUtil INSTANCE;

    public static LauncherUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (LauncherUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LauncherUtil();
                }
            }
        }
        return INSTANCE;
    }

    private LauncherUtil() {
    }

    public void setHuaWeiDefaultLauncher(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.HOME");
        //这里就是为了处置华为手机
        try {
            //华为手机,开启桌面设置
            startHuaweiSettingActOfDefLauncher(activity);
        } catch (Exception e1) {
            try {
                //华为手机
                intent.setComponent(new ComponentName("com.huawei.android.internal.app",
                        //这个类有些华为手机找不到
                        "com.huawei.android.internal.app.HwResolverActivity"));
                activity.startActivity(intent);
            } catch (Exception e2) {
                try {
                    intent.setComponent(new ComponentName("com.android.settings",
                            //这个类有些华为手机找不到
                            "com.android.settings.Settings$HomeSettingsActivity"));
                    activity.startActivity(intent);
                } catch (Exception e3) {
                    try {
                        activity.startActivity(new Intent(Settings.ACTION_HOME_SETTINGS));
                    } catch (Exception e4) {
                        try {
                            intent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
                            activity.startActivity(intent);
                        } catch (Exception e5) {

                        }
                    }
                }
            }
        }
    }

    /**
     * 设置为华为手机
     */
    private void startHuaweiSettingActOfDefLauncher(Activity activity) {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(Intent.ACTION_MAIN);
        localIntentFilter.addCategory(Intent.CATEGORY_HOME);
        Intent localIntent3 = new Intent(localIntentFilter.getAction(0));
        localIntent3.addCategory(localIntentFilter.getCategory(0));
        Intent localIntent4 = new Intent();
        localIntent4.setClassName("com.android.settings", "com.android.settings.Settings$PreferredSettingsActivity");
        localIntent4.putExtra("preferred_app_package_name", activity.getPackageName());
        localIntent4.putExtra("preferred_app_class_name", activity.getClass().getName());
        localIntent4.putExtra("is_user_confirmed", true);
        localIntent4.putExtra("preferred_app_intent", localIntent3);
        localIntent4.putExtra("preferred_app_intent_filter", localIntentFilter);
        localIntent4.putExtra("preferred_app_label", "默认桌面设置");
        activity.startActivity(localIntent4);
    }

    /**
     * 判断自己的应用程序是否为默认桌面
     */
    public boolean isDefaultHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        PackageManager pm = context.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return context.getPackageName().equals(info.activityInfo.packageName);
    }
}
