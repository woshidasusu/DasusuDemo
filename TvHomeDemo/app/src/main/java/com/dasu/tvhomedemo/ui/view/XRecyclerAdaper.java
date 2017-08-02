package com.dasu.tvhomedemo.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

public abstract class XRecyclerAdaper<T> extends RecyclerView.Adapter {

    protected List<T> mDataList;
    private  OnItemClickListener mItemClickListener;

    public XRecyclerAdaper(List<T> data) {
        mDataList = data;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final RecyclerView.ViewHolder vholer = holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(pos, mDataList.get(pos));
                }
            }
        });
        onBindView(holder, position);
    }

    protected abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
