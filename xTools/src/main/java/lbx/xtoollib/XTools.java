package lbx.xtoollib;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import java.io.File;

import lbx.xtoollib.app.ApkUtil;
import lbx.xtoollib.app.AppUtil;
import lbx.xtoollib.e.UncaughtExceptionHandler;
import lbx.xtoollib.net.DownloadUtil;
import lbx.xtoollib.net.HttpUtil;
import lbx.xtoollib.net.NetConnectUtil;
import lbx.xtoollib.phone.BluetoothUtil;
import lbx.xtoollib.phone.ChineseDateUtil;
import lbx.xtoollib.phone.CloseUtil;
import lbx.xtoollib.phone.FileUtil;
import lbx.xtoollib.phone.FingerPrintUtil;
import lbx.xtoollib.phone.LauncherUtil;
import lbx.xtoollib.phone.MD5Utils;
import lbx.xtoollib.phone.MathUtil;
import lbx.xtoollib.phone.MetaDataUtil;
import lbx.xtoollib.phone.ObjUtil;
import lbx.xtoollib.phone.PermissionUtil;
import lbx.xtoollib.phone.PhoneUtil;
import lbx.xtoollib.phone.PropertiesUtil;
import lbx.xtoollib.phone.SecurityUtil;
import lbx.xtoollib.phone.SoftInputUtil;
import lbx.xtoollib.phone.TimeUtil;
import lbx.xtoollib.phone.xLogUtil;
import lbx.xtoollib.res.BitmapUtil;
import lbx.xtoollib.res.DrawableUtil;
import lbx.xtoollib.res.ResUtil;
import lbx.xtoollib.res.SpUtil;
import lbx.xtoollib.res.UriUtil;
import lbx.xtoollib.window.ActivityUtil;
import lbx.xtoollib.window.StatusBarUtil;
import lbx.xtoollib.window.UIUtil;
import lbx.xtoollib.window.WindowUtil;

import static lbx.xtoollib.phone.xLogUtil.LEVEL_NONE;
import static lbx.xtoollib.phone.xLogUtil.LEVEL_VERBOSE;


/**
 * @author lbx
 * @date 2018/5/4.
 */

public class XTools {

    private static Context mAppContext;
    private static Application mApp;
    private static UIUtil UI_UTIL;
    private static ResUtil RES_UTIL;
    private static WindowUtil WINDOW_UTIL;
    private static AppUtil APP_UTIL;
    private static SpUtil SP_UTIL;
    private static ActivityUtil mActivityUtil;
    private static BitmapUtil mBitmapUtil;
    private static MathUtil mMathUtil;
    private static BluetoothUtil mBluetoothUtil;
    private static ChineseDateUtil mDateUtil;
    private static DrawableUtil mDrawableUtil;
    private static FileUtil mFileUtil;
    private static HttpUtil mHttpUtil;
    private static xLogUtil mLogUtil;
    private static ApkUtil mApkUtil;
    private static MD5Utils mMD5Utils;
    private static MetaDataUtil mMetaDataUtil;
    private static NetConnectUtil mNetConnectTypeUtil;
    private static ObjUtil mObjUtil;
    private static PermissionUtil mPermissionUtil;
    private static SoftInputUtil mSoftInputUtil;
    private static StatusBarUtil mStatusBarUtil;
    private static TimeUtil mTimeUtil;
    private static CloseUtil mCloseUtil;
    private static UriUtil mUriUtil;
    private static PhoneUtil mPhoneUtil;
    private static UncaughtExceptionHandler mUncaughtExceptionHandler;
    private static int mainThreadId;
    private static SecurityUtil mSecurity;
    private static FingerPrintUtil mFingerPrintUtil;
    private static DownloadUtil mDownloadUtil;
    private static LauncherUtil mLauncherUtil;
    private static PropertiesUtil mPropertiesUtil;
    private static boolean isInit;

    private XTools(Builder builder, Context app) {
        if (app instanceof Application) {
            mApp = (Application) app;
        }
        mAppContext = app.getApplicationContext();
        if (!builder.log) {
            LogUtil().setDebuggable(LEVEL_NONE);
        } else {
            LogUtil().setDebuggable(builder.logLevel);
        }
        LogUtil().setTag(builder.logTag);
        if (!builder.errLogFile && !builder.errLogCat) {
            return;
        }
        UncaughtExceptionHandler uncaughtExceptionHandler = getUncaughtExceptionHandler(builder);
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        uncaughtExceptionHandler.setErrFilePath(builder.errLogFilePath);
        uncaughtExceptionHandler.setErrFileName(builder.errLogFileName);
        uncaughtExceptionHandler.setErrPrintTag(builder.logTag);
        uncaughtExceptionHandler.log(builder.errLogFile, builder.errLogCat);
        uncaughtExceptionHandler.setSecurityUtil(mSecurity);
    }

    public static Application getApplication() {
        return mApp;
    }

    public static boolean isInit() {
        return isInit;
    }

    public static class Builder {

        private boolean log;
        private int logLevel = LEVEL_VERBOSE;
        private String logTag = "xys";
        private String errLogFilePath = "xTools";
        private String errLogFileName = "ERR";
        private boolean errLogFile = false;
        private boolean errLogCat = false;
        private UncaughtExceptionHandler handler;

        public Builder() {
        }

        public Builder log(boolean log) {
            this.log = log;
            return this;
        }

        /**
         * LEVEL_NONE, LEVEL_ERROR, LEVEL_WARN, LEVEL_INFO, LEVEL_DEBUG, LEVEL_VERBOSE
         * 可选类型{@link xLogUtil.LogPrintLevel}
         */
        public Builder logLevel(@xLogUtil.LogPrintLevel int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder errLogFilePath(String path) {
            errLogFilePath = path;
            return this;
        }

        public Builder logPrintFile(boolean isPrint, String path, String desKey) {
            xLogUtil.setIsPrintFile(isPrint);
            xLogUtil.setDefaultFilePath(XTools.FileUtil().getDefaultPath() + File.separator + path);
            if (!TextUtils.isEmpty(desKey)) {
                mSecurity = new SecurityUtil(desKey);
                xLogUtil.setSecurity(mSecurity);
            }
            return this;
        }

        public Builder errLogFileName(String name) {
            errLogFileName = name;
            return this;
        }

        public Builder errLogFile(boolean errLogFile, boolean errLogCat) {
            this.errLogFile = errLogFile;
            this.errLogCat = errLogCat;
            return this;
        }

        public Builder logTag(String tag) {
            this.logTag = tag;
            return this;
        }

        public UncaughtExceptionHandler getHandler() {
            return handler;
        }

        public Builder uncaughtExceptionHandler(UncaughtExceptionHandler handler) {
            this.handler = handler;
            return this;
        }

        public XTools build(Context app) {
            return new XTools(this, app);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "log=" + log +
                    ", logLevel=" + logLevel +
                    ", logTag='" + logTag + '\'' +
                    ", errLogFilePath='" + errLogFilePath + '\'' +
                    ", errLogFileName='" + errLogFileName + '\'' +
                    ", errLogFile=" + errLogFile +
                    ", errLogCat=" + errLogCat +
                    ", handler=" + handler +
                    '}';
        }
    }

    public void init() {
        isInit = true;
        UiUtil().init(mAppContext);
        mainThreadId = Process.myTid();
    }

    public static Context getApplicationContext() {
        return UI_UTIL.getContext();
    }

    public static UIUtil UiUtil() {
        return UI_UTIL == null ? UI_UTIL = UIUtil.getInstance() : UI_UTIL;
    }

    public static ResUtil ResUtil() {
        return RES_UTIL == null ? RES_UTIL = ResUtil.getInstance(getApplicationContext()) : RES_UTIL;
    }

    public static WindowUtil WindowUtil() {
        return WINDOW_UTIL == null ? WINDOW_UTIL = WindowUtil.getInstance(getApplicationContext()) : WINDOW_UTIL;
    }

    public static SpUtil SpUtil() {
        return SP_UTIL == null ? SP_UTIL = SpUtil.getInstance() : SP_UTIL;
    }

    public static AppUtil AppUtil() {
        return APP_UTIL == null ? APP_UTIL = AppUtil.getInstance(mainThreadId) : APP_UTIL;
    }

    public static ActivityUtil ActivityUtil() {
        return mActivityUtil == null ? mActivityUtil = ActivityUtil.getInstance() : mActivityUtil;
    }

    public static BitmapUtil BitmapUtil() {
        return mBitmapUtil == null ? mBitmapUtil = BitmapUtil.getInstance() : mBitmapUtil;
    }

    public static MathUtil MathUtil() {
        return mMathUtil == null ? mMathUtil = MathUtil.getInstance() : mMathUtil;
    }

    public static BluetoothUtil BluetoothUtil() {
        return mBluetoothUtil == null ? mBluetoothUtil = BluetoothUtil.getInstance() : mBluetoothUtil;
    }

    public static ChineseDateUtil DateUtil() {
        return mDateUtil == null ? mDateUtil = ChineseDateUtil.getInstance() : mDateUtil;
    }

    public static DrawableUtil DrawableUtil() {
        return mDrawableUtil == null ? mDrawableUtil = DrawableUtil.getInstance() : mDrawableUtil;
    }

    public static ApkUtil ApkUtil() {
        return mApkUtil == null ? mApkUtil = ApkUtil.getInstance() : mApkUtil;
    }

    public static FileUtil FileUtil() {
        return mFileUtil == null ? mFileUtil = FileUtil.getInstance() : mFileUtil;
    }

    public static HttpUtil HttpUtil() {
        return mHttpUtil == null ? mHttpUtil = HttpUtil.getInstance() : mHttpUtil;
    }

    public static xLogUtil LogUtil() {
        return mLogUtil == null ? mLogUtil = xLogUtil.getInstance() : mLogUtil;
    }

    public static MD5Utils MD5Utils() {
        return mMD5Utils == null ? mMD5Utils = MD5Utils.getInstance() : mMD5Utils;
    }

    public static MetaDataUtil MeatDataUtil() {
        return mMetaDataUtil == null ? mMetaDataUtil = MetaDataUtil.getInstance() : mMetaDataUtil;
    }

    public static NetConnectUtil NetConnectTypeUtil() {
        return mNetConnectTypeUtil == null ? mNetConnectTypeUtil = NetConnectUtil.getInstance() : mNetConnectTypeUtil;
    }

    public static ObjUtil ObjUtil() {
        return mObjUtil == null ? mObjUtil = ObjUtil.getInstance() : mObjUtil;
    }

    public static PermissionUtil PermissionUtil() {
        return mPermissionUtil == null ? mPermissionUtil = PermissionUtil.getInstance() : mPermissionUtil;
    }

    public static SoftInputUtil SoftInputUtil() {
        return mSoftInputUtil == null ? mSoftInputUtil = SoftInputUtil.getInstance() : mSoftInputUtil;
    }

    public static StatusBarUtil StatusBarUtil() {
        return mStatusBarUtil == null ? mStatusBarUtil = StatusBarUtil.getInstance() : mStatusBarUtil;
    }

    public static TimeUtil TimeUtil() {
        return mTimeUtil == null ? mTimeUtil = TimeUtil.getInstance() : mTimeUtil;
    }

    public static CloseUtil CloseUtil() {
        return mCloseUtil == null ? mCloseUtil = CloseUtil.getInstance() : mCloseUtil;
    }

    public static UriUtil UriUtil() {
        return mUriUtil == null ? mUriUtil = UriUtil.getInstance() : mUriUtil;
    }

    public static PhoneUtil PhoneUtil() {
        return mPhoneUtil == null ? mPhoneUtil = PhoneUtil.getInstance() : mPhoneUtil;
    }

    public static FingerPrintUtil FingerPrintUtil() {
        return mFingerPrintUtil == null ? mFingerPrintUtil = FingerPrintUtil.getInstance() : mFingerPrintUtil;
    }

    public static DownloadUtil DownloadUtil() {
        return mDownloadUtil == null ? mDownloadUtil = DownloadUtil.getInstance() : mDownloadUtil;
    }

    public static PropertiesUtil PropertiesUtil() {
        return mPropertiesUtil == null ? mPropertiesUtil = PropertiesUtil.getInstance() : mPropertiesUtil;
    }

    public static LauncherUtil LauncherUtil() {
        return mLauncherUtil == null ? mLauncherUtil = LauncherUtil.getInstance() : mLauncherUtil;
    }

    private UncaughtExceptionHandler getUncaughtExceptionHandler(Builder builder) {
        mUncaughtExceptionHandler = builder.getHandler();
        return mUncaughtExceptionHandler == null ?
                mUncaughtExceptionHandler = UncaughtExceptionHandler.DEFAULT :
                mUncaughtExceptionHandler;
    }
}
