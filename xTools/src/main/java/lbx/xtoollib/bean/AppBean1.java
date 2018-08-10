package lbx.xtoollib.bean;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

public class AppBean1 implements Parcelable {

    private String name;
    private String pkg;
    private byte[] icon;
    private ResolveInfo resolveInfo;
    private String launchActivity;

    public AppBean1(PackageManager pm, ResolveInfo resolveInfo) {
        this.name = resolveInfo.loadLabel(pm).toString();
        this.pkg = resolveInfo.activityInfo.packageName;
        this.launchActivity = resolveInfo.activityInfo.name;
        this.resolveInfo = resolveInfo;
        Drawable icon = resolveInfo.loadIcon(pm);
        if (icon instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            setBmp(bitmap);
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    protected AppBean1(Parcel in) {
        name = in.readString();
        pkg = in.readString();
        icon = in.createByteArray();
        resolveInfo = in.readParcelable(ResolveInfo.class.getClassLoader());
        launchActivity = in.readString();
    }

    public static final Creator<AppBean1> CREATOR = new Creator<AppBean1>() {
        @Override
        public AppBean1 createFromParcel(Parcel in) {
            return new AppBean1(in);
        }

        @Override
        public AppBean1[] newArray(int size) {
            return new AppBean1[size];
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

    public String getLaunchActivity() {
        return launchActivity;
    }

    public void setLaunchActivity(String launchActivity) {
        this.launchActivity = launchActivity;
    }

    public ResolveInfo getResolveInfo() {
        return resolveInfo;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "name='" + name + '\'' +
                "launchActivity='" + launchActivity + '\'' +
                ", pkg='" + pkg + '\'' +
                ", resolveInfo='" + resolveInfo + '\'' +
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
        dest.writeParcelable(resolveInfo, flags);
        dest.writeString(launchActivity);
    }
}
