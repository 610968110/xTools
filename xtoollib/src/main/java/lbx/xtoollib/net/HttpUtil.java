package lbx.xtoollib.net;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lbx.xtoollib.listener.OnHttpFlowableCallBack;
import lbx.xtoollib.listener.OnHttpObservableCallBack;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lbx
 * @date 2018/3/12.
 */

public class HttpUtil {

    private static HttpUtil INSTANCE;

    public static HttpUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpUtil();
                }
            }
        }
        return INSTANCE;
    }

    private HttpUtil() {
    }

    public <T> T getRetrofit(String baseUrl, Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }

    public <T> Flowable send(Flowable<T> flowable, OnHttpFlowableCallBack<T> listener) {
        if (listener == null) {
            listener = OnHttpFlowableCallBack.DEFAULT_CALLBACK;
        }
        final OnHttpFlowableCallBack<T> finalListener = listener;
        Flowable<T> tFlowable = flowable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        tFlowable.subscribe(new Subscriber<T>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
                finalListener.onSubscribe(s);
            }

            @Override
            public void onNext(T t) {
                finalListener.onSuccess(t);
                finalListener.onFinish(true);
            }

            @Override
            public void onError(Throwable t) {
                finalListener.onFailure(t);
                finalListener.onFinish(false);
            }

            @Override
            public void onComplete() {

            }
        });
        return tFlowable;
    }

    public <T> Observable send(Observable<T> observable, OnHttpObservableCallBack<T> listener) {
        if (listener == null) {
            listener = OnHttpObservableCallBack.DEFAULT_CALLBACK;
        }
        final OnHttpObservableCallBack<T> finalListener = listener;
        Observable<T> tObservable = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        tObservable.subscribe(new Observer<T>() {
            @Override
            public void onSubscribe(Disposable d) {
                finalListener.onSubscribe(d);
            }

            @Override
            public void onNext(T t) {
                finalListener.onSuccess(t);
                finalListener.onFinish(true);
            }

            @Override
            public void onError(Throwable e) {
                finalListener.onFailure(e);
                finalListener.onFinish(false);
            }

            @Override
            public void onComplete() {

            }
        });
        return tObservable;
    }
}
