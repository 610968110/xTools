package lbx.xtoollib.res;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/4/21.
 */
public class SpUtil {

    private SharedPreferences mSp;
    private static SpUtil INSTANCE;
    private static Map<String, SpUtil> mMap = new HashMap<>();

    public static SpUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (SpUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpUtil(XTools.getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE));
                }
            }
        }
        return INSTANCE;
    }

    public synchronized SpUtil newInstance(String name) {
        SpUtil spUtil = mMap.get(name);
        if (spUtil == null) {
            spUtil = new SpUtil(XTools.getApplicationContext().getSharedPreferences(name, Context.MODE_PRIVATE));
            mMap.put(name, spUtil);
        }
        return spUtil;
    }

    private SpUtil() {
        throw new RuntimeException("must be init by method : SpUtil(SharedPreferences) ");
    }

    private SpUtil(SharedPreferences sp) {
        this.mSp = sp;
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
