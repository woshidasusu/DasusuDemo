package com.dasu.tvhomedemo.ui.home;

import android.content.Context;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dasu.tvhomedemo.R;
import com.dasu.tvhomedemo.mode.home.LayoutMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

class HomeMenuView extends HorizontalGridView{

    private HomeMenuItemAdapter mMenuItemAdapter = null;
    private List<LayoutMenu.Element> mElementList = null;
    private HorizontalGridView mMenuHG;

    public HomeMenuView(Context context) {
        this(context, null);
    }

    public HomeMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mElementList == null) {
            mElementList = new ArrayList<>();
        }
        mMenuItemAdapter = new HomeMenuItemAdapter(mElementList);
        this.setAdapter(mMenuItemAdapter);
    }

    public void setData(List<LayoutMenu.Element> data) {
        if (mElementList == null) {
            mElementList = new ArrayList<>();
        }
        mElementList.clear();
        mElementList.addAll(data);
        if (mMenuItemAdapter != null) {
            mMenuItemAdapter.notifyDataSetChanged();
        }
    }

}
