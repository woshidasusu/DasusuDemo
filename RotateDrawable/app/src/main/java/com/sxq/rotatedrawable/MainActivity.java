package com.sxq.rotatedrawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.BoolRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private static final int HANDBAR_PLAYINT = 10000;
    private static final int HANDBAR_STOP = 0;

    private ImageView imgCd;
    private ImageView imgHandbar;
    private ImageView btnPre;
    private ImageView btnPlay;
    private ImageView btnNext;
    private SeekBar seekBar;
    private TextView tvTime;
    private TextView tvTotalTime;

    boolean enablePlay = false;
    MediaPlayer mediaPlayer;
    AudioState audioState = AudioState.AUDIO_STOP;

    int position;
    int[] raws = {R.raw.demo01, R.raw.demo02};

    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        new MyThread().start();
        startTimer();

    }

    private void initView() {
        WindowManager wm = this.getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        imgCd = (ImageView) findViewById(R.id.img_cd);
        imgHandbar = (ImageView) findViewById(R.id.img_handbar);
        btnNext = (ImageView) findViewById(R.id.img_next);
        btnPlay = (ImageView) findViewById(R.id.img_play);
        btnPre = (ImageView) findViewById(R.id.img_pre);
        seekBar = (SeekBar) findViewById(R.id.seekbar_process);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTotalTime = (TextView) findViewById(R.id.tv_total_time);

        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTime.setText(getTime(progress));
                float tvProgress = (float) (((progress * 1.0) / seekBar.getMax()) * (seekBar.getWidth() - tvTime.getWidth()));
                Logger.d(String.valueOf(tvProgress));
                tvTime.setTranslationX(tvProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_next:
                audioNext();
                break;

            case R.id.img_play:
                if (audioState == AudioState.AUDIO_PLAYING) {
                    audioPause();
                } else {
                    audioPlay();
                }
                break;

            case R.id.img_pre:
                audioPre();
                break;

            default:
                break;
        }
    }

    public void mediaPrepare() {
        imgCd.getDrawable().setLevel(0);
        // 等效于new + prepare
        mediaPlayer = MediaPlayer.create(this, raws[position]);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    /**
     * 下一首
     */
    public void audioNext() {
        position++;
        if (position >= raws.length) {
            position -= raws.length;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            setAudioState(AudioState.AUDIO_STOP);
        }
        mediaPrepare();
    }

    /**
     * 上一首
     */
    public void audioPre() {
        position--;
        if (position < 0) {
            position = raws.length - 1;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            setAudioState(AudioState.AUDIO_STOP);
        }
        mediaPrepare();
    }

    /**
     * 播放音乐
     */
    public void audioPlay() {
        if (mediaPlayer == null) {
            mediaPrepare();
        } else {
            setAudioState(AudioState.AUDIO_PLAYING);
            seekBar.setMax(mediaPlayer.getDuration());
            tvTotalTime.setText(getTime(mediaPlayer.getDuration()));
            mediaPlayer.start();
        }
    }

    /**
     * 暂停音乐
     */
    public void audioPause() {
        mediaPlayer.pause();
        setAudioState(AudioState.AUDIO_PAUSE);
    }

    /**
     * 播放杠动画
     */
    public void animHandbar(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                imgHandbar.getDrawable().setLevel(level);
            }
        });
        animator.setDuration(500);
        animator.start();
    }


    public void setAudioState(AudioState state) {
        this.audioState = state;
        if (state == AudioState.AUDIO_PLAYING) {
            enablePlay = true;
            btnPlay.setImageResource(R.drawable.select_pause);
            animHandbar(HANDBAR_STOP, HANDBAR_PLAYINT);
        } else {
            enablePlay = false;
            btnPlay.setImageResource(R.drawable.select_play);
            if (state != AudioState.AUDIO_PREPARE) {
                animHandbar(HANDBAR_PLAYINT, HANDBAR_STOP);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        setAudioState(AudioState.AUDIO_STOP);
        audioNext();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        setAudioState(AudioState.AUDIO_PREPARE);
        audioPlay();
    }

    public enum AudioState {
        AUDIO_PLAYING,
        AUDIO_PAUSE,
        AUDIO_PREPARE,
        AUDIO_STOP
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                while (true) {
                    Thread.sleep(60);
                    if (enablePlay) {
                        Message msg = Message.obtain();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int level = imgCd.getDrawable().getLevel();
                    level += 200;
                    if (level > 10000) {
                        level -= 10000;
                    }
                    imgCd.getDrawable().setLevel(level);
                    break;

                case 1:
                    if (enablePlay){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                default:
                    break;
            }
        }
    };

    public String getTime(int milliseconds) {
        int minute = 0;
        int second = 0;
        second = ((milliseconds / 1000) % 60);
        minute = ((milliseconds / 1000) / 60);
        return ((minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second));
    }

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }, 0, 1000);
    }
}
