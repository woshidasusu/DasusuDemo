package com.easiio.openrtspdemo.utils;

import android.content.Context;
import android.widget.Toast;

import com.easiio.openrtspdemo.RtspAPP;

public class ToastUtils {

    private static Toast mToast;
    private static Context mContext;

    /**
     * to normal use ToastUtils ,call {@code initToast} first in your extends application class
     * @param context
     */
    public static void initToast(Context context){
        mContext = context;
    }

    public static void showToast(String text){
        showToast(text,Toast.LENGTH_SHORT);
    }

    public static void showToast(String text,int time){
        if (mToast == null){
            mToast = Toast.makeText(mContext,text,time);
        }else {
            mToast.setText(text);
            mToast.setDuration(time);
        }
        mToast.show();
    }

    public static void showToast(int text){
        showToast(RtspAPP.getAppContext().getString(text),Toast.LENGTH_SHORT);
    }

    public static void showToast(int text,int time){
        showToast(RtspAPP.getAppContext().getString(text),time);
    }
}
