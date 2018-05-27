package lbx.xtoollib.phone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author lbx
 * @date 2017/1/8.
 */

public class SoftInputUtil {

    private static SoftInputUtil INSTANCE;

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
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
}
