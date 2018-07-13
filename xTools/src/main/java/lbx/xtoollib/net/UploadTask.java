package lbx.xtoollib.net;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lbx.xtoollib.XTools;
import lbx.xtoollib.listener.OnUploadCallBack;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author lbx
 * @date 2018/7/6.
 */

class UploadTask {

    static void upLoad(String url, File file, String key, String desc, OnUploadCallBack<ResponseBody> callBack) {
        if (callBack == null) {
            callBack = OnUploadCallBack.DEFAULT_CALLBACK;
        }
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part 和后端约定好Key
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        // 添加描述
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), desc);
        final OnUploadCallBack<ResponseBody> finalCallBack = callBack;
        XTools.HttpUtil().getRetrofit(url, IFileUpload.class)
                .upload(description, body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach()
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        finalCallBack.onSubscribe(d);
                    }

                    @Override
                    public void onNext(ResponseBody t) {
                        finalCallBack.onSuccess(t);
                        finalCallBack.onFinish(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        finalCallBack.onFailure(e);
                        finalCallBack.onFinish(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
