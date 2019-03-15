package lbx.xtoollib;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;

import static android.content.Context.BIND_AUTO_CREATE;

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
        start(mContext);
    }

    public void start(Context context) {
        Context c = context == null ? mContext : context;
        c.startActivity(this);
    }

    public void startForResult(int code) {

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

    public void startService() {
        startService(mContext);
    }

    public void startService(Context context) {
        Context c = context == null ? mContext : context;
        c.startService(this);
    }

    public void stopService() {
        stopService(mContext);
    }

    public void stopService(Context context) {
        Context c = context == null ? mContext : context;
        c.stopService(this);
    }

    public void bindService(Context context, ServiceConnection connection) {
        bindService(context, connection, BIND_AUTO_CREATE);
    }

    public void bindService(Context context, ServiceConnection connection, int flags) {
        context.bindService(this, connection, flags);
    }

    public void unbindService(Context context, ServiceConnection connection) {
        context.unbindService(connection);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startForegroundService(Context context) {
        context.startForegroundService(this);
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

    public XIntent startActivityWithTransition(Activity firstActivity, Pair<View, String>... pairs) {
        XTools.ActivityUtil().startActivityWithTransition(firstActivity, this, pairs);
        return this;
    }

    public XIntent startActivityWithTransitionForResult(Activity firstActivity, int code, Pair<View, String>... pairs) {
        XTools.ActivityUtil().startActivityWithTransitionForResult(firstActivity, this, code, pairs);
        return this;
    }
}
