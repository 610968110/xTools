package lbx.xtoollib.phone;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    private static MD5Utils INSTANCE;

    public static MD5Utils getInstance() {
        if (INSTANCE == null) {
            synchronized (MD5Utils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MD5Utils();
                }
            }
        }
        return INSTANCE;
    }

    private MD5Utils() {
    }

    public String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 进行加密运算,返回加密后的字节数组
            byte[] bytes = digest.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // 没有此算法异常
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 计算文件md5
     *
     * @param filePath
     * @return
     */
    public String encodeFile(String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            FileInputStream in = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] bytes = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception e) {
            // 没有此算法异常
            e.printStackTrace();
        }
        return null;
    }
}
