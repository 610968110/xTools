package lbx.xtoollib.phone;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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

    private static SecurityUtil mSecurityUtil;
    private static String DEFAULT_FILE_PATH = "xTools";
    private static ExecutorService service = Executors.newFixedThreadPool(1);

    /**
     * log是否打印到文件
     */
    private static boolean isPrintFile;

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

    public static void setDefaultFilePath(String defaultFilePath) {
        DEFAULT_FILE_PATH = defaultFilePath;
    }

    public static void setIsPrintFile(boolean isPrintFile) {
        xLogUtil.isPrintFile = isPrintFile;
    }

    public static void setSecurity(SecurityUtil security) {
        mSecurityUtil = security;
    }


    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void v(final String msg) {
        v(null, msg);
    }

    public static void v(final Object o, final String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            service.execute(() -> {
                StringBuilder sb = getStack(o, stack);
                String[] cut = cut(msg);
                for (String s : cut) {
                    Log.v(mTag, sb.toString() + s);
                }
            });
        }
        if (isPrintFile) {
            service.execute(() -> writeInFilePath("** v **  " + msg, mTag, DEFAULT_FILE_PATH, mSecurityUtil));
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(final String msg) {
        d(null, msg);
    }

    public static void d(final Object o, final String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            service.execute(() -> {
                StringBuilder sb = getStack(o, stack);
                String[] cut = cut(msg);
                for (String s : cut) {
                    Log.d(mTag, sb.toString() + s);
                }
            });
        }
        if (isPrintFile) {
            service.execute(() -> writeInFilePath("** d **  " + msg, mTag, DEFAULT_FILE_PATH, mSecurityUtil));
        }
    }


    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(final String msg) {
        i(null, msg);
    }

    public static void i(final Object o, final String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            service.execute(() -> {
                StringBuilder sb = getStack(o, stack);
                String[] cut = cut(msg);
                for (String s : cut) {
                    Log.i(mTag, sb.toString() + s);
                }
            });
        }
        if (isPrintFile) {
            service.execute(() -> writeInFilePath("** i **  " + msg, mTag, DEFAULT_FILE_PATH, mSecurityUtil));
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(final String msg) {
        w(null, msg);
    }

    public static void w(final Object o, final String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            service.execute(() -> {
                StringBuilder sb = getStack(o, stack);
                String[] cut = cut(msg);
                for (String s : cut) {
                    Log.w(mTag, sb.toString() + s);
                }
            });
        }
        if (isPrintFile) {
            service.execute(() -> writeInFilePath("** w **  " + msg, mTag, DEFAULT_FILE_PATH, mSecurityUtil));
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(final String msg) {
        e(null, msg);
    }

    public static void e(final Object o, final String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            service.execute(() -> {
                StringBuilder sb = getStack(o, stack);
                String[] cut = cut(msg);
                for (String s : cut) {
                    Log.e(mTag, sb.toString() + s);
                }
            });
        }
        if (isPrintFile) {
            service.execute(() -> writeInFilePath("** e **  " + msg, mTag, DEFAULT_FILE_PATH, mSecurityUtil));
        }
    }

    @NonNull
    private static StringBuilder getStack(Object o, StackTraceElement[] stack) {
        StringBuilder sb = new StringBuilder();
        if (o != null) {
            String clazz = o.getClass().getName();
            for (StackTraceElement s : stack) {
                String str = s.toString();
                if (str.contains(clazz + ":")) {
                    sb.append(str);
                    sb.append(" --> ");
                    sb.append("\n");
                    break;
                }
            }
        }
        return sb;
    }
    /**
     * 将log写入 自定义路径下的文件 文件名自动生成
     *
     * @param log  需要存储的log
     * @param tag  标签
     * @param path 路径
     */
    public static void writeInFilePath(String log, String tag, String path) {
        writeInFilePath(log, tag, path, false);
    }

    /**
     * 将log写入 自定义路径下的文件 文件名自动生成
     *
     * @param log  需要存储的log
     * @param tag  标签
     * @param path 路径
     * @param des  是否des加密
     */
    private static void writeInFilePath(String log, String tag, String path, boolean des) {
        writeInFilePath(log, tag, path, des ? mSecurityUtil : null);
    }


    /**
     * 将log写入 自定义路径下的文件 文件名自动生成
     *
     * @param log  需要存储的log
     * @param tag  标签
     * @param path 路径
     */
    public static synchronized void writeInFilePath(final String log, final String tag, final String path, final SecurityUtil securityUtil) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH", Locale.CHINA);
        String name = format.format(new Date());
        File pathF = new File(path);
        if (!pathF.exists() || !pathF.isDirectory()) {
            pathF.mkdirs();
        }
        File file = new File(path, name);
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String logTime = format.format(new Date());
        String logs = logTime + File.separator + tag + File.separator + log;
        if (securityUtil != null) {
            try {
                //des加密
                logs = new String(securityUtil.encrypt(logs.getBytes()), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        FileWriter s = null;
        try {
            s = new FileWriter(file, true);
            s.write("\n" + logs + "#lbx.xTools#");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
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
