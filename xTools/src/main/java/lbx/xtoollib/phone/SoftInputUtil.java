package lbx.xtoollib.phone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import lbx.xtoollib.XTools;

/**
 * @author lbx
 * @date 2017/1/8.
 */

public class SoftInputUtil {

    private static SoftInputUtil INSTANCE;
    private final InputMethodManager imm;

    public static SoftInputUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (SoftInputUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SoftInputUtil();
                }
            }
        }
        return INSTANCE;
    }

    private SoftInputUtil() {
        imm = (InputMethodManager) XTools.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    /**
     * 隐藏软件盘
     */
    public void hintSoftInput(Activity a) {
        View mv = a.getWindow().peekDecorView();
        if (mv != null) {
            InputMethodManager inputmanger = (InputMethodManager) a
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(mv.getWindowToken(), 0);
        }
    }

    public void showSoftInput(EditText editText) {
        //弹出软键盘
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public boolean isActive(Activity activity) {
        return activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }
}
