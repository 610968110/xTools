package lbx.xtoollib.phone;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    private static IOUtils INSTANCE;

    public static IOUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (IOUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new IOUtils();
                }
            }
        }
        return INSTANCE;
    }

    private IOUtils() {
    }

    /**
     * 关闭流
     */
    public boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
