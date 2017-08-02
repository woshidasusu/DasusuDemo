package com.dasu.tvhomedemo.presenter.home;

import android.content.Context;

import com.dasu.tvhomedemo.presenter.BasePresenter;

/**
 * Created by suxq on 2017/8/1.
 */

public class HomePresenter extends BasePresenter<HomeMvpView> {

    private Context mContext;

    public HomePresenter(Context context, HomeMvpView view) {
        mContext = context;
        attachView(view);
    }

    public void loadData() {

    }
}
