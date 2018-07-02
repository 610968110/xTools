package lbx.xtools;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import lbx.xtoollib.XTools;
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
}
