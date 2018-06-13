package lbx.xtoollib.phone;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.security.Key;

import javax.crypto.Cipher;

import lbx.xtoollib.XTools;

public class SecurityUtil {

    private static String strDefaultKey = "xTools";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * 使用默认的key
     */
    public SecurityUtil() {
        this(strDefaultKey);
    }

    /**
     * 使用自定义key
     */
    public SecurityUtil(String strKey) {
        strDefaultKey = strKey;
        Key key;
        try {
            key = getKey(strKey.getBytes());
            encryptCipher = Cipher.getInstance("DES");
            // 初始化加密模式
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            // 初始化解密模式
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用默认加密算法
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        // 按单部分操作加密或解密数据，或者结束一个多部分操作。数据将被加密或解密（具体取决于此 Cipher 的初始化方式）。
        byte[] des = encryptCipher.doFinal(arrB);
        //base64加密
        return Base64.encode(des, Base64.DEFAULT);
    }

    /**
     * 使用默认解密算法，与encrypt(byte[] arrB)对应
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        byte[] decode = Base64.decode(arrB, Base64.DEFAULT);
        return decryptCipher.doFinal(decode);
    }

    @NonNull
    public StringBuilder decryptFile(File file) {
        String fileLog = XTools.FileUtil().file2String(file);
        String[] logOne = fileLog.split("#lbx.xTools#");
        StringBuilder b = new StringBuilder();
        for (String s : logOne) {
            if (TextUtils.isEmpty(s)) {
                continue;
            }
            try {
                byte[] doc = new SecurityUtil(strDefaultKey).decrypt(s.getBytes());
                b.append(new String(doc, "utf-8") + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

//    /**
//     * 使用自定义加密算法
//     */
//    public String encrypt(String strIn) throws Exception {
//        return byteArr2HexStr(encrypt(strIn.getBytes()));
//    }
//
//    /**
//     * 使用自定义加密解密算法，与encrypt(String strIn)对应
//     */
//    public String decrypt(String strIn) throws Exception {
//        return new String(decrypt(hexStr2ByteArr(strIn)));
//    }

    /**
     * 生成密钥
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuilder sb = new StringBuilder(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}