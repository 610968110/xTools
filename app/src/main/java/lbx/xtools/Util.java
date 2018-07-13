package lbx.xtools;

import android.os.Environment;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.io.File;

import io.reactivex.disposables.Disposable;
import lbx.xtoollib.XTools;
import lbx.xtoollib.listener.DownloadCallBack;
import lbx.xtoollib.listener.FingerPrintCallback;
import lbx.xtoollib.listener.OnHttpObservableCallBack;
import lbx.xtoollib.phone.xLogUtil;

/**
 * @author lbx
 * @date 2018/7/2.
 */

public class Util {

    public static void fingerCheck() {
        XTools.FingerPrintUtil().check(new FingerPrintCallback() {
            @Override
            public void onSuccess(FingerprintManagerCompat.AuthenticationResult result) {
                xLogUtil.e("onSuccess:" + result.getCryptoObject());
            }

            @Override
            public void onFailed() {
                xLogUtil.e("onFailed");
            }

            @Override
            public void onErr(int errMsgId, CharSequence errString) {
                xLogUtil.e("onErr:" + errMsgId + "   " + errString.toString());
            }

            @Override
            public void onHelp(int helpMsgId, CharSequence helpString) {
                xLogUtil.e("onHelp:" + helpMsgId + "   " + helpString.toString());
            }
        });
    }

    public static void post() {
        XTools.HttpUtil().send(XTools.HttpUtil().getRetrofit("http://20.124.143.141:8866/", SnsScores.class, "登录拉")
                        .loginMain("110101199910101234", "101234", "", "0", 0),
                new OnHttpObservableCallBack<Result<LoginMainBean>>() {
                    @Override
                    public void onSuccess(Result<LoginMainBean> bean) {
                        xLogUtil.e("onSuccess");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        xLogUtil.e("onFailure:" + t);
                    }
                });
    }

    public static void download() {
        String url = "http://dl001.liqucn.com/upload/2018/291/s/com.ss.android.article.news_6.8.0_liqucn.com.apk";
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        XTools.HttpUtil().singleDownload(url, path, new DownloadCallBack() {
            @Override
            public void onStart(Disposable d, String url, File file) {
                xLogUtil.e("onStart:" + d);
            }

            @Override
            public void onProgress(float progress, float current, float total) {
                xLogUtil.e("onProgress:" + progress);
            }

            @Override
            public void onExistence(String url, File file) {
                xLogUtil.e("onExistence:" + file.getAbsolutePath());
            }

            @Override
            public void onSuccess(String url, File file) {
                xLogUtil.e("onSuccess:" + file.getAbsolutePath());
            }

            @Override
            public void onError(String err, String url, File file) {
                xLogUtil.e("onError:" + err);
            }

            @Override
            public void onFinish(boolean success, String url, File file) {
                xLogUtil.e("onFinish:" + Thread.currentThread().getName());
            }
        });
    }
}
