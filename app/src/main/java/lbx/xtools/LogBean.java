package lbx.xtools;

import android.databinding.ObservableField;

/**
 * @author lbx
 * @date 2018/6/14.
 */

public class LogBean {

    private ObservableField<String> log = new ObservableField<>();

    public LogBean() {
        log.set("");
    }

    public ObservableField<String> getLog() {
        return log;
    }
}
