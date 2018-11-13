package lbx.xtoollib.res;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

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
    public static final String SPLIT = "#XTOOLS#";

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

    public long getLong(String key, long l) {
        return mSp.getLong(key, l);
    }


    public boolean deleteString(String key) {
        return mSp.edit().remove(key).commit();
    }

    public boolean deleteInt(String key) {
        return mSp.edit().remove(key).commit();
    }

    public boolean putFloat(String key, float i) {
        return mSp.edit().putFloat(key, i).commit();
    }

    public float getFloat(String key, float i) {
        return mSp.getFloat(key, i);
    }

    public boolean deleteFloat(String key) {
        return mSp.edit().remove(key).commit();
    }

    public boolean putStringArray(String key, String... array) {
        int length = array.length;
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = 0; i < length; i++) {
            builder.append(array[i]);
            if (i != length - 1) {
                builder.append(SPLIT);
            }
        }
        return putString(key, builder.toString());
    }

    public String[] getStringArray(String key, String... array) {
        String string = getString(key, "");
        if (TextUtils.isEmpty(string)) {
            return new String[0];
        } else if (!string.contains(SPLIT)) {
            return new String[]{string};
        } else {
            return string.split(SPLIT);
        }
    }

    public boolean putIntArray(String key, int... array) {
        String[] strings = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            strings[i] = String.valueOf(array[i]);
        }
        return putStringArray(key, strings);
    }

    public int[] getIntArray(String key) {
        String string = getString(key, "");
        if (TextUtils.isEmpty(string)) {
            return new int[0];
        } else if (!string.contains(SPLIT)) {
            return new int[]{Integer.valueOf(string)};
        } else {
            String[] strings = string.split(SPLIT);
            int[] ints = new int[string.length()];
            for (int i = 0; i < strings.length; i++) {
                ints[i] = Integer.valueOf(strings[i]);
            }
            return ints;
        }
    }

}
