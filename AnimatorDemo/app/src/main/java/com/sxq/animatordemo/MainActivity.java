package com.sxq.animatordemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        downUpIcon2.performClick();
    }

    @InjectView(R.id.img_home1) ImageView homeIcon1;
    @InjectView(R.id.img_home2) ImageView homeIcon2;
    @InjectView(R.id.img_refresh1) ImageView refreshIcon1;
    @InjectView(R.id.img_refresh2) ImageView refreshIcon2;
    @InjectView(R.id.img_clear1) ImageView clearIcon1;
    @InjectView(R.id.img_clear2) ImageView clearIcon2;
    @InjectView(R.id.img_back1) ImageView backIcon1;
    @InjectView(R.id.img_back2) ImageView backIcon2;
    @InjectView(R.id.img_next1) ImageView nextIcon1;
    @InjectView(R.id.img_next2) ImageView nextIcon2;
    @InjectView(R.id.img_down_up1) ImageView downUpIcon1;
    @InjectView(R.id.img_down_up2) ImageView downUpIcon2;

    private boolean isIconHided1 = false;
    private boolean isIconHided2 = false;
    private boolean isIconCalculate = false;

    private static final int VERTIAL_ANIM = 1;
    private static final int ROUND_ANIM = 2;

    @OnClick(R.id.img_down_up1)
    public void mDownUpClick(){
        if (isIconHided1){
            //show icons
            showIcons(VERTIAL_ANIM);
            downUpIcon1.setImageResource(R.drawable.ic_arrow_drop_down_white_48dp);
        }else {
            //hide icons
            hideIcons(VERTIAL_ANIM);
            downUpIcon1.setImageResource(R.drawable.ic_arrow_drop_up_white_48dp);
        }
        isIconHided1 = !isIconHided1;
    }

    @OnClick(R.id.img_down_up2)
    public void mDownUpClick2(){
        if (isIconHided2){
            //show icons
            showIcons(ROUND_ANIM);
            downUpIcon2.setImageResource(R.drawable.ic_arrow_drop_down_white_48dp);
        }else {
            //hide icons
            hideIcons(ROUND_ANIM);
            downUpIcon2.setImageResource(R.drawable.ic_arrow_drop_up_white_48dp);
        }
        isIconHided2 = !isIconHided2;
    }

    private void showIcons(final int anim){
        if (anim == VERTIAL_ANIM){
            verialShowAnim(homeIcon1);
            verialShowAnim(refreshIcon1);
            verialShowAnim(clearIcon1);
            verialShowAnim(backIcon1);
            verialShowAnim(nextIcon1);

        }else if (anim == ROUND_ANIM){
            //以半径为300的圆形区域扩展
            float radius = 300f;
            float offset = radius / (float)Math.sqrt(2);
            ObjectAnimator animator = ObjectAnimator.ofFloat(homeIcon2,"translationX",-radius);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(refreshIcon2,"translationX",-offset);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(refreshIcon2,"translationY",-offset);
            ObjectAnimator animator3 = ObjectAnimator.ofFloat(clearIcon2,"translationY",-radius);
            ObjectAnimator animator4 = ObjectAnimator.ofFloat(backIcon2,"translationX",offset);
            ObjectAnimator animator5 = ObjectAnimator.ofFloat(backIcon2,"translationY",-offset);
            ObjectAnimator animator6 = ObjectAnimator.ofFloat(nextIcon2,"translationX",radius);

            roundShowAima(homeIcon2);
            roundShowAima(clearIcon2);
            roundShowAima(refreshIcon2);
            roundShowAima(backIcon2);
            roundShowAima(nextIcon2);

            AnimatorSet set = new AnimatorSet();
            set.setDuration(1000);
            set.play(animator).with(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6);
            set.start();
        }
    }

    private void hideIcons(int anim){
        if (anim == VERTIAL_ANIM){
            //竖直收缩动画需要计算icon要平移的距离
            if (!isIconCalculate){
                //将计算出来的竖直距离存起来，方便设置动画时使用
                homeIcon1.setTag(calculateWidgetsDistance(homeIcon1,downUpIcon1)[1]);
                refreshIcon1.setTag(calculateWidgetsDistance(refreshIcon1,downUpIcon1)[1]);
                clearIcon1.setTag(calculateWidgetsDistance(clearIcon1,downUpIcon1)[1]);
                backIcon1.setTag(calculateWidgetsDistance(backIcon1,downUpIcon1)[1]);
                nextIcon1.setTag(calculateWidgetsDistance(nextIcon1,downUpIcon1)[1]);
                isIconCalculate = true;
            }
            //使用ValueAnimator,为了了解它的更多用法，这里将用多种方式来达到动画效果
            ValueAnimator animator = ValueAnimator.ofFloat(0.0f,1.0f);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    //竖直平移动画
                    float transY;
                    transY = ((int) homeIcon1.getTag()) * value;
                    homeIcon1.setTranslationY(transY);
                    transY = ((int) refreshIcon1.getTag()) * value;
                    refreshIcon1.setTranslationY(transY);
                    transY = ((int) clearIcon1.getTag()) * value;
                    clearIcon1.setTranslationY(transY);
                    transY = ((int) backIcon1.getTag()) * value;
                    backIcon1.setTranslationY(transY);
                }
            });
            ValueAnimator animator1 = ValueAnimator.ofFloat(0.0f,((int)nextIcon1.getTag()));
            animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    nextIcon1.setTranslationY(value);
                }
            });
            //从不透明到完全透明
            ValueAnimator animator2 = ValueAnimator.ofFloat(1.0f,0.0f);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    homeIcon1.setAlpha(value);
                    refreshIcon1.setAlpha(value);
                    clearIcon1.setAlpha(value);
                    backIcon1.setAlpha(value);
                    nextIcon1.setAlpha(value);
                }
            });
            animator.setDuration(1000);
            animator1.setDuration(1000);
            animator2.setDuration(200);

            AnimatorSet set = new AnimatorSet();
            set.play(animator).with(animator1).before(animator2);
            set.start();
        }else if (anim == ROUND_ANIM){
            roundHideAnim(homeIcon2);
            roundHideAnim(refreshIcon2);
            roundHideAnim(clearIcon2);
            roundHideAnim(backIcon2);
            roundHideAnim(nextIcon2);
        }
    }

    private void verialShowAnim(View view){
        //0,表示view的Y坐标从当前位置平移到最初最初的位置
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationY",0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,"alpha",1);
        animator.setDuration(1000);
        animator1.setDuration(1000);
        animator.start();
        animator1.start();
        View view1;
    }

    private void roundHideAnim(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationX",0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view,"translationY",0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"alpha",0);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.play(animator).with(animator1).with(animator2);
        set.start();
    }

    private void roundShowAima(View view){
        ObjectAnimator animator7 = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.round_show);
        animator7.setTarget(view);
        animator7.start();
    }

    /**
     * 计算两个view的距离
     * @param v1
     * @param v2
     * @return 返回new int[2], [0]横坐标距离，[1]纵坐标的距离
     */
    private int[] calculateWidgetsDistance(View v1, View v2){
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        int[] ret = new int[2];

        v1.getLocationOnScreen(location1);
        v2.getLocationOnScreen(location2);

        ret[0] = Math.abs(location1[0] - location2[0]);
        ret[1] = Math.abs(location1[1] - location2[1]);
        return ret;
    }

}
