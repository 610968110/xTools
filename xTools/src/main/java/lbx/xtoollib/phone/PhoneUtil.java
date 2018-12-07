package lbx.xtoollib.phone;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * @author lbx
 * @date 2018/3/15.
 */

public class PhoneUtil {

    private static PhoneUtil INSTANCE;

    public static PhoneUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (PhoneUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PhoneUtil();
                }
            }
        }
        return INSTANCE;
    }

    private PhoneUtil() {
    }

    public void vibrator(Context context, VibrationEffect vibrationEffect) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(vibrationEffect);
        }
    }

    public void vibrator(Context context, long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public void vibrator(Context context, long[] time, int repeatTimes) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time, repeatTimes);
    }

    //
//    public final String getIMEI(Context context) {
//        try {
//            //实例化TelephonyManager对象
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            //获取IMEI号
//            String imei = telephonyManager.getDeviceId();
//            //在次做个验证，也不是什么时候都能获取到的啊
//            if (imei == null) {
//                imei = "";
//            }
//            return imei;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }

    public String getIMEI(Context context) {
        return getIMEI(context, 0);
    }

    /**
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return IMEI
     */
    public String getIMEI(Context context, int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            return (String) method.invoke(manager, slotId);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取手机IMSI
     */
    public String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
