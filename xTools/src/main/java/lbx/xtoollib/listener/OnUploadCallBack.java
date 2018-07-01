package lbx.xtoollib.listener;

import io.reactivex.disposables.Disposable;

public abstract class OnUploadCallBack<T> {


    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);

    public void onFinish(boolean ok) {

    }

    public void onSubscribe(Disposable d) {

    }

    public static OnUploadCallBack DEFAULT_CALLBACK = new Default<>();

    private static class Default<T> extends OnUploadCallBack<T> {
        @Override
        public void onSuccess(T o) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

}