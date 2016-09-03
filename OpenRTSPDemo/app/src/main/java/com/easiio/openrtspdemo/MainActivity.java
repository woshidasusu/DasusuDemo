package com.easiio.openrtspdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.FileObserver;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.easiio.openrtspdemo.adapter.ContentFragmentPagerAdapter;
import com.easiio.openrtspdemo.base.BaseFragmentActivity;
import com.easiio.openrtspdemo.inter.OnBackPressed;
import com.easiio.openrtspdemo.rtsp.RtspRecordFragment;
import com.easiio.openrtspdemo.utils.FileUtils;
import com.easiio.openrtspdemo.video.VideoFragment;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements VideoFragment.onFileCreateListener {

    private final String TAG = this.getClass().getSimpleName();

    private ViewPager contentViewPager;
    private ViewGroup bottomBtnRecord;
    private ViewGroup bottomBtnViedo;
    private ViewGroup layoutNetworkState;
    private BadgeView badgeView;
    private TextView tipView;

    private Bundle mSavedInstanceState;
    private int currentPage;

    private RtspRecordFragment rtspRecordFragment;
    private VideoFragment videoFragment;


    public static final int PAGE_RECORD = 0;
    public static final int PAGE_VIDEO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        bindView();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void bindView() {
        contentViewPager = (ViewPager) findViewById(R.id.layout_content);
        bottomBtnRecord = (ViewGroup) findViewById(R.id.bottom_btn_record);
        bottomBtnViedo = (ViewGroup) findViewById(R.id.bottom_btn_video);
        layoutNetworkState = (ViewGroup) findViewById(R.id.layout_net_state_notify);
        tipView = (TextView) findViewById(R.id.tip_view);
        badgeView = new BadgeView(MainActivity.this);
    }

    private void initView() {
        bottomBtnRecord.setOnClickListener(bottomBtnClickListener);
        bottomBtnViedo.setOnClickListener(bottomBtnClickListener);

        initContentViewPager();

        badgeView.setTargetView(tipView);
    }

    private void initContentViewPager() {
        if (mSavedInstanceState != null){

        }
        if (rtspRecordFragment == null){
            rtspRecordFragment = new RtspRecordFragment();
        }
        if (videoFragment == null){
            videoFragment = new VideoFragment();
        }

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(rtspRecordFragment);
        fragmentList.add(videoFragment);

        contentViewPager.setAdapter(new ContentFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
        contentViewPager.addOnPageChangeListener(mPageChangeListener);
        contentViewPager.setCurrentItem(PAGE_RECORD);
        currentPage = PAGE_RECORD;
        bottomBtnRecord.setSelected(true);
    }

    ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            bottomBtnRecord.setSelected(false);
            bottomBtnViedo.setSelected(false);
            switch (position){
                case PAGE_RECORD:
                    bottomBtnRecord.setSelected(true);
                    break;
                case PAGE_VIDEO:
                    bottomBtnViedo.setSelected(true);
                    break;
                default:
                    break;
            }
            currentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    View.OnClickListener bottomBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bottom_btn_record:
                    if (currentPage == PAGE_RECORD){
                        return;
                    }
                    contentViewPager.setCurrentItem(PAGE_RECORD,false);
                    break;
                case R.id.bottom_btn_video:
                    badgeView.setBadgeCount(0);
                    if (currentPage == PAGE_VIDEO){
                        return;
                    }
                    contentViewPager.setCurrentItem(PAGE_VIDEO,false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        boolean isIntercept = false;
        switch (currentPage){
            case PAGE_RECORD:
                if (rtspRecordFragment != null && rtspRecordFragment instanceof OnBackPressed ){
                    isIntercept = ((OnBackPressed) rtspRecordFragment).onBackPressed();
                }
                break;
            case PAGE_VIDEO:
                if (videoFragment != null && videoFragment instanceof OnBackPressed ){
                    isIntercept = ((OnBackPressed) videoFragment).onBackPressed();
                }
                break;
            default:
                break;
        }
        if (!isIntercept){
            super.onBackPressed();
        }
    }

    @Override
    protected void onNetworkStateChange(boolean isAvailable) {
        super.onNetworkStateChange(isAvailable);
        if (!isAvailable){
            layoutNetworkState.setVisibility(View.VISIBLE);
        }else {
            layoutNetworkState.setVisibility(View.GONE);
        }
    }


    private int notCheckFileCount = 0;

    @Override
    public void onFileCreate() {
        if (currentPage == PAGE_VIDEO){
            notCheckFileCount = 0;
        }else {
            notCheckFileCount++;
        }
        badgeView.setBadgeCount(notCheckFileCount);
    }
}
