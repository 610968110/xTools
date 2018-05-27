package lbx.xtoollib.window;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by 61096 on 2016/7/8.
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
}
