package lbx.xtoollib.listener;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * @author lbx
 * @date 2018/7/2.
 */

public abstract class FingerPrintCallback {

    public abstract void onSuccess(FingerprintManagerCompat.AuthenticationResult result);

    public abstract void onFailed();

    public abstract void onErr(int errMsgId, CharSequence errString);

    public void onFinish(boolean success) {
    }

    public void onHelp(int helpMsgId, CharSequence helpString) {
    }

    public static final DefaultFingerPrintCallback DEFAULT = new DefaultFingerPrintCallback();

    private static class DefaultFingerPrintCallback extends FingerPrintCallback {
        @Override
        public void onSuccess(FingerprintManagerCompat.AuthenticationResult result) {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onErr(int errMsgId, CharSequence errString) {

        }

        @Override
        public void onHelp(int helpMsgId, CharSequence helpString) {

        }
    }

}
