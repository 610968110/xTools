package lbx.xtoollib.window;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lbx.xtoollib.XTools;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author lbx
 * @date 2016/7/8
 * Activity管理类  收到注销时 结束所有Activity
 */
public class ActivityUtil {

    private static ActivityUtil INSTANCE;

    public static ActivityUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ActivityUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ActivityUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ActivityUtil() {
    }

    private static ArrayList<Activity> activities = new ArrayList<>();

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void finishAll() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else {
            return null;
        }
    }

    public Activity getTopActivity() {
        ArrayList<Activity> activities = XTools.ActivityUtil().getActivities();
        if (activities != null && activities.size() > 0) {
            return activities.get(activities.size() - 1);
        }
        return null;
    }

    @SafeVarargs
    public final <T extends AppCompatActivity> Intent startActivityWithTransition(
            Activity activity, Class<T> clazz, Pair<View, String>... pairs) {
        Intent intent = new Intent(activity, clazz);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && pairs != null && pairs.length > 0) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
        return intent;
    }
}
