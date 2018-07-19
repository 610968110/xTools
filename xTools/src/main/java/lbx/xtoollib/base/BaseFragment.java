package lbx.xtoollib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import lbx.xtoollib.XTools;
import lbx.xtoollib.bean.FragmentInfo;

public abstract class BaseFragment extends Fragment {

    private View view;
    protected String mFragmentName = "";
    private Bundle mArguments;
    private ViewDataBinding mViewDataBinding;
    private List<Disposable> mDisposables;
    private List<Subscription> mSubscriptions;
    private Unbinder mBind;
    private FragmentInfo mFragmentInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDisposables = new ArrayList<>();
        mSubscriptions = new ArrayList<>();
        int layoutID = getLayoutID();
        inflate(inflater, layoutID, container, savedInstanceState, false);
        mBind = ButterKnife.bind(this, view);
        getBaseArguments();
        getDataBinding(mViewDataBinding);
        initView(view);
        initData();
        initListener();
        return view;
    }

    public void inflate(LayoutInflater inflater, int layoutID, ViewGroup container, Bundle savedInstanceState, boolean b) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutID, container, b);
        view = mViewDataBinding == null ? inflater.inflate(layoutID, container, b) : mViewDataBinding.getRoot();
    }

    public abstract int getLayoutID();

    private void getBaseArguments() {
        mArguments = getArguments();
        if (mArguments != null) {
            initArguments(mArguments);
        }
    }

    public void initArguments(Bundle bundle) {

    }

    public void getDataBinding(ViewDataBinding binding) {

    }

    public abstract void initView(View view);

    public abstract void initData();

    public void initListener() {

    }


    protected <T> T findView(int viewID) {
        return (T) view.findViewById(viewID);
    }

    public String getFragmentName() {
        return mFragmentName;
    }

    public void setFragmentName(String mFragmentName) {
        this.mFragmentName = mFragmentName;
    }

    public Bundle getArgument() {
        return mArguments;
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

    public FragmentInfo getFragmentInfo() {
        return mFragmentInfo;
    }

    public void setmFragmentInfo(FragmentInfo fragmentInfo) {
        this.mFragmentInfo = fragmentInfo;
    }

    @Override
    public void onDestroy() {
        XTools.UiUtil().closeProgressDialog();
        for (Disposable disposable : mDisposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        for (Subscription subscription : mSubscriptions) {
            if (subscription != null) {
                subscription.cancel();
            }
        }
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }
}
