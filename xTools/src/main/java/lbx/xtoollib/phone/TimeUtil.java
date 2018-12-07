package lbx.xtoollib.phone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author lbx
 * @date 2017/2/18.
 */

public class TimeUtil {

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<>();
    private static String defaultFormat = "yyyy-MM-dd HH:mm:ss";

    private static TimeUtil INSTANCE;

    public static TimeUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (TimeUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TimeUtil();
                }
            }
        }
        return INSTANCE;
    }

    private TimeUtil() {
    }

    private SimpleDateFormat getDateFormat() {
        return getDateFormat(defaultFormat);
    }

    private SimpleDateFormat getDateFormat(String format) {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat(format, Locale.CHINA));
        }
        return DateLocal.get();
    }

    public String formatTime(long time) {
        return formatTime(time, defaultFormat);
    }

    public String formatTime(long time, String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(time);
    }

    public String formatNowTime() {
        return formatNowTime(defaultFormat);
    }

    public String formatNowTime(String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(System.currentTimeMillis());
    }

    public boolean isToday(String day) {
        return isToday(day, defaultFormat);
    }

    public boolean isToday(String day, String format) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);
            Calendar cal = Calendar.getInstance();
            Date date = getDateFormat(format).parse(day);
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
                if (diffDay == 0) {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isToday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isYesterday(String day) {
        return isYesterday(day, defaultFormat);
    }

    public boolean isYesterday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isYesterday(String day, String format) {
        try {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            Date date = getDateFormat(format).parse(day);
            cal.setTime(date);
            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
                if (diffDay == -1) {
                    return true;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getHourString(long time) {
        return getHourString(time, "HH:mm");
    }

    public String getHourString(long time, String format) {
        return isToday(time) ? formatTime(time, format) :
                isYesterday(time) ? "昨天" + formatTime(time, format) :
                        formatTime(time, defaultFormat);
    }

    public long formatTime(String time) {
        return formatTime(time, defaultFormat);
    }

    public long formatTime(String time, String format) {
        long timeInMillis = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat(format, Locale.CHINA).parse(time));
            timeInMillis = c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMillis;
    }

    public boolean isBeforeNow(String time, String format) {
        return isBeforeNow(time, format, 0);
    }

    public boolean isBeforeNow(long time) {
        return time - System.currentTimeMillis() < 0;
    }

    public boolean isBeforeNow(String time) {
        return isBeforeNow(time, defaultFormat);
    }

    public boolean isBeforeNow(String time, long offset) {
        return isBeforeNow(time, defaultFormat, offset);
    }

    /**
     * @param time
     * @param format
     * @param offset 时间偏移量  毫秒  例如1000*60*5位5分钟 则比现在提前5分钟的为true
     *               例：正数：传入时间是现在时间的五分钟后  也是 true
     * @return
     */
    public boolean isBeforeNow(String time, String format, long offset) {
        long timeInMillis = 0;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat(format, Locale.CHINA).parse(time));
            timeInMillis = c.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMillis - System.currentTimeMillis() <= offset;
    }
}
