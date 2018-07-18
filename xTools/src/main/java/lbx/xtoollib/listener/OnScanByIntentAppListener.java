package lbx.xtoollib.listener;

import java.util.List;

import lbx.xtoollib.bean.AppBean;

public interface OnScanByIntentAppListener {

    /**
     * 扫描结束的回调
     *
     * @param list 扫描的全部结果
     */
    void onScanFinish(List<AppBean> list);
}