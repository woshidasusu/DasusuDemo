package com.easiio.openrtspdemo.utils;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

public class HandlerUtlis {

    public static void sendMessage(@NonNull Handler handler, int what){
        Message msg = Message.obtain();
        msg.what = what;
        sendMessage(handler,msg);
    }

    public static void sendMessage(@NonNull Handler handler, int what , long delay){
        Message msg = Message.obtain();
        msg.what = what;
        sendMessage(handler,msg,delay);
    }

    public static void sendMessage(@NonNull Handler handler, int what ,Object obj){
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        sendMessage(handler,msg);
    }

    public static void sendMessage(@NonNull Handler handler, int what ,Object obj,long delay){
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        sendMessage(handler,msg,delay);
    }

    public static void sendMessage(@NonNull Handler handler ,Message msg){
        handler.sendMessage(msg);
    }

    public static void sendMessage(@NonNull Handler handler,Message msg,long delay){
        if (delay < 1){
            handler.sendMessage(msg);
        }else {
            handler.sendMessageDelayed(msg,delay);
        }
    }
}
