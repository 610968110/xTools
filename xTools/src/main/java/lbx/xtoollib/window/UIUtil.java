package lbx.xtoollib.window;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import lbx.xtoollib.R;
import lbx.xtoollib.XTools;
import lbx.xtoollib.view.UnCancelDialog;


public class UIUtil {

    private static UIUtil INSTANCE;

    private static Context mApp;
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

    public void init(Context app) {
        mApp = app;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public boolean doubleClick(int time) {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        return mHits[0] >= (SystemClock.uptimeMillis() - time);
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

    public void toastInUI(final @StringRes int textId) {
        toastInUI(XTools.ResUtil().getString(textId));
    }

    public void toastInUI(final String text) {
        Runnable r = () -> showToast(text);
        if (isRunOnUIThread()) {
            r.run();
        } else {
            runOnUIThread(r);
        }
    }

    public boolean textIsEmpty(String s, @StringRes int id) {
        return textIsEmpty(s, XTools.ResUtil().getString(id));
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

    public AlertDialog.Builder getSystemDialog(Activity activity,
                                               String title,
                                               String text,
                                               DialogInterface.OnClickListener sure,
                                               DialogInterface.OnClickListener cancel) {
        return getSystemDialog(activity, -1, title, text, sure, cancel);
    }

    public AlertDialog.Builder getSystemDialog(Activity activity, int style,
                                               String title,
                                               String text,
                                               DialogInterface.OnClickListener sure,
                                               DialogInterface.OnClickListener cancel) {
        AlertDialog.Builder builder;
        if (style != -1) {
            builder = new AlertDialog.Builder(activity, style);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        if (sure != null) {
            builder.setPositiveButton("确定", sure);
        }
        if (cancel != null) {
            builder.setNegativeButton("取消", cancel);
        }
        return builder.setCancelable(false)
                .setTitle(title)
                .setMessage(text);

    }

    public Context getContext() {
        return mApp.getApplicationContext();
    }

    public Handler getHandler() {
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
    public void showProgressDialog(final Context context, final boolean isAlert) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isDestroyed()) {
            return;
        }
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = isAlert ? new UnCancelDialog(context, R.style.Theme_MyDialog) : new Dialog(context, R.style.Theme_MyDialog);
        dialog.setContentView(R.layout.dialog_progress_layout);
        if (dialog != null && !dialog.isShowing()) {
            runOnUIThread(() -> dialog.show());
        }
        dialog.setCanceledOnTouchOutside(false);
        if (!isAlert) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                closeProgressDialog();
                return true;
            });
        }
    }

    public void showProgressDialog(final Context context) {
        showProgressDialog(context, false);
    }

    /**
     * 关闭等待提示框
     */
    public void closeProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            runOnUIThread(() -> dialog.dismiss());
        }
    }

    public String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public Point makeCanversTextCenterPoint(String text, Paint paint, Rect viewBounds) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return new Point((int) (viewBounds.centerX() - paint.measureText(text, 0, text.length()) / 2),
                (int) (viewBounds.centerY() - fontMetrics.top / 2 - fontMetrics.bottom / 2));
    }
}
