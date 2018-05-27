package lbx.xtoollib.phone;

import java.io.Closeable;

/**
 * @author lbx
 * @date 2018/5/8.
 */

public class CloseUtil {

    private static CloseUtil INSTANCE;

    public static CloseUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (CloseUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CloseUtil();
                }
            }
        }
        return INSTANCE;
    }

    private CloseUtil() {
    }

    public boolean close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
