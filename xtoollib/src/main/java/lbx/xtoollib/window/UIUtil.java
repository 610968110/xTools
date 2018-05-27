package lbx.xtoollib.window;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import lbx.xtoollib.R;
import lbx.xtoollib.XTools;


public class UIUtil {

    private static UIUtil INSTANCE;

    private static Application mApp;
    private static Handler mHandler;
    private static Toast emptyToast;
    private static Toast toast;
    private static Dialog dialog = null;
    private long[] mHits = new long[2];

    public static UIUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (UIUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UIUtil();
                }
            }
        }
        return INSTANCE;
    }

    private UIUtil() {
    }

    public void init(Application app) {
        mApp = app;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public  boolean doubleClick(int time) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        return mHits[0] >= (SystemClock.uptimeMillis() - time);
    }

    public interface OnClickListener {
        void accord();

        void disaccord();
    }

    public Toast showToast(String text) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public Toast showToast(@StringRes int textId) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(getContext(), XTools.ResUtil().getString(textId), Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public void toastInUI(final String text) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                showToast(text);
            }
        };
        if (isRunOnUIThread()) {
            r.run();
        } else {
            runOnUIThread(r);
        }
    }

    public boolean textIsEmpty(String s, String toast) {
        if (TextUtils.isEmpty(s)) {
            if (emptyToast == null && !TextUtils.isEmpty(toast)) {
                emptyToast = showToast(toast);
                emptyToast = null;
            }
            return true;
        }
        return false;
    }

    public Context getContext() {
        return mApp.getApplicationContext();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public View inflate(@LayoutRes int id) {
        return View.inflate(getContext(), id, null);
    }

    public View inflate(@LayoutRes int id, ViewGroup parent) {
        LayoutInflater lf = LayoutInflater.from(getContext());
        return lf.inflate(id, parent, false);
    }

    /**
     * 运行在主线程
     */
    public void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程, 直接运行
            r.run();
        } else {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }

    private boolean isRunOnUIThread() {
        return XTools.AppUtil().isRunOnUIThread();
    }

    /**
     * 显示等待提示框
     *
     * @param context context
     */
    public void showProgressDialog(final Context context) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isDestroyed()) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.Theme_MyDialog);
        dialog.setContentView(R.layout.dialog_progress_layout);
        if (dialog != null && !dialog.isShowing()) {
            runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                closeProgressDialog();
                return true;
            }
        });
    }

    /**
     * 关闭等待提示框
     */
    public void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }
}
