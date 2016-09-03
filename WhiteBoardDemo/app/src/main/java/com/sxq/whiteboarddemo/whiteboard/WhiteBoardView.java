package com.sxq.whiteboarddemo.whiteboard;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.google.gson.Gson;
import com.sxq.whiteboarddemo.Apps;
import com.sxq.whiteboarddemo.R;
import com.sxq.whiteboarddemo.whiteboard.draw.CanvasDrawer;
import com.sxq.whiteboarddemo.whiteboard.draw.PaintDrawer;
import com.sxq.whiteboarddemo.whiteboard.gesture.GestureScaleListener;
import com.sxq.whiteboarddemo.whiteboard.gesture.GestureScrollListener;
import com.sxq.whiteboarddemo.whiteboard.gesture.ScaleListener;
import com.sxq.whiteboarddemo.whiteboard.gesture.ScrollListener;
import com.sxq.whiteboarddemo.whiteboard.share.DrawShare;
import com.sxq.whiteboarddemo.whiteboard.share.ScaleShare;
import com.sxq.whiteboarddemo.whiteboard.share.ScrollShare;
import com.sxq.whiteboarddemo.whiteboard.share.ShareConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WhiteBoardView extends View implements View.OnTouchListener, ScaleListener, ScrollListener {

    private static final String TAG = "WhiteBoardView";

    public WhiteBoardView(Context context) {
        super(context);
        init();
    }

    public WhiteBoardView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public WhiteBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public WhiteBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private ScaleGestureDetector scaleDetector;
    private GestureDetector scrollDetector;

    private CanvasDrawer canvasDrawer;
    private PaintDrawer paintDrawer;
    private Paint curPaint;
    private Path curPath;

    private RectF viewRect;
    private RectF canvasRect;
    private List<DrawHistory> historySaveList;
    private List<DrawHistory> historyDeleteList;

    private float scaleFactor = 1.0f;
    private boolean isEraserSelected = false;
    private boolean isEnable = true;
    private boolean isShareWhiteboard = false;

    private void init() {
        canvasDrawer = new CanvasDrawer();
        paintDrawer = new PaintDrawer();
        curPaint = paintDrawer.getCurPaint();

        scaleDetector = new ScaleGestureDetector(Apps.getAppContext(),new GestureScaleListener(this));
        scrollDetector = new GestureDetector(Apps.getAppContext(),new GestureScrollListener(this));

        historySaveList = new ArrayList<>();
        historyDeleteList = new ArrayList<>();

        viewRect = new RectF();
        canvasRect = new RectF();

        setOnTouchListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewRect.set(0,0,w,h);
        canvasRect.set(0,0,w,h);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasDrawer.onDraw(canvas,curPaint);
        paintDrawer.onDraw(canvas,curPath,historySaveList);
    }

    float startX,startY;
    Gson gson = new Gson();
    DrawShare drawShare;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEventCompat.getPointerCount(event) == 2){
            scaleDetector.onTouchEvent(event);
            scrollDetector.onTouchEvent(event);
        }

        if (isEnable){
            float x = (MotionEventCompat.getX(event,0) - viewRect.left) / scaleFactor;
            float y = (MotionEventCompat.getY(event,0) - viewRect.top) / scaleFactor;

            if (isShareWhiteboard){
                drawShare = new DrawShare(MotionEventCompat.getActionMasked(event),x,y);
                linstener.onShare(ShareConstant.SHARE_TYPE_OPERATION_DRAW+gson.toJson(drawShare));
            }
            actionDraw(MotionEventCompat.getActionMasked(event),x,y);
        }else {
            scrollDetector.onTouchEvent(event);
            invalidate();
        }
        return true;
    }

    public void actionDraw(int actionMasked, float x, float y) {
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                actionDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove(x,y);
                break;

            case MotionEvent.ACTION_UP:
                actionUp(x,y);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                actionPointerDown(x,y);
                break;

            default:
                break;
        }
        postInvalidate();
    }

    private void actionDown(float x, float y) {
        startX = x;
        startY = y;
        curPath = new Path();
        curPath.moveTo(startX, startY);
    }

    private void actionMove(float x, float y) {
        if (curPath != null) {
            curPath.quadTo(startX, startY, x, y);
            historyDeleteList.clear();
        }
        startX = x;
        startY = y;
    }

    private void actionUp(float x, float y) {
        if (curPath != null) {
            curPath.lineTo(x, y);
            DrawHistory tempDraw;
            if (isEraserSelected){
                Log.d(TAG,"save eraser");
                Paint eraserPaint = new Paint();
                eraserPaint.setStrokeWidth(30f);
                eraserPaint.setAlpha(0);
                eraserPaint.setAntiAlias(false);
                eraserPaint.setColor(getResources().getColor(R.color.white));
                tempDraw = new DrawHistory(curPath,eraserPaint);

            }else {
                Log.d(TAG,"save Paint");
                tempDraw = new DrawHistory(curPath, new Paint(curPaint));
            }
            historySaveList.add(tempDraw);
        }
        curPath = null;
    }

    private void actionPointerDown(float x, float y) {
        curPath = null;
    }

    public void undo(){
        if (!historySaveList.isEmpty()){
            DrawHistory delHistory = historySaveList.get(historySaveList.size()-1);
            historySaveList.remove(delHistory);
            historyDeleteList.add(delHistory);
            invalidate();
        } else if (!historyDeleteList.isEmpty()){
            //撤销全部清除的操作
            historySaveList.addAll(historyDeleteList);
            historyDeleteList.clear();
            invalidate();
        }
    }

    public void redo(){
        if (!historyDeleteList.isEmpty()){
            DrawHistory storeHistory = historyDeleteList.get(historyDeleteList.size()-1);
            historySaveList.add(storeHistory);
            historyDeleteList.remove(storeHistory);
            invalidate();
        }
    }

    public void selectedEraser(){
        isEraserSelected = true;
    }

    public void selectedPen(){
        isEraserSelected = false;
    }

    public void addPenWidth(){
        if (curPaint != null){
            curPaint.setStrokeWidth(curPaint.getStrokeWidth() * 1.5f);
        }
    }

    public void reducePenWidth(){
        if (curPaint != null){
            curPaint.setStrokeWidth(curPaint.getStrokeWidth() - curPaint.getStrokeWidth() * 0.5f);
        }
    }

    public void clearContent(){
        if (historySaveList != null){
            historyDeleteList.addAll(historySaveList);
            historySaveList.clear();
            postInvalidate();
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isShareWhiteboard() {
        return isShareWhiteboard;
    }

    public void setShareWhiteboard(boolean shareWhiteboard) {
        isShareWhiteboard = shareWhiteboard;
    }

    @Override
    public void onScaleChange(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        canvasDrawer.onScaleChange(scaleFactor);
    }

    public void onScaleChange(ScaleShare scaleShare){
        onScaleChange(scaleShare.getScaleFactor());
        postInvalidate();
    }

    @Override
    public void onSceleShare(float scaleFactor) {
        if (isShareWhiteboard){
            ScaleShare scaleShare = new ScaleShare(scaleFactor);
            linstener.onShare(ShareConstant.SHARE_TYPE_OPERATION_SCALE+gson.toJson(scaleShare));
        }
    }

    @Override
    public void onViewScroll(float distanceX, float distanceY) {
        viewRect.left += distanceX;
        viewRect.top += distanceY;
        viewRect.right += distanceX;
        viewRect.bottom += distanceY;

        canvasDrawer.onViewScroll(distanceX,distanceY);
    }

    public void onViewScroll(ScrollShare scrollShare){
        onViewScroll(scrollShare.getDistanceX(),scrollShare.getDistanceY());
        postInvalidate();
    }

    @Override
    public void onShareScroll(float distanceX, float distanceY) {
        if (isShareWhiteboard){
            ScrollShare scrollShare = new ScrollShare(distanceX,distanceY);
            linstener.onShare(ShareConstant.SHARE_TYPE_OPERATION_SCROLL+gson.toJson(scrollShare));
        }
    }

    private OnShareLinstener linstener;

    public void setShareListener(OnShareLinstener listener){
        this.linstener = listener;
    }

    public interface OnShareLinstener{
        void onShare(String content);
    }
}
