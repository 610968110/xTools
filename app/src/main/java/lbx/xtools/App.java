package lbx.xtools;

import android.app.Application;

import lbx.xtoollib.XTools;
import lbx.xtoollib.phone.xLogUtil;

/**
 * @author lbx
 * @date 2018/5/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XTools xTools = new XTools.Builder()
                //打印log
                .log(true)
                //设置log的tag
                .logTag("xys")
                //设置显示log的级别
                .logLevel(xLogUtil.LEVEL_VERBOSE)
                //设置crashLog的文件存储路径
                .errLogFilePath("xTools")
                //设置crashLog的文件存储名
                .errLogFileName("ERR")
                //设置crashLog的打印，第一个参数是打印到文件，第二个参数是打印到log
                .errLogFile(false, true)
                //log是否打印到文件  设置打印路径
                // 第三个参数:加密的key，打印到file的log是否加密(des+base64对称加密) null为不加密
                .logPrintFile(true, "xTools", "lbx")
                .build(this);
        //初始化
        xTools.init();
    }
}
