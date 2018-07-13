package lbx.xtoollib.phone;

import android.app.KeyguardManager;
import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat.AuthenticationCallback;
import android.support.v4.os.CancellationSignal;

import lbx.xtoollib.XTools;
import lbx.xtoollib.listener.FingerPrintCallback;

/**
 * @author lbx
 * @date 2018/7/2.
 */

public class FingerPrintUtil {

    private FingerprintManagerCompat mFingerprintManager;
    private KeyguardManager mKeyManager;
    private CancellationSignal mCancellationSignal;
    private static FingerPrintUtil INSTANCE;

    public static FingerPrintUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (FingerPrintUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FingerPrintUtil(XTools.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public FingerPrintUtil(Context context) {
        init(context);
    }

    private void init(Context context) {
        mFingerprintManager = FingerprintManagerCompat.from(context);
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    }

    public CancellationSignal check(FingerPrintCallback callback) {
        return check(null, callback);
    }

    public CancellationSignal check(FingerprintManagerCompat.CryptoObject object, FingerPrintCallback callback) {
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        if (callback == null) {
            callback = FingerPrintCallback.DEFAULT;
        }
        final FingerPrintCallback finalCallback = callback;
        mFingerprintManager.authenticate(object, 0, mCancellationSignal, new AuthenticationCallback() {
            //多次尝试都失败会走onAuthenticationError。会停止响应一段时间。提示尝试次数过多。请稍后再试。
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                finalCallback.onErr(errMsgId, errString);
                finalCallback.onFinish(false);
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                finalCallback.onHelp(helpMsgId, helpString);
            }

            //当验证的指纹成功时会回调此函数。然后不再监听指纹sensor
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                finalCallback.onSuccess(result);
                finalCallback.onFinish(true);
            }

            //指纹验证失败走此方法，比如小米前4次验证失败走onAuthenticationFailed,第5次走onAuthenticationError
            @Override
            public void onAuthenticationFailed() {
                finalCallback.onFailed();
                finalCallback.onFinish(false);
            }
        }, null);
        return mCancellationSignal;
    }

    /**
     * 是否录入指纹，有些设备上即使录入了指纹，可是没有开启锁屏password的话此方法还是返回false
     *
     * @return result
     */
    public boolean isHasEnrolledFingerprints() {
        try {
            return mFingerprintManager.hasEnrolledFingerprints();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否有指纹识别硬件支持
     *
     * @return result
     */
    public boolean isHardwareDetected() {
        try {
            return mFingerprintManager.isHardwareDetected();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 推断是否开启锁屏password
     *
     * @return result
     */
    public boolean isKeyguardSecure() {
        try {
            return mKeyManager.isKeyguardSecure();
        } catch (Exception e) {
            return false;
        }
    }

    public void cancel(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
        }
    }

    public void cancel() {
        cancel(mCancellationSignal);
        mCancellationSignal = null;
    }

}
