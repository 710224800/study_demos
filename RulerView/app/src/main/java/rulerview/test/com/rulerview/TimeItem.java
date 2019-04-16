package rulerview.test.com.rulerview;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeItem implements Parcelable, Comparable<TimeItem> {
    public long startTime;//单位为毫秒
    public long duration;
    public long endTime;
    public int isSaveFile;//是否是永久保存文件0否 1 是
    public boolean isMotion = false;

    public TimeItem(long startTime, long duration, int isSaveFile) {
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime + duration;
        this.isSaveFile = isSaveFile;
    }

    public TimeItem(long startTime, long duration, int isSaveFile, boolean isMotion) {
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime + duration;
        this.isSaveFile = isSaveFile;
        this.isMotion = isMotion;
    }

    protected TimeItem(Parcel in) {
        startTime = in.readLong();
        duration = in.readLong();
        endTime = in.readLong();
        isSaveFile = in.readInt();
    }

    public String getThumbUrl() {
//        return CameraImageLoader.CAMERA_PREFIX + String.valueOf(startTime);
        return null;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime);
        dest.writeLong(duration);
        dest.writeLong(endTime);
        dest.writeInt(isSaveFile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TimeItem> CREATOR = new Creator<TimeItem>() {
        @Override
        public TimeItem createFromParcel(Parcel in) {
            return new TimeItem(in);
        }

        @Override
        public TimeItem[] newArray(int size) {
            return new TimeItem[size];
        }
    };

    //单位为s
    public static TimeItem createTimeItem(byte[] data, int pos, int isSaveFile) {
        long startTime = Packet.byteArrayToInt_Little(data, pos) * 1000l;
        long duration = 60 * 1000;
        return new TimeItem(startTime, duration, isSaveFile);
    }

    public static TimeItem createTimeItem(byte[] data, int pos) {
        long startTime = Packet.byteArrayToInt_Little(data, pos) * 1000l;
        long duration = data[pos + 4] * 1000l;
        byte motion = data[pos + 5];
        byte save = data[pos + 6];
        return new TimeItem(startTime, duration, save, motion == 1);
    }

    public static int timeItemLen() {
        return 4;
    }

    public static int timeItemLenNew() {
        return 8;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean contains(long time) {
        return time >= startTime && time < endTime;
    }

    @Override
    public int compareTo(TimeItem another) {
        return (int) (this.startTime - another.startTime);
    }

    @Override
    public int hashCode() {
        return (int) (this.startTime / 1000);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof TimeItem)) {
            return false;
        }
        return this.startTime == ((TimeItem) o).startTime;
    }
}
