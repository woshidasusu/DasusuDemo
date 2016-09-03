package com.sxq.whiteboarddemo.whiteboard.share;

/**
 * Created by sxq on 2016/7/13.
 */
public class ScrollShare {
    private int type;
    private float distanceX;
    private float distanceY;

    public ScrollShare(float distanceX, float distanceY) {
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    public ScrollShare() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(float distanceX) {
        this.distanceX = distanceX;
    }

    public float getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(float distanceY) {
        this.distanceY = distanceY;
    }
}
