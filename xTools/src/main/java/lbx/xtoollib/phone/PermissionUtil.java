package lbx.xtoollib.phone;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author lbx
 * @date 2017/9/11.
 */

public class PermissionUtil {

    private static PermissionUtil INSTANCE;
    private static String[] mDefaultPermissions14 = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WAKE_LOCK};
    private static String[] mDefaultPermissions20 = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.BODY_SENSORS};

    public static PermissionUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (PermissionUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PermissionUtil();
                }
            }
        }
        return INSTANCE;
    }

    private PermissionUtil() {
    }

    public boolean checkPermission(Activity ac, int code, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(ac, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ac, permissions, code);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 6.0+申请权限
     */
    public boolean checkDefaultPermission(Activity ac, int code) {
        return checkPermission(ac, code, Build.VERSION.SDK_INT >= 20 ? mDefaultPermissions20 : mDefaultPermissions14);
    }
}
