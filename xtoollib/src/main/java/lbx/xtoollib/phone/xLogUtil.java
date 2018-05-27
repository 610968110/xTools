package lbx.xtoollib.phone;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * @author lbx
 */
public class xLogUtil {
    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_NONE = 0;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 1;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 4;
    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 5;

    /**
     * 日志输出时的TAG
     */
    private static String mTag = "xys";
    /**
     * 是否允许输出log
     * LEVEL_VERBOSE  LEVEL_NONE 等
     */
    private static
    @LogPrintLevel
    int mDebuggable = LEVEL_VERBOSE;

    private static final int MAX = 3000;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LEVEL_NONE, LEVEL_ERROR, LEVEL_WARN, LEVEL_INFO, LEVEL_DEBUG, LEVEL_VERBOSE})
    public @interface LogPrintLevel {
    }


    private static xLogUtil INSTANCE;

    public static xLogUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (xLogUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new xLogUtil();
                }
            }
        }
        return INSTANCE;
    }

    private xLogUtil() {
    }

    public void setTag(String tag) {
        xLogUtil.mTag = tag;
    }

    public void setDebuggable(@LogPrintLevel int debuggable) {
        xLogUtil.mDebuggable = debuggable;
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void v(String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.v(mTag, s);
            }
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.d(mTag, s);
            }
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.i(mTag, s);
            }
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.w(mTag, s);
            }
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.e(mTag, s);
            }
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public void w(Throwable tr) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public void w(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_WARN && null != msg) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.w(mTag, s, tr);
            }
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public void e(Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public void e(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            String[] cut = cut(msg);
            for (String s : cut) {
                Log.e(mTag, s, tr);
            }
        }
    }

    public static void writeInDataPath(Context c, String fileName, String log) {
        try {
            FileOutputStream fout = c.openFileOutput(fileName, MODE_PRIVATE);
            byte[] bytes = log.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String writeInSDCardPath(String log, String fileName) {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return "没有SD卡";
        }
        File file = new File(path, fileName);
        FileOutputStream s = null;
        try {
            s = new FileOutputStream(file);
            s.write(log.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "FileNotFoundException:" + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException:" + e.toString();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return "success";
    }

    public static String writeInFilePath(String data, String tag, String path) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String name = format.format(new Date());
        File pathF = new File(path);
        if (!pathF.exists()||!pathF.isDirectory()) {
            pathF.mkdirs();
        }
        File file = new File(path, name);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String logTime = format.format(new Date());
        String log = logTime + File.separator + tag + File.separator + data;
        FileWriter s = null;
        try {
            s = new FileWriter(file, true);
            s.write("\n" + log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "FileNotFoundException:" + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException:" + e.toString();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return "success";
    }

    private static String[] cut(String s) {
        if (TextUtils.isEmpty(s) || s.length() == 0) {
            return new String[0];
        }
        int length = s.length();
        int part = length / MAX;
        String[] cut = new String[length % MAX == 0 ? part : part + 1];
        int index = 0;
        while (length > MAX) {
            cut[index++] = s.substring(0, MAX);
            s = s.substring(MAX, length);
            length = s.length();
        }
        cut[index] = s;
        return cut;
    }
}
