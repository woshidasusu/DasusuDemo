package com.sxq.whiteboarddemo.whiteboard;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 记录每一次的绘画操作
 */
public class DrawHistory {

    private Path mPath;
    private Paint mPaint;

    public DrawHistory(Path mPath, Paint mPaint) {
        this.mPath = mPath;
        this.mPaint = mPaint;
    }

    public Path getmPath() {
        return mPath;
    }

    public void setmPath(Path mPath) {
        this.mPath = mPath;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }
}
