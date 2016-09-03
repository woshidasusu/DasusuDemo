package com.sxq.whiteboarddemo.socket;

import com.sxq.whiteboarddemo.whiteboard.WhiteBoardView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

/**
 * Created by sxq on 2016/7/13.
 */
public class SocketUtil implements Runnable,WhiteBoardView.OnShareLinstener {

    public Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    @Override
    public void onShare(String content) {

    }

    @Override
    public void run() {

    }
}
