package lbx.xtoollib.listener;

import java.util.List;

import lbx.xtoollib.bean.AppBean;

public interface OnScanAppListener {

    /**
     * 每扫面完一个应用回调一次
     *
     * @param list 已扫描的应用
     * @param bean 当前扫描的应用
     */
    void onScan(List<AppBean> list, AppBean bean);

    /**
     * 扫描结束的回调
     *
     * @param list 扫描的全部结果
     */
    void onScanFinish(List<AppBean> list);
}