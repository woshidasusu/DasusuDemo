package com.dasu.tvhomedemo.ui.home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dasu.tvhomedemo.R;
import com.dasu.tvhomedemo.mode.home.LayoutMenu;
import com.dasu.tvhomedemo.ui.view.XRecyclerAdaper;

import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

class HomeMenuAdapter extends XRecyclerAdaper<LayoutMenu> {

    public HomeMenuAdapter(List<LayoutMenu> data) {
        super(data);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) holder;
        Log.d("home", vHolder + "");
        LayoutMenu menu = mDataList.get(position);
        vHolder.menuTitle.setText(menu.getMenuName());
        vHolder.menuChilds.setData(menu.getElementList());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView menuTitle;
        HomeMenuView menuChilds;

        public ViewHolder(View itemView) {
            super(itemView);
            menuTitle = (TextView) itemView.findViewById(R.id.tv_home_title);
            menuChilds = (HomeMenuView) itemView.findViewById(R.id.hcv_home_menu);
        }
    }
}
