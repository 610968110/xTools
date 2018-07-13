package lbx.xtoollib.listener;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * @author lbx
 * @date 2018/7/6.
 */

public abstract class DownloadCallBack {


    public void onStart(Disposable d, String url, File file) {

    }

    public void onProgress(float progress, float current, float total) {

    }

    public abstract void onExistence(String url, File file);

    public abstract void onSuccess(String url, File file);

    public abstract void onError(String err, String url, File file);

    public void onFinish(boolean success, String url, File file) {

    }

    public static DefaultDownloadCallBack DEFAULT = new DefaultDownloadCallBack();

    private static class DefaultDownloadCallBack extends DownloadCallBack {
        @Override
        public void onExistence(String url, File file) {

        }

        @Override
        public void onSuccess(String url, File file) {

        }

        @Override
        public void onError(String err, String url, File file) {

        }
    }

}
