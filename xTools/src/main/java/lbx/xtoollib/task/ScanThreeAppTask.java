package lbx.xtoollib.task;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import lbx.xtoollib.XTools;
import lbx.xtoollib.bean.AppBean;
import lbx.xtoollib.listener.OnScanAppListener;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 *         异步扫描手机上第三方应用的任务
 */
public class ScanThreeAppTask extends AsyncTask<Void, Void, Void> {

    private Context mContext;
    private ArrayList<AppBean> mList;
    private OnScanAppListener onScanAppListener;

    public ScanThreeAppTask(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        if (mContext instanceof Activity) {
            XTools.UiUtil().showProgressDialog(mContext);
        }
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //开始扫描
        scanApp();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        XTools.UiUtil().closeProgressDialog();
        if (onScanAppListener != null) {
            onScanAppListener.onScanFinish(mList);
        }
    }

    /**
     * 扫描三方app
     */
    private void scanApp() {
        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        Flowable.fromIterable(installedPackages)
                //过滤掉 系统应用
                .filter(packageInfo -> !((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==
                        ApplicationInfo.FLAG_SYSTEM))
                //把PackageInfo转换成ActionAppBean
                .map(packageInfo -> new AppBean(pm, packageInfo.applicationInfo))
                //在主线程回调
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    mList.add(bean);
                    if (onScanAppListener != null) {
                        onScanAppListener.onScan(mList, bean);
                    }
                });
    }

    public void setOnLoadListener(OnScanAppListener onScanAppListener) {
        this.onScanAppListener = onScanAppListener;
    }
}