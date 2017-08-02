package com.dasu.tvhomedemo.ui.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AnimationSet;

/**
 * Created by suxq on 2017/8/2.
 */

public class ScaleUtil {

    public static final float DEFAULT_SCALE_UP_FACTOR = 1.1f;
    public static final float DEFAULT_SCALE_DOWM_FACTOR = 1.0f;

    public static void scaleUp(View view) {
        scaleUp(view, DEFAULT_SCALE_UP_FACTOR);
    }

    public static void scaleDown(View view) {
        scaleDown(view, DEFAULT_SCALE_DOWM_FACTOR);
    }

    public static void scaleUp(View view, float factor) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", factor);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", factor);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(300);
        set.start();
    }

    public static void scaleDown(View view, float factor) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", factor);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", factor);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(300);
        set.start();
    }
}
