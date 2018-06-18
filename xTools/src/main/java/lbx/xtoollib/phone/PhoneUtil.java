package lbx.xtoollib.phone;

import android.content.Context;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

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

    public void vibrator(Context context, long time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public void vibrator(Context context, long[] time, int repeatTimes) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time, repeatTimes);
    }

    public final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取手机IMSI
     */
    public final String getIMSI(Context context) {
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
