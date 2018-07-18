package lbx.xtoollib.net;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
 * @date 2018/7/16.
 */

public class DownloadUtil {

    private static DownloadUtil INSTANCE;

    public static DownloadUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (DownloadUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownloadUtil();
                }
            }
        }
        return INSTANCE;
    }

    private DownloadUtil() {
    }

    public boolean downloadByBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        boolean succcess = intent.resolveActivity(context.getPackageManager()) != null;
        if (succcess) {
            context.startActivity(intent);
        }
        return succcess;
    }
}
