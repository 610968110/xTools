package lbx.xtoollib.phone;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lbx.xtoollib.XTools;

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
 * @date 2018/8/10.
 */

public class PropertiesUtil {

    private static PropertiesUtil INSTANCE;

    public static PropertiesUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (PropertiesUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PropertiesUtil();
                }
            }
        }
        return INSTANCE;
    }

    private PropertiesUtil() {
    }

    public Properties getPropertiesFile(String name) {
        Properties properties = new Properties();
        try {
            InputStream in = XTools.getApplicationContext().getAssets().open(name);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getPropertiesFileValue(String fileName, String key) {
        return getPropertiesFile(fileName).getProperty(key);
    }
}
