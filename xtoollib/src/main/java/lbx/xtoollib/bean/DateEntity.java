package lbx.xtoollib.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author lbx
 * @date 2017/12/5.
 */

public class DateEntity implements Parcelable, Serializable {

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public int second;
    public String timeFormat;
    public long millis;

    public DateEntity() {
        this(System.currentTimeMillis());
    }

    public DateEntity(String format) {
        this(System.currentTimeMillis());
    }

    public DateEntity(long time) {
        this(time, FORMAT);
    }

    public DateEntity(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        millis = time;
        timeFormat = formatString(time, format);
    }

    public DateEntity(int year, int month, int day) {
        this(year, month, day, 0, 0, 0, FORMAT);
    }

    public DateEntity(int year, int month, int day, String format) {
        this(year, month, day, 0, 0, 0, format);
    }

    public DateEntity(int year, int month, int day, int hour, int minute, int second) {
        this(year, month, day, hour, minute, second, FORMAT);
    }

    public DateEntity(int year, int month, int day, int hour, int minute, int second, String format) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        millis = calendar.getTimeInMillis();
        timeFormat = formatString(millis, format);
    }

    public DateEntity(String data, String fomart) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(fomart, Locale.CHINA).parse(data));
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.get(Calendar.DATE);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            second = calendar.get(Calendar.SECOND);
            millis = calendar.getTimeInMillis();
            timeFormat = data;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected DateEntity(Parcel in) {
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        second = in.readInt();
        timeFormat = in.readString();
        millis = in.readLong();
    }

    public static final Creator<DateEntity> CREATOR = new Creator<DateEntity>() {
        @Override
        public DateEntity createFromParcel(Parcel in) {
            return new DateEntity(in);
        }

        @Override
        public DateEntity[] newArray(int size) {
            return new DateEntity[size];
        }
    };

    private String formatString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(time);
    }

    public boolean isToday(DateEntity entity) {
        int year = entity.year;
        int month = entity.month;
        int day = entity.day;
        return year == this.year && month == this.month && day == this.day;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", timeFormat='" + timeFormat + '\'' +
                ", millis=" + millis +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeInt(second);
        dest.writeString(timeFormat);
        dest.writeLong(millis);
    }
}
