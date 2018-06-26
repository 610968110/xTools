package lbx.xtoollib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import lbx.xtoollib.XTools;

public abstract class BaseFragment extends Fragment {

    private View view;
    protected String mFragmentName = "";
    private Bundle mArguments;
    private ViewDataBinding mViewDataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutID = getLayoutID();
        inflate(inflater, layoutID, container, savedInstanceState, false);
        ButterKnife.bind(this, view);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        XTools.UiUtil().closeProgressDialog();
    }
}
