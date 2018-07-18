package lbx.xtoollib.bean;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

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
 * @date 2018/7/18.
 */

public class AppBean implements Parcelable {

    private String name;
    private String pkg;
    private byte[] icon;
    private ApplicationInfo applicationInfo;

    public AppBean(PackageManager pm, ApplicationInfo applicationInfo) {
        this.name = applicationInfo.loadLabel(pm).toString();
        this.pkg = applicationInfo.packageName;
        this.applicationInfo = applicationInfo;
        Drawable icon = applicationInfo.loadIcon(pm);
        if (icon instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            setBmp(bitmap);
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public AppBean(String name, String pkg, Bitmap icon, ApplicationInfo applicationInfo) {
        this.name = name;
        this.pkg = pkg;
        setBmp(icon);
        this.applicationInfo = applicationInfo;
    }

    protected AppBean(Parcel in) {
        name = in.readString();
        pkg = in.readString();
        icon = in.createByteArray();
        applicationInfo = in.readParcelable(ApplicationInfo.class.getClassLoader());
    }

    public static final Creator<AppBean> CREATOR = new Creator<AppBean>() {
        @Override
        public AppBean createFromParcel(Parcel in) {
            return new AppBean(in);
        }

        @Override
        public AppBean[] newArray(int size) {
            return new AppBean[size];
        }
    };

    private void setBmp(Bitmap bitmap) {
        if (bitmap != null) {
            this.icon = XTools.BitmapUtil().bmp2byte(bitmap);
        } else {
            this.icon = new byte[0];
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Bitmap getIcon() {
        return XTools.BitmapUtil().byte2Bmp(icon);
    }

    public void setIcon(Bitmap bitmap) {
        setBmp(bitmap);
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "name='" + name + '\'' +
                ", pkg='" + pkg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pkg);
        dest.writeByteArray(icon);
        dest.writeParcelable(applicationInfo, flags);
    }
}
