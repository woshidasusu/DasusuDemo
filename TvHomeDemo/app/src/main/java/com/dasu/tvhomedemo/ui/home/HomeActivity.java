package com.dasu.tvhomedemo.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.Log;
import android.view.KeyEvent;

import com.dasu.tvhomedemo.R;
import com.dasu.tvhomedemo.mock.MockLayoutMenu;
import com.dasu.tvhomedemo.mode.home.LayoutMenu;
import com.dasu.tvhomedemo.presenter.home.HomeMvpView;
import com.dasu.tvhomedemo.presenter.home.HomePresenter;
import com.dasu.tvhomedemo.ui.base.BaseActivity;

import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

public class HomeActivity extends BaseActivity implements HomeMvpView{

    private HomePresenter mHomePresenter = null;
    private VerticalGridView mMenuVG = null;
    private HomeMenuAdapter mMenuAdapter = null;
    private List<LayoutMenu> mDataList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVariable();
        initView();
        loadData();
    }

    private void initVariable() {
        mDataList = MockLayoutMenu.mock();
        mHomePresenter = new HomePresenter(this, this);
        mMenuAdapter = new HomeMenuAdapter(mDataList);
    }

    private void initView() {
        mMenuVG = (VerticalGridView) findViewById(R.id.vg_home_content);
        mMenuVG.setAdapter(mMenuAdapter);
    }

    private void loadData() {
        mHomePresenter.loadData();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                Log.d("home", "向下");
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                Log.d("home", "向上");
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                Log.d("home", "向左");
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                Log.d("home", "向右");
            }
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                Log.d("home", "ok");
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
