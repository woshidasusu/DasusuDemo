package com.sxq.whiteboarddemo.whiteboard.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by sxq on 2016/7/8.
 */
public class GestureScrollListener implements GestureDetector.OnGestureListener {
    private static final String TAG = "GestureScrollListener";

    private ScrollListener delegate;

    public GestureScrollListener(ScrollListener delegate){
        this.delegate = delegate;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp");
        return false;
    }

    private float maxOffset = 1 * 1920;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    /**
     *
     * @param distanceX 相邻两次调用onScroll的X偏移量,X1-X2,X1为第一次调用onScroll
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        distanceX = Math.abs(offsetX+distanceX)>maxOffset ? 0.0f : distanceX;
        distanceY = Math.abs(offsetY+distanceY)>maxOffset ? 0.0f : distanceY;
        offsetX+=distanceX;
        offsetY+=distanceY;
        Log.d(TAG,"offset:"+offsetX+"   "+offsetY);
        delegate.onViewScroll(-distanceX,-distanceY);
        delegate.onShareScroll(-distanceX,-distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG,"onFling");
        return false;
    }
}
