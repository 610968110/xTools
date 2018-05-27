package lbx.xtoollib.res;

import android.content.Context;
import android.content.SharedPreferences;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/4/21.
 */
public class SpUtil {

    private static final String SP_CONFIG_NAME = "config";
    private static final int MODE = Context.MODE_PRIVATE;

    private static SharedPreferences mSp;
    private static SpUtil INSTANCE;

    public static SpUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (SpUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpUtil(XTools.getApplicationContext().getSharedPreferences(SP_CONFIG_NAME, MODE));
                }
            }
        }
        return INSTANCE;
    }

    private SpUtil() {
        throw new RuntimeException("must be init by method : SpUtil(SharedPreferences) ");
    }

    private SpUtil(SharedPreferences sp) {
        SpUtil.mSp = sp;
    }

    public boolean putBoolean(String key, boolean b) {
        return mSp.edit().putBoolean(key, b).commit();
    }

    public boolean putString(String key, String s) {
        return mSp.edit().putString(key, s).commit();
    }

    public boolean putInt(String key, int i) {
        return mSp.edit().putInt(key, i).commit();
    }

    public boolean putLong(String key, long i) {
        return mSp.edit().putLong(key, i).commit();
    }

    public boolean getBoolean(String key, boolean b) {
        return mSp.getBoolean(key, b);
    }

    public String getString(String key, String s) {
        return mSp.getString(key, s);
    }

    public int getInt(String key, int i) {
        return mSp.getInt(key, i);
    }

    public long getLong(String key, long i) {
        return mSp.getLong(key, i);
    }


    public boolean deleteString(String key) {
        return mSp.edit().remove(key).commit();
    }

    public boolean deleteInt(String key) {
        return mSp.edit().remove(key).commit();
    }
}
