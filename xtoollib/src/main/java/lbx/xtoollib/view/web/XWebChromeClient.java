package lbx.xtoollib.view.web;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class XWebChromeClient extends WebChromeClient {

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (onWebChromeClientListener != null) {
            onWebChromeClientListener.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
        return true;
    }

    public void openFileChooser(ValueCallback<Uri> upLoadMsg) {
        if (onWebChromeClientListener != null) {
            onWebChromeClientListener.openFileChooser(upLoadMsg);
        }
    }

    public void openFileChooser(ValueCallback<Uri> upLoadMsg, String type) {
        if (onWebChromeClientListener != null) {
            onWebChromeClientListener.openFileChooser(upLoadMsg,type);
        }
    }

    public void openFileChooser(ValueCallback<Uri> upLoadMsg, String type, String capture) {
        if (onWebChromeClientListener != null) {
            onWebChromeClientListener.openFileChooser(upLoadMsg,type,capture);
        }
    }

    private OnWebChromeClientListener onWebChromeClientListener;

    public interface OnWebChromeClientListener {
        void onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams);

        void openFileChooser(ValueCallback<Uri> upLoadMsg);

        void openFileChooser(ValueCallback<Uri> upLoadMsg, String type);

        void openFileChooser(ValueCallback<Uri> upLoadMsg, String type, String capture);
    }

    public void setOnWebChromeClientListener(OnWebChromeClientListener onWebChromeClientListener) {
        this.onWebChromeClientListener = onWebChromeClientListener;
    }
}