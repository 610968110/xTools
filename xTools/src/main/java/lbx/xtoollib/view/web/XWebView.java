package lbx.xtoollib.view.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.CallSuper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import lbx.xtoollib.XTools;
import lbx.xtoollib.view.WebPhotoActivity;

/**
 * @author lbx
 * @date 2018/5/21.
 * <input capture="camera" class="upload-input" type="file" accept="image/*" onchange="angular.element(this).scope().img_upload(this.files)"/>
 */

public class XWebView extends WebView implements XWebChromeClient.OnWebChromeClientListener, DownloadListener {

    public ValueCallback mFilePathCallback;
    private View mErrorView;
    public static final String EMPTY_URL = "about:blank";
    private String mLoadUrl = EMPTY_URL;
    private String mLoadUrlWithoutEmpty = EMPTY_URL;

    public XWebView(Context context) {
        super(context);
        init();
    }

    /**
     * com.android.internal.R.attr.webViewStyle
     */
    public XWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //启用支持Javascript
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        // 缩放按钮
        settings.setBuiltInZoomControls(false);
        // 支持缩放
        settings.setSupportZoom(false);
        // 允许访问文件
        settings.setAllowFileAccess(true);
        //适配网页 防止左右移动
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        setWebChromeClient(new XWebChromeClient());
        setWebViewClient(new XWebViewClient());
        setDownloadListener(this);
    }

    @CallSuper
    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if (client instanceof XWebChromeClient) {
            XWebChromeClient xWebChromeClient = (XWebChromeClient) client;
            xWebChromeClient.setOnWebChromeClientListener(this);
        } else {
            throw new RuntimeException("WebChromeClient client not instanceof XWebChromeClient");
        }
        super.setWebChromeClient(client);
    }

    private void start() {
        WebPhotoActivity.start(getContext());
        WebPhotoActivity.setOnPhotoSelectListener(mListener);
    }

    public void cancelFilePathCallback() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    private WebPhotoActivity.OnPhotoSelectListener mListener = new WebPhotoActivity.OnPhotoSelectListener() {

        @Override
        public void onCameraUrlResult18(Uri uri) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(new Uri[]{uri});
            }
        }

        @Override
        public void onCameraUrlResult(Uri uri) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(uri);
            }
        }

        @Override
        public void onPhotoUrlResult18(Uri uri) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(new Uri[]{uri});
            }
        }

        @Override
        public void onPhotoUrlResult(Uri uri) {
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(uri);
            }
        }

        @Override
        public void cancel() {
            cancelFilePathCallback();
        }
    };

    @CallSuper
    @Override
    public void onProgressChanged(WebView webView, int progress) {
        if (progress == 100) {
            setLoadUrl(getUrl());
        }
    }

    @Override
    public void onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        mFilePathCallback = filePathCallback;
        start();
    }

    @Override
    public void openFileChooser(ValueCallback<Uri> upLoadMsg) {
        mFilePathCallback = upLoadMsg;
        start();
    }

    @Override
    public void openFileChooser(ValueCallback<Uri> upLoadMsg, String type) {
        mFilePathCallback = upLoadMsg;
        start();
    }

    @Override
    public void openFileChooser(ValueCallback<Uri> upLoadMsg, String type, String capture) {
        mFilePathCallback = upLoadMsg;
        start();
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        downloadByBrowser(url);
    }

    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
            XTools.UiUtil().toastInUI("开始下载");
        } else {
            XTools.UiUtil().toastInUI("下载失败");
        }
    }

    public View getErrorView() {
        return mErrorView;
    }

    public void setErrorView(View errorView) {
        this.mErrorView = errorView;
    }

    public String getLoadUrl() {
        return mLoadUrl;
    }


    public void setLoadUrl(String loadUrl) {
        this.mLoadUrl = loadUrl;
        setLoadUrlWithoutEmpty(loadUrl);
    }

    public String getLoadUrlWithoutEmpty() {
        return mLoadUrlWithoutEmpty;
    }

    private void setLoadUrlWithoutEmpty(String loadUrlWithoutEmpty) {
        if (!EMPTY_URL.equals(loadUrlWithoutEmpty)) {
            this.mLoadUrlWithoutEmpty = loadUrlWithoutEmpty;
        }
    }

    @Override
    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            url = EMPTY_URL;
        }
        super.loadUrl(url);
        if (url.startsWith("javascript:")) {
            return;
        }
        setLoadUrl(url);
    }

    public boolean canGoBack(String firstUrl) {
        String urlWithoutEmpty = getLoadUrlWithoutEmpty();
        return !TextUtils.isEmpty(urlWithoutEmpty) && !urlWithoutEmpty.equals(firstUrl);
    }
}
