package rulerview.test.com.rulerview;


import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class TimeUtils {
    public static final String TAG = TimeUtils.class.getSimpleName();
    private static SimpleDateFormat mDataTime = new SimpleDateFormat("MM-dd HH:mm:ss");
    private static SimpleDateFormat mHourTime = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat mData = new SimpleDateFormat("MM/dd");
    private static TimeZone mTimeZone = TimeZone.getDefault();

    public static void changeTimeZone(String zone) {
        String temp = mTimeZone.getDisplayName(false, TimeZone.SHORT);
        if (!TextUtils.isEmpty(zone) && !temp.equals(zone)) {
            Pattern PATTERN = Pattern.compile("^GMT[-+](\\d{1,2})(:?(\\d\\d))?$");
            if (!PATTERN.matcher(zone).matches()) {
                SDKLog.e(TAG, "setTimeZone not matches " + zone);
                return;
            }
            mTimeZone = TimeZone.getTimeZone(zone);
            SDKLog.e(TAG, "new TimeZone " + mTimeZone.getDisplayName(false, TimeZone.SHORT));
            mHourTime.setTimeZone(mTimeZone);
            mDataTime.setTimeZone(mTimeZone);
            mData.setTimeZone(mTimeZone);
        }
    }

    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public static String getHourTime(long time) {
        return mHourTime.format(time);
    }

    public static String getDataTime(long time) {
        return mDataTime.format(new Date(time));
    }

    public static String getData(long time) {
        return mData.format(new Date(time));
    }

    public static void resume() {
        TimeZone timeZone = TimeZone.getDefault();
        if (!mTimeZone.getID().equals(timeZone)) {
            mDataTime = new SimpleDateFormat("MM-dd HH:mm:ss");
            mHourTime = new SimpleDateFormat("HH:mm:ss");
            mData = new SimpleDateFormat("MM/dd");
            mTimeZone = timeZone;
        }
    }


}
