package lbx.xtools;

import android.databinding.ViewDataBinding;
import android.view.View;

import butterknife.BindView;
import lbx.xtoollib.XTools;
import lbx.xtoollib.base.BaseActivity;
import lbx.xtoollib.phone.xLogUtil;
import lbx.xtoollib.view.web.XWebView;

/**
 * @author lbx
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.webView)
    public XWebView mWebView;

    @Override
    public void getDataBinding(ViewDataBinding binding) {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mWebView.loadUrl("file:///android_asset/testimg.html");
    }

    @Override
    public void initData() {
        xLogUtil.e("XTools.AppUtil().getMainThreadId():" + XTools.AppUtil().getMainThreadId());
        xLogUtil.e("XTools.AppUtil().isRunOnUIThread:" + XTools.AppUtil().isRunOnUIThread());
        new Thread(new Runnable() {
            @Override
            public void run() {
                xLogUtil.e("XTools.AppUtil().isRunOnUIThread Thread:" + XTools.AppUtil().isRunOnUIThread());
            }
        }).start();
    }
}
