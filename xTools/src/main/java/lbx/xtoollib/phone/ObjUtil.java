package lbx.xtoollib.phone;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/7/13.
 */
public class ObjUtil {

    private static ObjUtil INSTANCE;

    public static ObjUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ObjUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ObjUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ObjUtil() {
    }

    private Context getContext() {
        return XTools.getApplicationContext();
    }

    public void saveObj(String name, Object td) {
        try {
            FileOutputStream stream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(td);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObj(String name) {
        Object td = null;
        try {
            FileInputStream stream = getContext().openFileInput(name);
            ObjectInputStream ois = new ObjectInputStream(stream);
            td = ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return td;
    }
}
