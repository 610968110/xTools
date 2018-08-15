package lbx.xtools;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import java.io.File;

import io.reactivex.disposables.Disposable;
import lbx.xtoollib.XIntent;
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
        XTools.HttpUtil().send(XTools.HttpUtil().getRetrofit("http://20.1.11.184:8087/", SnsScores.class, "登录拉")
                        .getConfigFromService("110101199910101237"),
                new OnHttpObservableCallBack<Result<Config>>() {
                    @Override
                    public void onSuccess(Result<Config> bean) {
                        xLogUtil.e("onSuccess");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        xLogUtil.e("onFailure:" + t);
                    }
                });
    }

    public static void postPhp() {
        XTools.HttpUtil().send(XTools.HttpUtil().getRetrofit("http://20.1.11.190/", SnsScores.class, "PHP登录拉")
                        .loginSns("110101199910101237@ydjw.com", "101237", "db69fc039dcbd2962cb4d28f5891aae1", "POST"),
                new OnHttpObservableCallBack<SnsLoginRequest>() {
                    @Override
                    public void onSuccess(SnsLoginRequest snsLoginRequest) {
                        xLogUtil.e("snsLoginRequest: success");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        xLogUtil.e("snsLoginRequest:" + t);
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

    public static void startMdmActivity(final Context context) {
        XIntent mainIntent = new XIntent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("test");
        XTools.AppUtil().scanAppByIntent(context, mainIntent, list -> xLogUtil.e("list:" + list.size()));
    }
}
