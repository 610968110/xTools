package lbx.xtoollib.phone;

import android.content.Context;
import android.os.Vibrator;

/**
 * @author lbx
 * @date 2018/3/15.
 */

public class VibratorUtil {

    private static VibratorUtil INSTANCE;

    public static VibratorUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (VibratorUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VibratorUtil();
                }
            }
        }
        return INSTANCE;
    }

    private VibratorUtil() {
    }

    public void vibrator(Context context, long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public void vibrator(Context context, long[] time, int repeatTimes) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time, repeatTimes);
    }
}
