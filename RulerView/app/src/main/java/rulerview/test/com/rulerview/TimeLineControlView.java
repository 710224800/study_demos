
package rulerview.test.com.rulerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class TimeLineControlView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    static final String TAG = "TimeLineControlView";

    static final int MSG_NOTIFY_UPDATE = 1;
    static final int MSG_NEXT_LONG_PRESS = 2;
    static final int MSG_LAST_LONG_PRESS = 3;

    static final int LONG_PRESS_MOVE = 30000;//长按时间移动时长
    static final int TIME_LINE_MINITUES = 5;
    static final long MINITUES = 60 * 1000;
    static final float MAX_SCALE = 10.0f;
    static final float MIN_SCALE = 0.5f;

    private int mLineLen = 7001;
    int mTopH;
    int mTimeBarW;
    Paint mPaint;
    long mOffsetCurrentTime;
    long mCurrentTime;// milliseconds
    List<TimeItem> mTimeItems = new ArrayList<TimeItem>();
    int mOffsetPos;//
    int mTouchStartX;

    int mWidthPer5Minutes;
    int mWidthPer5MinutesBase;
    float mWidthScaleFators = 1.0f;

    int mHalfW;
    //    SimpleDateFormat localSimpleDateFormat;
    TimeLineCallback mTimeLineCallback;
    ScaleGestureDetector mScaleGestureDetector;
    GestureDetector mGestureDetector;
    boolean mOnscaleBegin = false;
    long mBeforeScaleTime;

    Drawable mTimelineBg;
    Drawable mTimelineSelBg;
    Drawable mTimelMotionBg;
    Drawable mTimelineSaveSelBg;
    Drawable mTimelinePointer;
    int mBottomPadding;

    int mLastDrawbleWidth;
    ImageDrawable mLastDrawable;
    ImageDrawable mNextDrawable;
    ArrayList<ImageDrawable> mImageDrawables = new ArrayList<>();

    boolean mIsLastPress = false;
    boolean mIsLastLongPress = false;

    boolean mIsNextPress = false;
    boolean mIsNextLongPress = false;

    boolean mMediaPlayer = false;

    boolean mNeedNextButton = true;

    boolean mIsPress = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NOTIFY_UPDATE:
                    if (mTimeLineCallback != null) {
                        mCurrentTime = System.currentTimeMillis() + mOffsetCurrentTime;
                        long selectTime = getSelectTime();
                        if (Math.abs(mCurrentTime - selectTime) < 15 * 1000) {
                            setLivePlay();
                            SDKLog.e(TAG, " selectTime near now ");
                        } else {
                            if (mTimeItems.isEmpty()) {
                                updatePlayTime(System.currentTimeMillis());
                            } else {
                                mTimeLineCallback.onSelectTime(getSelectTime());
                            }
                        }
                        mIsPress = false;
                    }
                    break;
                case MSG_LAST_LONG_PRESS:
                    if (mIsLastPress) {
                        mIsLastLongPress = true;
                        movePressLast();
                        mHandler.sendEmptyMessageDelayed(MSG_LAST_LONG_PRESS, 50);
                    } else {
                        mHandler.removeMessages(MSG_LAST_LONG_PRESS);
                    }
                    break;
                case MSG_NEXT_LONG_PRESS:
                    if (mIsNextPress) {
                        mIsNextLongPress = true;
                        movePressNext();
                        mHandler.sendEmptyMessageDelayed(MSG_NEXT_LONG_PRESS, 50);
                    } else {
                        mHandler.removeMessages(MSG_NEXT_LONG_PRESS);
                    }
                    break;
            }
        }
    };

    public void setNeedNextButton(boolean bl) {
        mNeedNextButton = bl;
    }

    public void setTimeLineCallback(TimeLineCallback timeLineCallback) {
        mTimeLineCallback = timeLineCallback;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mWidthScaleFators *= detector.getScaleFactor();
        scale(mWidthScaleFators);
        return true;
    }

    public void scale(float scaleFator) {
        mWidthScaleFators = scaleFator;
        if (mWidthScaleFators > MAX_SCALE) {
            mWidthScaleFators = MAX_SCALE;
        }
        if (mWidthScaleFators < MIN_SCALE) {
            mWidthScaleFators = MIN_SCALE;
        }
        mWidthPer5Minutes = (int) (mWidthPer5MinutesBase * mWidthScaleFators);
        mOffsetPos = (int) ((mCurrentTime - mBeforeScaleTime) * mWidthPer5Minutes
                / (TIME_LINE_MINITUES * MINITUES));
        invalidate();
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        mOnscaleBegin = true;
        mBeforeScaleTime = getSelectTime();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    // 回调函数
    public static interface TimeLineCallback {
        // 更新滑动过程中时间
        public void onUpdateTime(long time);

        // 滑动结束后，选中时间
        public void onSelectTime(long time);

        public void onPlayLive();
    }

    //设置录制时间
    public void setTimeItems(List<TimeItem> items) {
        mTimeItems = items;
        invalidate();
    }

    public TimeLineControlView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
//        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                if(mWidthScaleFators<1.0){
//                    scale(1.0f);
//                }else if(mWidthScaleFators<2.0){
//                    scale(2.0f);
//                }else{
//                    scale(2.0f);
//                }
//                return true;
//            }
//        });
//        localSimpleDateFormat = new SimpleDateFormat("MM/dd");
//        localSimpleDateFormat.setTimeZone(TimeZone.getDefault());

        Resources resources = getContext().getResources();
        mTopH = resources.getDimensionPixelSize(R.dimen.time_line_topbar_h);
        mTimeBarW = resources.getDimensionPixelSize(R.dimen.time_line_timebar_w);

        mWidthPer5MinutesBase = resources.getDimensionPixelSize(R.dimen.time_line_time_5m_w);
        mWidthPer5Minutes = (int) (mWidthPer5MinutesBase * mWidthScaleFators);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mTimelineBg = resources.getDrawable(R.mipmap.progress_bg_01);
        mTimelineSelBg = resources.getDrawable(R.mipmap.progress_bg_02);
        mTimelMotionBg = resources.getDrawable(R.mipmap.progress_bg_04);
        mTimelineSaveSelBg = resources.getDrawable(R.mipmap.progress_bg_03);
        mBottomPadding = 0;
        mTimelinePointer = resources.getDrawable(R.mipmap.progress_components_pointer_nor);

        if (mNeedNextButton) {
            mLastDrawable = new ImageDrawable(resources.getDrawable(R.mipmap.progress_button_last_nor), resources.getDrawable(R.mipmap.progress_button_last_pres));
            mNextDrawable = new ImageDrawable(resources.getDrawable(R.mipmap.progress_button_next_nor), resources.getDrawable(R.mipmap.progress_button_next_pres));
            mLastDrawbleWidth = resources.getDimensionPixelOffset(R.dimen.time_line_last_w);
            mImageDrawables.add(mLastDrawable);
            mImageDrawables.add(mNextDrawable);

            mLastDrawable.setOnTouchListener(new ImageDrawable.OnTouchListener() {
                @Override
                public void onActionDown() {
                    SDKLog.d(TAG, "last down");
                    mIsPress = true;
                    mIsLastLongPress = false;
                    mIsLastPress = true;
                    mHandler.sendEmptyMessageDelayed(MSG_LAST_LONG_PRESS, 1000);
                }

                @Override
                public void onActionUp() {
                    SDKLog.d(TAG, "last up");
                    mIsLastPress = false;
                    mHandler.removeMessages(MSG_LAST_LONG_PRESS);
                    if (!mIsLastLongPress) {
                        movePrev();
                    }
                    mIsLastLongPress = false;

                    notifyUpdate();
                }
            });
            mNextDrawable.setOnTouchListener(new ImageDrawable.OnTouchListener() {
                @Override
                public void onActionDown() {
                    SDKLog.d(TAG, "next down");
                    mIsPress = true;
                    mIsNextLongPress = false;
                    mIsNextPress = true;
                    mHandler.sendEmptyMessageDelayed(MSG_NEXT_LONG_PRESS, 1000);
                }

                @Override
                public void onActionUp() {
                    SDKLog.d(TAG, "next up");
                    mIsNextPress = false;
                    mHandler.removeMessages(MSG_NEXT_LONG_PRESS);
                    if (!mIsNextLongPress) {
                        moveNext();
                    }
                    mIsNextLongPress = false;

                    notifyUpdate();
                }
            });
        }
    }

    void movePrev() {
        if (mTimeItems.size() == 0)
            return;
        int moveTime = (int) (LONG_PRESS_MOVE / mWidthScaleFators);
        long selectTime = getSelectTime() - moveTime;
        if (selectTime < mTimeItems.get(0).startTime) {
            return;
        }
        if (selectTime < mTimeItems.get(0).getEndTime()) {
            setPlayTime(mTimeItems.get(0).startTime);
            return;
        }
        TimeItem timeItem = getNeedItem(selectTime, false);
        if (timeItem != null) {
            setPlayTime(timeItem.startTime);
            return;
        }
        setPlayTime(mTimeItems.get(0).startTime);
    }

    void movePressLast() {
        if (mTimeItems.size() == 0)
            return;
        int moveTime = (int) (LONG_PRESS_MOVE / mWidthScaleFators);
        long selectTime = getSelectTime() - moveTime;
        if (selectTime < mTimeItems.get(0).startTime) {
            return;
        }
        if (selectTime < mTimeItems.get(0).getEndTime()) {
            setPlayTime(selectTime);
            return;
        }
        for (int i = 1, len = mTimeItems.size(); i < len; i++) {
            TimeItem timeItem = mTimeItems.get(i);
            if (selectTime < timeItem.startTime) {
                if (selectTime < mTimeItems.get(i - 1).getEndTime() - moveTime) {
                    setPlayTime(selectTime);
                } else {
                    setPlayTime(mTimeItems.get(i - 1).getEndTime() - moveTime);
                }
                break;
            }
        }
    }

    void moveNext() {
        if (mTimeItems.size() == 0)
            return;
        int moveTime = (int) (LONG_PRESS_MOVE / mWidthScaleFators);
        long selectTime = getSelectTime() + moveTime;
        if (selectTime > mTimeItems.get(mTimeItems.size() - 1).getEndTime()) {
            return;
        }
        if (selectTime >= mTimeItems.get(mTimeItems.size() - 1).startTime) {
            setLivePlay();
            return;
        }
        TimeItem timeItem = getNeedItem(selectTime, true);
        if (timeItem != null) {
            setPlayTime(timeItem.startTime);
        } else {
            setLivePlay();
        }
    }

    void movePressNext() {
        if (mTimeItems.size() == 0)
            return;
        int moveTime = (int) (LONG_PRESS_MOVE / mWidthScaleFators);
        long selectTime = getSelectTime() + moveTime;
        if (selectTime > mTimeItems.get(mTimeItems.size() - 1).getEndTime()) {
            return;
        }
        if (selectTime >= mTimeItems.get(mTimeItems.size() - 1).startTime) {
            setPlayTime(selectTime);
            return;
        }
        for (int i = mTimeItems.size() - 2; i >= 0; i--) {
            TimeItem timeItem = mTimeItems.get(i);
            if (selectTime > timeItem.startTime) {
                if (selectTime < timeItem.getEndTime()) {
                    setPlayTime(selectTime);
                } else {
                    setPlayTime(mTimeItems.get(i + 1).startTime);
                }
                break;
            }
        }
    }

    void notifyUpdate() {
        mHandler.removeMessages(MSG_NOTIFY_UPDATE);
        mHandler.sendEmptyMessageDelayed(MSG_NOTIFY_UPDATE, 1200);
    }

    void setLivePlay() {
        mHandler.removeMessages(MSG_NOTIFY_UPDATE);
        setPlayTime(mCurrentTime);
        if (mTimeLineCallback != null) {
            mTimeLineCallback.onPlayLive();
        }
    }

    public void setMediaPlayer(boolean mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    // 同步当前时间 ms
    public void synCurrentTime(long time) {
        mOffsetCurrentTime = time - System.currentTimeMillis();
        mCurrentTime = time;
        postInvalidate();
    }

    // 更新播放时间 ms
    public void updatePlayTime(long playTime) {
        if (playTime == 0) {
            playTime = mCurrentTime;
        }
        long selecttime = getSelectTime();
        mOffsetPos -= (int) ((playTime - selecttime) * mWidthPer5Minutes
                / (TIME_LINE_MINITUES * MINITUES));
        postInvalidate();
    }


    private void setPlayTime(long playTime) {
        long selectTime = getSelectTime();
        mOffsetPos -= (int) ((playTime - selectTime) * mWidthPer5Minutes
                / (TIME_LINE_MINITUES * MINITUES));
        postInvalidate();
        if (mTimeLineCallback != null) {
            mTimeLineCallback.onUpdateTime(playTime);
        }
    }

    public void setJustPlayTime(long playTime) {
        long selectTime = getSelectTime();
        mOffsetPos -= (int) ((playTime - selectTime) * mWidthPer5Minutes
                / (TIME_LINE_MINITUES * MINITUES));
        postInvalidate();
    }

    public boolean isPlayRealTime() {
        long time = getSelectTime();
        if (mTimeItems != null && mTimeItems.size() > 0) {
            if (time > mTimeItems.get(mTimeItems.size() - 1).getEndTime()) {
                return true;
            }
        }
        return false;
    }

    // 获取当前选中时间
    public long getSelectTime() {
        return getTime(mHalfW);
    }

    long getTime(int pos) {
        return (long) (mCurrentTime + (pos - mHalfW - mOffsetPos) * (TIME_LINE_MINITUES * MINITUES)
                / mWidthPer5Minutes);
    }

    int getPos(long time) {
        return (int) ((time - mCurrentTime) * mWidthPer5Minutes
                / (TIME_LINE_MINITUES * MINITUES) + mHalfW + mOffsetPos);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHalfW = (right - left) / 2;
        if (mNeedNextButton) {
            mLastDrawable.setRect(0, 3, mLastDrawbleWidth, bottom - top - mBottomPadding - 3);
            mNextDrawable.setRect(right - left - mLastDrawbleWidth, 3, right - left, bottom - top - mBottomPadding - 3);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        mGestureDetector.onTouchEvent(event);
        if (!mMediaPlayer) {
            boolean handled = false;
            for (ImageDrawable imageDrawable : mImageDrawables) {
                if (imageDrawable.onTouchEvent(event)) {
                    handled = true;
                }
            }
            if (handled) {
                invalidate();
                return true;
            }
        }
        mScaleGestureDetector.onTouchEvent(event);
        if (mOnscaleBegin) {
            if (event.getAction() == MotionEvent.ACTION_CANCEL
                    || event.getAction() == MotionEvent.ACTION_UP) {
                mOnscaleBegin = false;
                notifyUpdate();
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mIsPress = true;
            mHandler.removeMessages(MSG_NOTIFY_UPDATE);
            mTouchStartX = (int) event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mOffsetPos += (int) (event.getX() - mTouchStartX);
            long selectTime = getSelectTime();
            mCurrentTime = System.currentTimeMillis() + mOffsetCurrentTime;
            if (selectTime > mCurrentTime) {
                setJustPlayTime(System.currentTimeMillis());
                SDKLog.e(TAG, "only update currentTime");
            } else if (mCurrentTime - selectTime > 2000) {
                SDKLog.e(TAG, "onUpdateTime" + selectTime);
                if (mTimeLineCallback != null) {
                    mTimeLineCallback.onUpdateTime(getSelectTime());
                }
            }
            mTouchStartX = (int) event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                || event.getAction() == MotionEvent.ACTION_UP) {
//            if (mTimeLineCallback != null)
//                mTimeLineCallback.onUpdateTime(getSelectTime());
            mTouchStartX = 0;
            notifyUpdate();
        }
        invalidate();
        return true;
    }

    protected boolean getIsPress() {
        return mIsPress;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int width = getWidth();
        int height = getHeight() - mBottomPadding;
        int top = 0;
        int left = 0;
        mCurrentTime = System.currentTimeMillis() + mOffsetCurrentTime;

        //背景
        mTimelineBg.setBounds(left, top, width, height);
        mTimelineBg.draw(canvas);
        canvas.save();
        canvas.clipRect(new Rect(left + mLastDrawbleWidth, top, width - mLastDrawbleWidth, height + 1));

        ///时间轴
        int lastPos = -52;
        long starttime = getTime(lastPos);
        TimeDay timeDay = new TimeDay(starttime);
        timeDay.minute = (timeDay.minute / TIME_LINE_MINITUES) * TIME_LINE_MINITUES;
        timeDay.second = 0;
        timeDay.updateTimeInMillis();
        starttime = timeDay.millis;
        lastPos = getPos(timeDay.millis) + left;
        int hour = timeDay.hour + 1;
        int minute = timeDay.minute;
        int timeTextSize = 30;
        int timeTextBaseSize = timeTextSize / 3;
        int timeTextColor = 0xff808080;
        int timelineColor = 0xff808080;

        //录制时间
        int lastDrawPos = -20;
        long lastEndTime = 0;
        for (int i = 0; i < mTimeItems.size(); i++) {
            TimeItem timeItem = mTimeItems.get(i);
            int startPos = left + getPos(timeItem.startTime);
            long end = timeItem.startTime + timeItem.duration;
            if (mCurrentTime <= end) {
                end = mCurrentTime;
            }
            int endPos = getPos(end);
            if (startPos > width || endPos < 0) {
                continue;
            }
            if (endPos < lastDrawPos) {
                continue;
            }
            if (timeItem.startTime > lastEndTime && (timeItem.startTime - lastEndTime) < mLineLen) {
                startPos = getPos(lastEndTime);
            }
            if (startPos < lastDrawPos) {
                startPos = lastDrawPos;
            }
            if (timeItem.isSaveFile == 1) {
                mTimelineSaveSelBg.setBounds(startPos, top, endPos, height);
                mTimelineSaveSelBg.draw(canvas);
            } else if (timeItem.isMotion) {
                mTimelMotionBg.setBounds(startPos, top, endPos, height);
                mTimelMotionBg.draw(canvas);
            } else {
                mTimelineSelBg.setBounds(startPos, top, endPos, height);
                mTimelineSelBg.draw(canvas);
            }
//            if (timeItem.isSaveFile == 0) {
//                mTimelineSelBg.setBounds(startPos, top, endPos, height);
//                mTimelineSelBg.draw(canvas);
//            } else {
//                mTimelineSaveSelBg.setBounds(startPos, top, endPos, height);
//                mTimelineSaveSelBg.draw(canvas);
//            }
            lastDrawPos = endPos;
            lastEndTime = end;
        }

        //时间刻度
        mPaint.setTextSize(timeTextSize);
        mPaint.setStrokeWidth(1);
        while (lastPos < width + 50) {
            if (minute == 60) {
                mPaint.setColor(timelineColor);
                canvas.drawLine(lastPos, top + mTopH * 3, lastPos, top + height - mTopH * 2, mPaint);
                if (hour == 24) {
                    hour = 0;
                    minute = 0;
                }
                if (hour == 0 || hour == 23) {
                    mPaint.setColor(0xff808080);
                    canvas.drawText(TimeUtils.getData(starttime), lastPos + 5, top + timeTextSize + 20, mPaint);// hour
                }
                mPaint.setColor(timeTextColor);
                canvas.drawText("" + hour + ":00", lastPos + 10, top + height - mTopH * 2 - timeTextBaseSize, mPaint);// hour
                hour++;
                minute = 0;
            } else if (minute == 30) {
                mPaint.setColor(timelineColor);
                canvas.drawLine(lastPos, top + mTopH * 3, lastPos, top + height - mTopH * 2, mPaint);
                mPaint.setColor(timeTextColor);
                canvas.drawText("" + (hour - 1) + ":30", lastPos + 10, top + height - mTopH * 2 - timeTextBaseSize, mPaint);// hour
            } else {
                mPaint.setColor(timelineColor);
                canvas.drawLine(lastPos, top + mTopH * 9, lastPos, top + height - mTopH * 10, mPaint);
            }
            minute += TIME_LINE_MINITUES;
            starttime += TIME_LINE_MINITUES * MINITUES;
            lastPos += mWidthPer5Minutes;
        }

        //中间指针
        mTimelinePointer.setBounds(left + width / 2 - mTimeBarW / 2, top, left + width / 2 + mTimeBarW / 2, top + height + mBottomPadding);
        mTimelinePointer.draw(canvas);
        canvas.restore();
        if (!mMediaPlayer) {
            for (ImageDrawable imageDrawable : mImageDrawables) {
                imageDrawable.draw(canvas);
            }
        }
    }

    public static class TimeDay {
        public int day;
        public int hour;
        public int minute;
        public int month;
        public int second;
        public int year;
        public long millis;

        public TimeDay(long milliseconds) {
            updateTime(milliseconds);
        }

        public void updateTime(long milliseconds) {
            this.millis = milliseconds;
            GregorianCalendar startCal = new GregorianCalendar(TimeUtils.getTimeZone());
            startCal.setTimeInMillis(millis);
            this.year = startCal.get(Calendar.YEAR);
            this.month = (1 + startCal.get(Calendar.MONTH));
            this.day = startCal.get(Calendar.DAY_OF_MONTH);
            this.hour = startCal.get(Calendar.HOUR_OF_DAY);
            this.minute = startCal.get(Calendar.MINUTE);
            this.second = startCal.get(Calendar.SECOND);
        }

        public void updateTimeInMillis() {
            GregorianCalendar startCal = new GregorianCalendar(
                    TimeZone.getDefault());
            startCal.set(this.year, -1 + this.month, this.day, this.hour,
                    this.minute, this.second);
            millis = startCal.getTimeInMillis();
        }

        @Override
        public String toString() {
            return "TimeDay{" +
                    "day=" + day +
                    ", hour=" + hour +
                    ", minute=" + minute +
                    ", month=" + month +
                    ", second=" + second +
                    ", year=" + year +
                    ", millis=" + millis +
                    '}';
        }
    }

    private TimeItem getNeedItem(long time, boolean next) {
        TimeItem lastTime = null;
        if (next) {
            TimeItem recordItem = null;
            TimeItem lastItem;
            boolean skip = false;
            for (int i = 0, j = mTimeItems.size() - 1; i <= j; i++) {
                TimeItem timeItem = mTimeItems.get(i);
                if (!skip) {
                    recordItem = timeItem;
                }
                if (i < j) {
                    lastItem = mTimeItems.get(i + 1);
                    if ((lastItem.startTime - timeItem.endTime) <= mLineLen) {
                        skip = true;
                        continue;
                    }
                }
                if (i == j - 1 && skip) {
                    break;
                }
                skip = false;
                if (recordItem.startTime >= time) {
                    lastTime = recordItem;
                    break;
                }
            }
        } else {
            for (int i = mTimeItems.size() - 1, j = 0; i >= j; i--) {
                TimeItem timeItem = mTimeItems.get(i);
                if (i > 0) {
                    TimeItem lastItem = mTimeItems.get(i - 1);
                    if ((timeItem.startTime - lastItem.endTime) <= 4000) {
                        continue;
                    }
                }
                if (timeItem.startTime <= time) {
                    lastTime = timeItem;
                    break;
                }
            }
        }
        return lastTime;
    }
}
