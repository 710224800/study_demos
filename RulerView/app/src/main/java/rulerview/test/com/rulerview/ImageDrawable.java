package rulerview.test.com.rulerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

public class ImageDrawable {
    Rect mRect = new Rect();
    Drawable mNormalDrawable;
    Drawable mPressedDrawable;
    boolean mPressed;

    public interface OnTouchListener {
        void onActionDown();

        void onActionUp();
    }

    OnTouchListener mOnTouchListener;

    public void setOnTouchListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    public ImageDrawable(Drawable normalDrawable, Drawable pressedDrawable) {
        mNormalDrawable = normalDrawable;
        mPressedDrawable = pressedDrawable;
    }

    public void setRect(int left, int top, int right, int bottom) {
        mRect.set(left, top, right, bottom);
    }

    public void actionCancel() {
        if (mPressed) {
            mPressed = false;
            if (mOnTouchListener != null) {
                mOnTouchListener.onActionUp();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (mRect.contains(x, y)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mPressed = true;
                if (mOnTouchListener != null) {
                    mOnTouchListener.onActionDown();
                }
                return true;
            } else {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) && mPressed) {
                    actionCancel();
                    return true;
                }
                return mPressed;
            }
        } else {
            if (mPressed) {
                actionCancel();
                return true;
            } else {
                return false;
            }
        }
    }

    public void draw(Canvas canvas) {
        Drawable drawable = mNormalDrawable;
        if (mPressed && mPressedDrawable != null) {
            drawable = mPressedDrawable;
        }
        if (drawable != null) {
            drawable.setBounds(mRect);
            drawable.draw(canvas);
        }
    }
}
