package lbx.xtoollib.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lbx.xtoollib.XTools;
import lbx.xtoollib.listener.DownloadCallBack;
import lbx.xtoollib.res.SpUtil;
import okhttp3.ResponseBody;

/**
 * @author lbx
 * @date 2018/7/6.
 */

class DownLoadTask {

    static void singleDownload(final String url, final String filePath, final String fileName, DownloadCallBack callback) {
        if (callback == null) {
            callback = DownloadCallBack.DEFAULT;
        }
        final SpUtil spUtil = XTools.SpUtil().newInstance(url.replace("/", ""));
        //断点续传时请求的总长度
        final File file = new File(filePath, fileName);
        final DownloadCallBack finalCallback = callback;
        if (!file.exists() || file.isDirectory()) {
            spUtil.putLong(url, 0);
        }
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }
        //当前进度
        final long range = spUtil.getLong(url, 0);
        if (file.exists() && !file.isDirectory() && range == file.length()) {
            XTools.UiUtil().runOnUIThread(() -> {
                finalCallback.onExistence(url, file);
                finalCallback.onFinish(false, url, file);
            });
            return;
        }
        XTools.HttpUtil().getRetrofit(url + (url.endsWith("/") ? "" : "/"), IDownLoad.class)
                .executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        XTools.UiUtil().runOnUIThread(() -> finalCallback.onStart(d, url, file));
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength;
                        try {
                            byte[] buf = new byte[2048];
                            int len;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            final File file = new File(filePath, fileName);
                            File dir = new File(filePath);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            randomAccessFile = new RandomAccessFile(file, "rwd");
                            if (range == 0) {
                                randomAccessFile.setLength(responseLength);
                            }
                            randomAccessFile.seek(range);
                            float progress;
                            long time = System.currentTimeMillis();
                            while ((len = inputStream.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                total += len;
                                progress = total * 100F / randomAccessFile.length();
                                spUtil.putLong(url, total);
                                float showProgress = Math.round(progress * 100F) / 100F;
                                if (progress == 100 || System.currentTimeMillis() - time >= 1000) {
                                    time = System.currentTimeMillis();
                                    finalCallback.onProgress(showProgress, total, file.length());
                                }
                            }
                            XTools.UiUtil().runOnUIThread(() -> {
                                finalCallback.onSuccess(url, file);
                                finalCallback.onFinish(true, url, file);
                            });
                        } catch (final Exception e) {
                            XTools.UiUtil().runOnUIThread(() -> {
                                finalCallback.onError(e.getMessage(), url, file);
                                finalCallback.onFinish(false, url, file);
                            });
                            e.printStackTrace();
                        } finally {
                            try {
                                spUtil.putLong(url, total);
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        XTools.UiUtil().runOnUIThread(() -> {
                            finalCallback.onError(e.toString(), url, file);
                            finalCallback.onFinish(false, url, file);
                        });
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
