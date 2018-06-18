package lbx.xtoollib.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.telephony.TelephonyManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/8/15.
 */
public class NetConnectUtil {


    private static ConnectivityManager mManager;

    public static final int TYPE_NONE = 0;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_WIFI = 4;
    public static final int TYPE_OTHER = 8;

    public static final int UNKNOWN = -1;
    public static final int WIFI = 1;
    public static final int NET_2G = 2;
    public static final int NET_3G = 3;
    public static final int NET_4G = 4;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TYPE_NONE, TYPE_MOBILE, TYPE_WIFI, TYPE_OTHER})
    public @interface NetConnectState {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNKNOWN, WIFI, NET_2G, NET_3G, NET_4G})
    public @interface NetState {
    }

    private static NetConnectUtil INSTANCE;

    public static NetConnectUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (NetConnectUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NetConnectUtil();
                }
            }
        }
        return INSTANCE;
    }

    private NetConnectUtil() {
    }

    private ConnectivityManager getConnectivityManager() {
        return mManager == null ?
                mManager = (ConnectivityManager) XTools.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) :
                mManager;
    }

    private int getConnectedTypeINT() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null ? networkInfo.getType() : -1;
    }


    private TelephonyManager getTelephonyManager() {
        return (TelephonyManager) XTools.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    private
    @NetConnectState
    int getNetConnectState() {
        @NetConnectState int i;
        mManager = getConnectivityManager();
        NetworkInfo info = getConnectivityManager().getActiveNetworkInfo();
        if (info != null) {
            switch (info.getType()) {
                case 0:
                    return TYPE_MOBILE;
                case 1:
                    return TYPE_WIFI;
                default:
                    return TYPE_OTHER;
            }
        } else {
            return TYPE_NONE;
        }
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public
    @NetState
    int getNetworkType() {
        int type = getConnectedTypeINT();
        switch (type) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                int var2 = getTelephonyManager().getNetworkType();
                switch (var2) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return NET_2G;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return NET_3G;
                    case 13:
                        return NET_4G;
                    default:
                        return UNKNOWN;
                }
            case 1:
                return WIFI;
            default:
                return UNKNOWN;
        }
    }
}