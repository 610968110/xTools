package lbx.xtools;

import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import butterknife.BindView;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData() {
        xLogUtil.e("哈哈哈/123abc:。");
    }
}
