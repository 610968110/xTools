package lbx.xtoollib.net;


import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lbx.xtoollib.XTools;
import lbx.xtoollib.listener.DownloadCallBack;
import lbx.xtoollib.listener.OnHttpFlowableCallBack;
import lbx.xtoollib.listener.OnHttpObservableCallBack;
import lbx.xtoollib.listener.OnUploadCallBack;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
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

    public <T> T getRetrofit(String baseUrl, Class<T> clazz, String[]... headers) {
        return getRetrofit(baseUrl, clazz, null, headers);
    }

    public <T> T getRetrofit(String baseUrl, Class<T> clazz, String tag, String[]... headers) {
        return makeRetrofitBuilder(baseUrl, true, tag, headers)
                .build()
                .create(clazz);
    }

    public Retrofit.Builder makeRetrofitBuilder(String baseUrl, boolean json, String tag, final String[]... headers) {
        Retrofit.Builder client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(makeClient(headers, tag));
        if (json) {
            client.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
        }
        return client;
    }

    @NonNull
    private OkHttpClient makeClient(final String[][] headers, String tag) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (tag != null) {
            builder.addNetworkInterceptor(new XHttpLoggingInterceptor(tag).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                if (headers != null) {
                    for (String[] header : headers) {
                        builder.addHeader(header[0], header[1]);
                    }
                }
                return chain.proceed(builder.build());
            }
        }).build();
    }

    private SSLSocketFactory getSSLSocketFactory(int[] certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            if (certificates != null) {
                for (int i = 0; i < certificates.length; i++) {
                    InputStream certificate = XTools.getApplication().getResources().openRawResource(certificates[i]);
                    keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
                    if (certificate != null) {
                        certificate.close();
                    }
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> void send(Flowable<T> flowable, OnHttpFlowableCallBack<T> listener) {
        if (listener == null) {
            listener = OnHttpFlowableCallBack.DEFAULT_CALLBACK;
        }
        final OnHttpFlowableCallBack<T> finalListener = listener;
        flowable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Subscriber<T>() {
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
    }

    public <T> void send(Observable<T> observable, OnHttpObservableCallBack<T> listener) {
        if (listener == null) {
            listener = OnHttpObservableCallBack.DEFAULT_CALLBACK;
        }
        final OnHttpObservableCallBack<T> finalListener = listener;
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Observer<T>() {
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
    }

    public void upLoad(String url, File file, String key, String desc, OnUploadCallBack<ResponseBody> callBack) {
        UploadTask.upLoad(url, file, key, desc, callBack);
    }

    public void singleDownload(String url, String filePath, DownloadCallBack callback) {
        singleDownload(url, filePath, url.substring(url.lastIndexOf("/") + 1, url.length()), callback);
    }

    public void singleDownload(String url, String filePath, String fileName, DownloadCallBack callback) {
        DownLoadTask.singleDownload(url, filePath, fileName, callback);
    }

}
