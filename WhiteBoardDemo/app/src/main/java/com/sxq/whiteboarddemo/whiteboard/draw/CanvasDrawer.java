package com.sxq.whiteboarddemo.whiteboard.draw;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by sxq on 2016/7/9.
 */
public class CanvasDrawer {

    private static final String TAG = "CanvasDrawer";

    private float scaleFactor = 1.0f;
    private float viewOffsetX = 0.0f;
    private float viewOffsetY = 0.0f;

    public CanvasDrawer(){

    }

    public void onDraw(Canvas canvas, Paint curPaint){
        canvas.translate(viewOffsetX,viewOffsetY);
        canvas.scale(scaleFactor,scaleFactor);
    }

    public void onScaleChange(float scaleFactor){
        this.scaleFactor = scaleFactor;
    }

    public void onViewScroll(float distanceX,float distanceY){
        this.viewOffsetX += distanceX;
        this.viewOffsetY += distanceY;
    }
}
