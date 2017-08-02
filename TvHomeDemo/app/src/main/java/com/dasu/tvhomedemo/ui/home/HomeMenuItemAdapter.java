package com.dasu.tvhomedemo.ui.home;

import android.graphics.Color;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.SearchOrbView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dasu.tvhomedemo.R;
import com.dasu.tvhomedemo.mode.home.LayoutMenu;
import com.dasu.tvhomedemo.ui.utils.ScaleUtil;
import com.dasu.tvhomedemo.ui.view.XRecyclerAdaper;

import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

class HomeMenuItemAdapter extends XRecyclerAdaper<LayoutMenu.Element> {

    public HomeMenuItemAdapter(List<LayoutMenu.Element> data) {
        super(data);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) holder;
        LayoutMenu.Element element = mDataList.get(position);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("home", v + "放大");
                    ScaleUtil.scaleUp(v);
                } else {
                    Log.d("home", v + "缩小");
                    ScaleUtil.scaleDown(v);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_rect_card, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView elementCard;

        public ViewHolder(View itemView) {
            super(itemView);
            elementCard = (ImageView) itemView.findViewById(R.id.icv_home_card);
        }
    }
}
