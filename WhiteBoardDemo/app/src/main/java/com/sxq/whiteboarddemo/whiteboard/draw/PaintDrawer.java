package com.sxq.whiteboarddemo.whiteboard.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.sxq.whiteboarddemo.whiteboard.DrawHistory;

import java.util.List;

/**
 * Created by sxq on 2016/7/9.
 */
public class PaintDrawer {

    private Paint curPaint;

    public PaintDrawer(){
        init();
    }

    private void init() {
        curPaint = new Paint();
        curPaint.setColor(Color.BLACK);
        curPaint.setStyle(Paint.Style.STROKE);  //设置风格，描边
        curPaint.setAntiAlias(true);            //设置防锯齿，但会耗性能
        curPaint.setDither(true);               //设置防抖动
        curPaint.setStrokeCap(Paint.Cap.ROUND); //当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式    Cap.ROUND,或方形样式Cap.SQUARE
        curPaint.setStrokeWidth(10);
        curPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void onDraw(Canvas canvas, Path curPath, List<DrawHistory> pathList){
        drawPaths(canvas,pathList);
        if (curPath != null){
            canvas.drawPath(curPath,curPaint);
        }
    }

    public void drawPaths(Canvas canvas,List<DrawHistory> pathList){
        for (DrawHistory draw:pathList){
            canvas.drawPath(draw.getmPath(),draw.getmPaint());
        }
    }

    public Paint getCurPaint(){
        return curPaint;
    }
}
