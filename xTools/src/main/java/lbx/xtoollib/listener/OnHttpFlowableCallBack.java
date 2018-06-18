package lbx.xtoollib.listener;

import org.reactivestreams.Subscription;

public abstract class OnHttpFlowableCallBack<T> {


    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);

    public void onFinish(boolean ok) {

    }

    public void onSubscribe(Subscription s) {

    }

    public static OnHttpFlowableCallBack DEFAULT_CALLBACK = new Default();

    private static class Default extends OnHttpFlowableCallBack {
        @Override
        public void onSuccess(Object o) {

        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

}