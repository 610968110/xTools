package lbx.xtoollib.view.web;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static lbx.xtoollib.view.web.XWebView.EMPTY_URL;

/**
 * @author lbx
 * @date 2018/5/24.
 */

public class XWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        XWebView webView = (XWebView) view;
        webView.setLoadUrl(url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        view.loadUrl(EMPTY_URL);
        View errorView = ((XWebView) view).getErrorView();
        if (errorView != null) {
            // 在这里显示自定义错误页
            errorView.setVisibility(View.VISIBLE);
        }
        view.setVisibility(View.GONE);
    }

    // 新版本，只会在Android6及以上调用
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (request.isForMainFrame()) {
            view.loadUrl(EMPTY_URL);
            View errorView = ((XWebView) view).getErrorView();
            if (errorView != null) {
                // 在这里显示自定义错误页
                errorView.setVisibility(View.VISIBLE);
            }
            view.setVisibility(View.GONE);
        }
    }

}
