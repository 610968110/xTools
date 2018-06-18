package lbx.xtoollib.listener;

import io.reactivex.disposables.Disposable;

public abstract class OnHttpObservableCallBack<T> {


    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);

    public void onFinish(boolean ok) {

    }

    public void onSubscribe(Disposable d) {

    }

    public static OnHttpObservableCallBack DEFAULT_CALLBACK = new Default();

    private static class Default extends OnHttpObservableCallBack {
        @Override
        public void onSuccess(Object o) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

}