package lbx.xtoollib.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;

import lbx.xtoollib.XTools;
import lbx.xtoollib.phone.SecurityUtil;
import lbx.xtoollib.phone.xLogUtil;


/**
 * 全局捕获异常
 *
 * @author lbx
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static Context mContext;
    private static UncaughtExceptionHandler mHandler;
    private static String DEFAULT_FILE_NAME = "ERR";
    private static String DEFAULT_FILE_PATH = "xTools";
    private static String DEFAULT_ERR_TAG = "xys";
    private static boolean DEFAULT_SAVE = false;
    private static boolean DEFAULT_PRINT = false;
    private SecurityUtil mSecurityUtil;

    public static UncaughtExceptionHandler getInstance(Context context) {
        if (mHandler == null) {
            synchronized (UncaughtExceptionHandler.class) {
                if (mHandler == null) {
                    mHandler = new UncaughtExceptionHandler(context);
                }
            }
        }
        return mHandler;
    }

    private UncaughtExceptionHandler(Context c) {
        mContext = c;
    }

    public void setErrFileName(String defaultFileName) {
        DEFAULT_FILE_NAME = defaultFileName;
    }

    public void setErrFilePath(String defaultFilePath) {
        DEFAULT_FILE_PATH = File.separator + defaultFilePath;
    }

    public void setSecurityUtil(SecurityUtil mSecurityUtil) {
        this.mSecurityUtil = mSecurityUtil;
    }

    public void setErrPrintTag(String errTag) {
        DEFAULT_ERR_TAG = errTag;
    }

    public void log(boolean isSave, boolean isPrint) {
        DEFAULT_SAVE = isSave;
        DEFAULT_PRINT = isPrint;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        xLogUtil.e("****************************** CRASH ******************************");
        //收集崩溃日志
        String info = collectPhoneInfo(mContext);
        String result = getErr(ex);
        info += result;
        if (DEFAULT_PRINT) {
            Log.e(DEFAULT_ERR_TAG, "未捕获异常 \n photoSelect：\n" + info + "\n end \n");
        }
        if (DEFAULT_SAVE) {
            saveFile(info, DEFAULT_FILE_PATH, DEFAULT_FILE_NAME);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void saveFile(String info, String filepath, String fileName) {
        String time = XTools.TimeUtil().formatNowTime();
        String path = XTools.FileUtil().getDefaultPath() + filepath + File.separator + "err";
        FileOutputStream stream = null;
        try {
            File file = new File(path);
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
            File file1 = new File(path, "crash_" + time + fileName + ".txt");
            xLogUtil.e("未捕获异常:" + file1.getName());
            stream = new FileOutputStream(file1);
            byte[] encrypt;
            if (mSecurityUtil != null) {
                try {
                    encrypt = mSecurityUtil.encrypt(info.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                    encrypt = info.getBytes();
                }
            } else {
                encrypt = info.getBytes();
            }
            stream.write(encrypt);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                XTools.CloseUtil().close(stream);
            }
        }
    }

    private String getErr(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter err = new PrintWriter(writer);
        ex.printStackTrace(err);
        err.close();
        return writer.toString();
    }


    private String collectPhoneInfo(Context c) {
        String s = "";
        try {
            String packageInfo = packageInfo(c);
            s += packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String phoneInfo = getPhoneInfo();
            s += phoneInfo;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return s;
    }

    private String getPhoneInfo() throws IllegalAccessException {
        String s = "";
        Field[] fields = Build.class.getFields();
        if (fields != null && fields.length > 0) {
            for (Field f : fields) {
                f.setAccessible(true);
                s += f.getName() + ":" + f.get(null).toString() + "\n";
            }
        }
        return s;
    }

    private String packageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), PackageManager.GET_ACTIVITIES);
        if (packageInfo != null) {
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            String packageName = packageInfo.packageName;
            return "packageName:" + packageName + "\n"
                    + "versionName:" + versionName + "\n"
                    + "versionCode:" + versionCode + "\n";
        }
        return "";
    }
}