package com.sxq.whiteboarddemo.whiteboard.gesture;

import android.util.Log;
import android.view.ScaleGestureDetector;

public class GestureScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "GestureScaleListener";

    /**
     * 委派任务的对象，该类干的活交由委派者具体实现
     */
    private ScaleListener delegate;

    public GestureScaleListener(ScaleListener delegate){
        this.delegate = delegate;
    }

    private float maxScale = 5.0f;
    private float minScale = 0.5f;

    private float scaleFactor = 1.0f;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float retScale = 1.0f;
        float tempScale = detector.getScaleFactor();
        if((scaleFactor * tempScale < maxScale) && (scaleFactor*tempScale > minScale)){
            scaleFactor *= tempScale;
            retScale = tempScale;
        }
        delegate.onScaleChange(scaleFactor);
        delegate.onSceleShare(scaleFactor);
        Log.d(TAG,"onScale:"+scaleFactor+"      "+retScale);

        //true:每次调用onScale时缩放倍数都是基于上一次调用onScale时的数据
        //false：缩放倍数基于第一次调用onScale时的数据
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //true:因为要自己处理该事件，所以需要将事件拦截下来，设置为true后，才会调用onScale()
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
