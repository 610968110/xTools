package lbx.xtools;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;

import lbx.xtoollib.XTools;
import lbx.xtoollib.base.BaseActivity;
import lbx.xtoollib.bean.DateEntity;
import lbx.xtoollib.phone.SecurityUtil;
import lbx.xtoollib.phone.xLogUtil;
import lbx.xtools.databinding.ActivityMainBinding;

/**
 * @author lbx
 */
public class MainActivity extends BaseActivity {

    public static final int CHOOSE = 0x010;
    private EditText mKey;
    private LogBean mLog;


    @Override
    public void getDataBinding(ViewDataBinding binding) {
        ActivityMainBinding mainBinding = (ActivityMainBinding) binding;
        mLog = new LogBean();
        mainBinding.setLog(mLog);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        mKey = (EditText) findViewById(R.id.et_key);
        //申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    public void initData() {
        //指纹识别
//        Util.fingerCheck();
        //post请求
//        Util.post();
        //post请求
//        Util.postPhp();
        //下载
//        Util.download();
        //搜索intent
//        Util.scanIntentApp(this);
        xLogUtil.e(this, new DateEntity(System.currentTimeMillis()).toString());
    }

    public void choose(View view) {
        //选择文件
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE:
                    String key = mKey.getText().toString().trim();
                    if (TextUtils.isEmpty(key)) {
                        XTools.UiUtil().showToast("key不能为空");
                        return;
                    }
                    Uri uri = data.getData();
                    File file = XTools.UriUtil().uriToFile(MainActivity.this, uri);
                    //log解密
                    StringBuilder builder = new SecurityUtil(key).decryptFile(file);
                    String log = builder.toString();
                    this.mLog.getLog().set(log);
                    Log.e("xys", log);
                    break;
                default:
                    break;
            }
        }
    }
}
