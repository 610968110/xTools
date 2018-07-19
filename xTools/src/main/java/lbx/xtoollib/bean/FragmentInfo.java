package lbx.xtoollib.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.io.Serializable;

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
 * @date 2017/11/10.
 * fragment基本信息  并不是所有fragment都有 设置了才会有
 */

public class FragmentInfo implements Parcelable, Serializable {

    /**
     * 绑定的图片资源id
     */
    private int drawableId;
    /**
     * 绑定的名字资源id
     */
    private int nameId = -1;
    private String name;
    private Parcelable parcelable;
    private Serializable serializable;

    public FragmentInfo(@DrawableRes int drawableId, @StringRes int nameId) {
        this.drawableId = drawableId;
        this.nameId = nameId;
        if (nameId != -1) {
            name = XTools.ResUtil().getString(nameId);
        }
    }

    public FragmentInfo(@DrawableRes int drawableId, String name) {
        this.drawableId = drawableId;
        this.name = name;
    }

    protected FragmentInfo(Parcel in) {
        drawableId = in.readInt();
        nameId = in.readInt();
        name = in.readString();
    }

    public static final Creator<FragmentInfo> CREATOR = new Creator<FragmentInfo>() {
        @Override
        public FragmentInfo createFromParcel(Parcel in) {
            return new FragmentInfo(in);
        }

        @Override
        public FragmentInfo[] newArray(int size) {
            return new FragmentInfo[size];
        }
    };

    @Override
    public String toString() {
        return "FragmentInfo{" +
                "drawableId=" + drawableId +
                ", nameId=" + nameId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(drawableId);
        dest.writeInt(nameId);
        dest.writeString(name);
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getNameId() {
        return nameId;
    }

    public void setNameId(int nameId) {
        this.nameId = nameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parcelable getParcelable() {
        return parcelable;
    }

    public void setParcelable(Parcelable parcelable) {
        this.parcelable = parcelable;
    }

    public Serializable getSerializable() {
        return serializable;
    }

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }
}
