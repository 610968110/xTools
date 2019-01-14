package lbx.xtoollib.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import lbx.xtoollib.XTools;

/**
 * @author lbx
 * @date 2016/5/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Intent baseIntent;
    private ViewDataBinding mViewDataBinding;
    private List<Disposable> mDisposables;
    private List<Subscription> mSubscriptions;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposables = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        beforeLayout();
        int layoutID = getLayoutID();
        if (layoutID != -1 && layoutID != 0) {
            mViewDataBinding = DataBindingUtil.setContentView(this, layoutID);
        } else {
            setContentView(getLayout());
        }
        XTools.ActivityUtil().addActivity(this);
        mBind = ButterKnife.bind(this);
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

    protected <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
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

    public abstract void getDataBinding(ViewDataBinding binding);

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

    public List<Disposable> addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
        return mDisposables;
    }

    public List<Disposable> removeDisposable(Disposable disposable) {
        if (mDisposables.contains(disposable)) {
            mDisposables.remove(disposable);
        }
        return mDisposables;
    }

    public List<Subscription> addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
        return mSubscriptions;
    }

    public List<Subscription> removeSubscription(Subscription subscription) {
        if (mSubscriptions.contains(subscription)) {
            mSubscriptions.remove(subscription);
        }
        return mSubscriptions;
    }

    @Override
    protected void onDestroy() {
        XTools.ActivityUtil().removeActivity(this);
        if (closeProgressDialogWithDestroy()) {
            XTools.UiUtil().closeProgressDialog();
        }
        if (mDisposables != null) {
            for (Disposable d : mDisposables) {
                if (d != null && !d.isDisposed()) {
                    d.dispose();
                }
            }
            mDisposables.clear();
        }
        XTools.UiUtil().closeProgressDialog();
        if (mSubscriptions != null) {
            for (Subscription s : mSubscriptions) {
                if (s != null) {
                    s.cancel();
                }
            }
            mSubscriptions.clear();
        }
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    public boolean closeProgressDialogWithDestroy() {
        return true;
    }
}
