package lbx.xtoollib.net;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public static abstract class OnCallListener<T> {
        public abstract void onNext(T t);

        public void onError(Throwable e) {
        }

        public void onCompleted() {
        }
    }

    public static OnCallListener DEFAULT_LISTENER = new OnCallListener() {

        @Override
        public void onNext(Object o) {

        }
    };

    public  <T> T getRetrofit(String baseUrl, Class<T> clazz) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz);
    }

    public  <T> Subscription call(Observable<T> observable, OnCallListener<T> listener) {
        if (listener == null) {
            listener = DEFAULT_LISTENER;
        }
        final OnCallListener<T> finalListener = listener;
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        finalListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        finalListener.onError(e);
                        finalListener.onCompleted();
                    }

                    @Override
                    public void onNext(T t) {
                        finalListener.onNext(t);
                    }
                });
    }
}
