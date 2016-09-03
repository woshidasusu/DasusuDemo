package com.sxq.whiteboarddemo.whiteboard.gesture;

/**
 * Created by sxq on 2016/7/8.
 */
public interface ScrollListener {
    void onViewScroll(float distanceX, float distanceY);
    void onShareScroll(float distanceX,float distanceY);
}
