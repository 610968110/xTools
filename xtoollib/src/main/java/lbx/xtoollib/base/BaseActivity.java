package lbx.xtoollib.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import lbx.xtoollib.XTools;

/**
 * @author lbx
 * @date 2016/5/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Intent baseIntent;
    private ViewDataBinding mViewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeLayout();
        int layoutID = getLayoutID();
        if (layoutID != -1) {
            mViewDataBinding = DataBindingUtil.setContentView(this, layoutID);
        } else {
            setContentView(getLayout());
        }
        XTools.ActivityUtil().addActivity(this);
        ButterKnife.bind(this);
        View view = findViewById(android.R.id.content);
        getBaseIntent();
        getDataBinding(mViewDataBinding);
        initView(view);
        initData();
        initListener();
    }

    public void beforeLayout() {
    }

    public abstract int getLayoutID();

    protected <T> T findView(int viewID) {
        return (T) findViewById(viewID);
    }


    public View getLayout() {
        return new View(this);
    }

    private void getBaseIntent() {
        baseIntent = getIntent();
        if (baseIntent != null) {
            initIntent(baseIntent);
        }
    }

    public void initIntent(Intent intent) {

    }

    public void getDataBinding(ViewDataBinding binding) {

    }

    public abstract void initView(View view);

    public abstract void initData();

    public void initListener() {

    }

    public void onClick(View view, int id) {

    }

    @Override
    public void onClick(View v) {
        onClick(v, v.getId());
    }

    public void saveFinish() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XTools.ActivityUtil().removeActivity(this);
        XTools.UiUtil().closeProgressDialog();
    }
}
