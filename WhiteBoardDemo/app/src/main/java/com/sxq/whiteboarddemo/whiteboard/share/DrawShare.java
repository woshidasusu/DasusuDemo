package com.sxq.whiteboarddemo.whiteboard.share;

/**
 * Created by sxq on 2016/7/13.
 */
public class DrawShare {
    private int eventAction;
    private float x;
    private float y;

    public DrawShare() {
    }

    public DrawShare(int eventAction, float x, float y) {
        this.eventAction = eventAction;
        this.x = x;
        this.y = y;
    }

    public int getEventAction() {
        return eventAction;
    }

    public void setEventAction(int eventAction) {
        this.eventAction = eventAction;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
