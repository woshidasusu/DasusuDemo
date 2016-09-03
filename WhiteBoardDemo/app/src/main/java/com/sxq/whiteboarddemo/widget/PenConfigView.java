package com.sxq.whiteboarddemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sxq on 2016/7/11.
 */
public class PenConfigView extends View implements View.OnTouchListener {
    public PenConfigView(Context context) {
        super(context);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PenConfigView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PenConfigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PenConfigView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint wbPaint;
    private Paint viewPaint;
    private float curPaintWidth = 3.0f;

    private void init() {
        wbPaint = new Paint();
        wbPaint.setAntiAlias(true);
        wbPaint.setStrokeWidth(4);

        viewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = MotionEventCompat.getX(event, 0);
        float y = MotionEventCompat.getY(event, 0);

        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                int[] area = getTouchArea(x,y,1,3);
                if (area[1] == 0){
                    Log.d("!!!!","sub");
                    curPaintWidth = Math.max(curPaintWidth-5,3);
                }else if (area[1] == 1){
                    Log.d("!!!!","size");
                }else if (area[1] == 2){
                    Log.d("!!!!","add");
                    curPaintWidth = Math.min(curPaintWidth+5,48);
                }
                break;

            default:
                break;
        }
        postInvalidate();
        return true;
    }

    /**
     * 在{@code horizontal}行{@code vertial}列的区域里，返回坐标(x,y)在哪里区域内
     */
    public int[] getTouchArea(float x, float y, int horizontal, int vertial) {
        int[] location = new int[2];
        location[0] = (int) (y / (getHeight() / horizontal));
        location[1] = (int) (x / (getWidth() / vertial));
        return location;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSub(canvas, getWidth() / 6, getHeight() / 2);
        drawCir(canvas, (3 * getWidth()) / 6, getHeight() / 2);
        drawAdd(canvas, (5 * getWidth()) / 6, getHeight() / 2);
    }

    /**
     * 画加号
     */
    public void drawAdd(Canvas canvas, int centerX, int centerY) {
        viewPaint.setColor(Color.GRAY);
        viewPaint.setStyle(Paint.Style.STROKE);
        viewPaint.setStrokeWidth(3);
        canvas.drawLine(centerX, centerY - 22, centerX, centerY + 22, viewPaint);
        canvas.drawLine(centerX - 22, centerY, centerX + 22, centerY, viewPaint);
    }

    /**
     * 画减号
     */
    public void drawSub(Canvas canvas, int centerX, int centerY) {
        viewPaint.setColor(Color.GRAY);
        viewPaint.setStyle(Paint.Style.STROKE);
        viewPaint.setStrokeWidth(3);
        canvas.drawLine(centerX - 22, centerY, centerX + 22, centerY, viewPaint);
    }

    public void drawCir(Canvas canvas, int centerX, int centerY) {
        wbPaint.setColor(Color.BLACK);
        wbPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY,curPaintWidth, wbPaint);
    }

    public void drawBound(Canvas canvas){
        viewPaint.setColor(Color.GRAY);
        canvas.drawRect(new RectF(getLeft(),getTop()-getHeight(),getRight(),getBottom()-getHeight()),viewPaint);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    private int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            int size = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
            return size;
        }
    }

    private int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            int size = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                size = Math.min(size, specSize);
            }
            return size;
        }
    }
}
