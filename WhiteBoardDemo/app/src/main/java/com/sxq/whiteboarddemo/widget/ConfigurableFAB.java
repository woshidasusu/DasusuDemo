package com.sxq.whiteboarddemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class ConfigurableFAB  extends ImageButton implements View.OnTouchListener {

    private static final String TAG = "ConfigurableFAB";

    public ConfigurableFAB(Context context) {
        super(context);
        init();
    }

    public ConfigurableFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConfigurableFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public ConfigurableFAB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Paint paint;

    public void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        setOnTouchListener(this);
    }

    public static final int FAB_STATUS_NORMAL = 1;
    public static final int FAB_STATUS_SELECTED = 2;
    public static final int FAB_STATUS_CONFIGE = 3;

    private int mFaBstatus = FAB_STATUS_NORMAL;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void changeStatus() {
        if (mFaBstatus == FAB_STATUS_SELECTED){
            mFaBstatus = FAB_STATUS_CONFIGE;
            Log.d(TAG,"config");
        }else if (mFaBstatus == FAB_STATUS_CONFIGE){
            mFaBstatus = FAB_STATUS_SELECTED;
            Log.d(TAG,"select");

        }
    }



    private onClickStateListener mlistener;

    public void setOnClickStateListener(onClickStateListener listener){
        mlistener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG,"onTouch");
        return false;
    }

    public interface onClickStateListener{
        void onStateChange(View v,int state);
    }
}
