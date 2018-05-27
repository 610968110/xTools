package lbx.xtoollib.view.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author lbx
 * @date 2018/5/24.
 */

public class XWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
