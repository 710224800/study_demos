package myview.test.com.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luyanhao on 2019/4/10.
 */
public class MyView extends View {
    private int mWidth;
    private int mHeight;
    // 画笔
    private Paint mPaint = new Paint();
    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    Path path = new Path();
    Path src = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        // 画坐标系
        mPaint.setColor(Color.RED);
        canvas.drawLine(0.0f, mHeight >> 1, mWidth, mHeight >> 1, mPaint);
        canvas.drawLine(mWidth >> 1, 0, mWidth >> 1, mHeight, mPaint);

        // 画内容
        canvas.translate(mWidth >> 1, mHeight >> 1);  // 移动坐标系到屏幕中心
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴

        path.reset();
        src.reset();
//
        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);

        path.addPath(src,0,200);

        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
        canvas.drawPath(path,mPaint);
//        path.lineTo(100,100);
//
//        RectF oval = new RectF(0,0,300,300);
//
//        path.arcTo(oval,0,270);
//// path.arcTo(oval,0,270,false);             // <-- 和上面一句作用等价
//
//        canvas.drawPath(path,mPaint);


    }
}
