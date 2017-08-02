package com.dasu.tvhomedemo.ui.base;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by suxq on 2017/8/1.
 */

public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private View mPreFocus = null;
    //边界抖动
    private boolean isShakeWhenReachBoard = true;

    public void setShakeWhenReachBoard(boolean enable) {
        isShakeWhenReachBoard = enable;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!isShakeWhenReachBoard) {
            return super.dispatchKeyEvent(event);
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mPreFocus = getCurrentFocus();
        } else {
            if (mPreFocus != null && mPreFocus == getCurrentFocus()) {
                ObjectAnimator animator = null;
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        animator = ObjectAnimator.ofFloat(getCurrentFocus(), "translationY",
                                -5f,0f,5f,0f,-5f,0f,5f,0f,-5f,0f);
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        animator = ObjectAnimator.ofFloat(getCurrentFocus(), "translationX",
                                -5f,0f,5f,0f,-5f,0f,5f,0f,-5f,0f);
                        break;
                }
                if (animator != null) {
                    animator.setDuration(200);
                    animator.start();
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
