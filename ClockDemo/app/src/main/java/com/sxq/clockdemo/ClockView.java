package com.sxq.clockdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sxq.clockdemo.utils.SizeUtils;
import com.sxq.clockdemo.utils.TimeUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClockView extends View{

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private int mClockRadius;
    private Context mContext;
    private Paint mPaint;
    private int mClockCenter;
    private int mPadding;
    private int mClockSize;
    private int mScaleLength;
    private int mLongScaleLength;
    private int[] mHourPointer;
    private int[] mMinutePointer;
    private int[] mSecondPointer;

    private static final int DEFAULT_SIZE = 300;
    private static int DEFAULT_PADDING = 20;

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        //init paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        Log.d("size","init");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        Log.d("size","width: " + width + "  height: " + height);
        int size = Math.min(width,height);
        setMeasuredDimension(size,size);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("size","w: " + w + "  h: " + h);
        mClockSize = w;
        mClockCenter = w / 2 ;
        mClockRadius = w / 2 - SizeUtils.dp2px(mContext, DEFAULT_PADDING);

        mScaleLength = mClockRadius / 20;
        mLongScaleLength = mClockRadius / 10;

        mHourPointer = new int[]{6, mClockRadius * 4 / 10};
        mMinutePointer = new int[]{4, mClockRadius * 6 / 10};
        mSecondPointer = new int[]{2, mClockRadius * 8 / 10};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBounds(canvas);
        drawPointer(canvas);
        postInvalidateDelayed(100);
    }

    private void drawPointer(Canvas canvas) {
        canvas.save();

        canvas.translate(mClockCenter, mClockCenter);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mHourPointer[0] + mSecondPointer[0], mPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        int hour = new GregorianCalendar().get(Calendar.HOUR);
        int minute = TimeUtils.getCurTimeDate().getMinutes();
        int second = TimeUtils.getCurTimeDate().getSeconds();
        int millisecond = new GregorianCalendar().get(Calendar.MILLISECOND);

        //hour
        canvas.save();
        mPaint.setStrokeWidth(mHourPointer[0]);
        Log.d("!!!!","hour:"+(hour + minute /60f));
        canvas.rotate((hour + minute /60f) * 30);
        canvas.translate(0, mHourPointer[1]/5);
        canvas.drawLine(0, 0, 0, -mHourPointer[1], mPaint);
        canvas.restore();
        //minute
        canvas.save();
        mPaint.setStrokeWidth(mMinutePointer[0]);
        Log.d("!!!!","minute:"+(minute + second / 60f));
        canvas.rotate( (minute + second / 60f) * 6);
        canvas.translate(0, mMinutePointer[1]/5);
        canvas.drawLine(0, 0, 0, -mMinutePointer[1], mPaint);
        canvas.restore();
        //second
        canvas.save();
        mPaint.setStrokeWidth(mSecondPointer[0]);
        Log.d("!!!!","second:"+ (second + millisecond * 0.001f));
        canvas.rotate((second + millisecond * 0.001f) * 6);
        canvas.translate(0, mSecondPointer[1]/5);
        canvas.drawLine(0, 0, 0, -mSecondPointer[1], mPaint);
        canvas.restore();

        canvas.restore();
    }

    private void drawBounds(Canvas canvas) {
        canvas.save();
        canvas.translate(mClockCenter, mClockCenter);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, mClockRadius, mPaint);
        for (int i=0;i<60;i++){
            //draw scale
            mPaint.setStyle(Paint.Style.STROKE);
            int length = i%5==0 ? mLongScaleLength : mScaleLength;
            if (i%5 == 0){
                mPaint.setColor(Color.BLACK);
                canvas.drawLine(0, -mClockRadius, 0, -mClockRadius + length, mPaint);

                //draw text
                canvas.save();
                String text = (i/5==0 ? 12 : (i/5)) + "";
                Rect rect = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), rect);
                int textHeight = rect.bottom - rect.top;
                int textWidth = rect.right - rect.left;
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(SizeUtils.dp2px(mContext, 10));
                int textCenterY = -mClockRadius + mLongScaleLength + mClockRadius/15 + textHeight ;
                canvas.translate(0, textCenterY);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -textWidth/2 , textHeight/2 , mPaint);
                canvas.restore();
            } else {
                mPaint.setColor(Color.GRAY);
                canvas.drawLine(0, -mClockRadius, 0, -mClockRadius + length, mPaint);
            }
            canvas.rotate(6);
        }
        canvas.restore();
    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = Math.max(specSize, DEFAULT_SIZE);
        }else {
            result = DEFAULT_SIZE;
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(specSize, result);
            }
        }
        return result;
    }

}
