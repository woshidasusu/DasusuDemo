package com.iwin.fragment01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.CodeAppbar;

/**
 * Created by sxq on 2016/5/31.
 */
public class FragmentBoke extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boke1,container,false);
        TextView appbarCode = (TextView) view.findViewById(R.id.tv_appbar_code);
        CodeAppbar codeData = new CodeAppbar();
        appbarCode.setText(codeData.getCode());
        return view;
    }
}
