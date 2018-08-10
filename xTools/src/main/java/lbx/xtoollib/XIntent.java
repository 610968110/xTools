package lbx.xtoollib;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

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
 * @date 2018/7/17.
 */

public class XIntent extends Intent {

    private Context mContext;

    public XIntent(Intent o) {
        super(o);
    }

    public XIntent(String action) {
        super(action);
    }

    public XIntent(String action, Uri uri) {
        super(action, uri);
    }

    public XIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
        mContext = packageContext;
    }

    public XIntent(String action, Uri uri, Context packageContext, Class<?> cls) {
        super(action, uri, packageContext, cls);
        mContext = packageContext;
    }

    public void start() {
        mContext.startActivity(this);
    }

    public void startForResult(int code) {
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(this, code);
        } else {
            start();
        }
    }

    public void startForResult(Activity activity, int code) {
        activity.startActivityForResult(this, code);
    }

    public void startForResult(Fragment fragment, int code) {
        fragment.startActivityForResult(this, code);
    }

    public void startForResult(android.app.Fragment fragment, int code) {
        fragment.startActivityForResult(this, code);
    }

    @Override
    public XIntent addFlags(int flags) {
        super.addFlags(flags);
        return this;
    }

    @Override
    public XIntent putExtra(String name, boolean value) {
        super.putExtra(name, value);
        return this;
    }

    @Override
    public XIntent putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        super.putCharSequenceArrayListExtra(name, value);
        return this;
    }

    @Override
    public XIntent putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        super.putParcelableArrayListExtra(name, value);
        return this;
    }

    @Override
    public XIntent putStringArrayListExtra(String name, ArrayList<String> value) {
        super.putStringArrayListExtra(name, value);
        return this;
    }

    @Override
    public XIntent putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        super.putIntegerArrayListExtra(name, value);
        return this;
    }

    @Override
    public XIntent setAction(String action) {
        return (XIntent) super.setAction(action);
    }

    @Override
    public XIntent setData(Uri data) {
        return (XIntent) super.setData(data);
    }

    @Override
    public XIntent setClass(Context packageContext, Class<?> cls) {
        return (XIntent) super.setClass(packageContext, cls);
    }

    @Override
    public XIntent setClassName(Context packageContext, String className) {
        return (XIntent) super.setClassName(packageContext, className);
    }

    @Override
    public XIntent setClassName(String packageName, String className) {
        return (XIntent) super.setClassName(packageName, className);
    }

    @Override
    public XIntent setDataAndType(Uri data, String type) {
        return (XIntent) super.setDataAndType(data, type);
    }

    @Override
    public XIntent setPackage(String packageName) {
        return (XIntent) super.setPackage(packageName);
    }

    @Override
    public void setExtrasClassLoader(ClassLoader loader) {
        super.setExtrasClassLoader(loader);
    }

    @Override
    public XIntent setDataAndNormalize(Uri data) {
        return (XIntent) super.setDataAndNormalize(data);
    }

    @Override
    public XIntent setType(String type) {
        return (XIntent) super.setType(type);
    }

    @Override
    public XIntent setTypeAndNormalize(String type) {
        return (XIntent) super.setTypeAndNormalize(type);
    }

    @Override
    public XIntent setDataAndTypeAndNormalize(Uri data, String type) {
        return (XIntent) super.setDataAndTypeAndNormalize(data, type);
    }

    @Override
    public XIntent setFlags(int flags) {
        return (XIntent) super.setFlags(flags);
    }

    @Override
    public XIntent setComponent(ComponentName component) {
        return (XIntent) super.setComponent(component);
    }
}
