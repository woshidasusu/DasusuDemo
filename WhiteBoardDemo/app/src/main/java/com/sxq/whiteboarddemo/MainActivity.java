package com.sxq.whiteboarddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.sxq.whiteboarddemo.socket.SocketAPI;
import com.sxq.whiteboarddemo.whiteboard.WhiteBoardView;
import com.sxq.whiteboarddemo.whiteboard.share.DrawShare;
import com.sxq.whiteboarddemo.whiteboard.share.ScaleShare;
import com.sxq.whiteboarddemo.whiteboard.share.ScrollShare;
import com.sxq.whiteboarddemo.whiteboard.share.ShareConstant;
import com.sxq.whiteboarddemo.widget.PenConfigView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class MainActivity extends Activity implements View.OnClickListener, WhiteBoardView.OnShareLinstener {
    private static final String TAG = "MainActivity";

    private ImageButton btnPen;
    private ImageButton btnEraser;
    private ImageButton btnColor;
    private ImageButton btnUndo;
    private ImageButton btnRedo;
    private ImageButton btnEnable;
    private WhiteBoardView whiteBoardContent;
    private PenConfigView penConfigView;

    private Button btnZoomin;
    private Button btnZoomout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket !=null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        btnPen = (ImageButton) findViewById(R.id.imgbtn_pen);
        btnEraser = (ImageButton) findViewById(R.id.imgbtn_eraser);
        btnColor = (ImageButton) findViewById(R.id.imgbtn_color);
        btnUndo = (ImageButton) findViewById(R.id.imgbtn_undo);
        btnRedo = (ImageButton) findViewById(R.id.imgbtn_redo);
        btnEnable = (ImageButton) findViewById(R.id.imgbtn_enable);
        whiteBoardContent = (WhiteBoardView) findViewById(R.id.whiteboard_content);
        penConfigView = (PenConfigView) findViewById(R.id.pen_config_view);

        btnZoomin = (Button) findViewById(R.id.btn_zoomin);
        btnZoomout = (Button) findViewById(R.id.btn_zoomout);

        btnPen.setOnClickListener(this);
        btnPen.setBackgroundResource(R.drawable.pen_selected_selector);
        btnColor.setOnClickListener(this);
        btnEraser.setOnClickListener(this);
        btnEnable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                whiteBoardContent.clearContent();
                return true;
            }
        });
        btnUndo.setOnClickListener(this);
        btnRedo.setOnClickListener(this);
        btnEnable.setOnClickListener(this);

        btnZoomin.setOnClickListener(this);
        btnZoomout.setOnClickListener(this);

        whiteBoardContent.setShareListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbtn_pen:
                selectedPenBtn();
                break;

            case R.id.imgbtn_color:
                selectedColorBtn();
                break;

            case R.id.imgbtn_eraser:
                selectedEraserBtn();
                break;

            case R.id.imgbtn_undo:
                selectedUndoBtn();
                break;

            case R.id.imgbtn_redo:
                selectedRedoBtn();
                break;

            case R.id.imgbtn_enable:
                selectedEnableBtn();
                break;

            case R.id.btn_zoomin:
                whiteBoardContent.addPenWidth();
                break;

            case R.id.btn_zoomout:
                whiteBoardContent.reducePenWidth();
                break;

            default:
                break;
        }
    }

    private void selectedPenBtn() {
        btnPen.setBackgroundResource(R.drawable.pen_selected_selector);
        btnEraser.setBackgroundResource(R.drawable.eraser_normal_selector);
        whiteBoardContent.selectedPen();
    }

    private void selectedColorBtn() {

    }

    private void selectedEraserBtn() {
        btnEraser.setBackgroundResource(R.drawable.eraser_selected_selector);
        btnPen.setBackgroundResource(R.drawable.pen_normal_selector);
        whiteBoardContent.selectedEraser();
    }

    private void selectedUndoBtn() {
        whiteBoardContent.undo();
    }

    private void selectedRedoBtn() {
        whiteBoardContent.redo();
    }

    private void selectedEnableBtn() {
        if (whiteBoardContent.isEnable()) {
            whiteBoardContent.setEnable(false);
            btnEnable.setBackgroundResource(R.drawable.undisable_selector);

            btnPen.setVisibility(View.INVISIBLE);
            btnColor.setVisibility(View.INVISIBLE);
            btnEraser.setVisibility(View.INVISIBLE);
            btnUndo.setVisibility(View.INVISIBLE);
            btnRedo.setVisibility(View.INVISIBLE);
        } else {
            whiteBoardContent.setEnable(true);
            btnEnable.setBackgroundResource(R.drawable.disable_selector);

            btnPen.setVisibility(View.VISIBLE);
            btnColor.setVisibility(View.VISIBLE);
            btnEraser.setVisibility(View.VISIBLE);
            btnUndo.setVisibility(View.VISIBLE);
            btnRedo.setVisibility(View.VISIBLE);
        }
    }

    private void selectedShareWhiteboard() {
        if (whiteBoardContent.isShareWhiteboard()) {
            whiteBoardContent.setShareWhiteboard(false);



        } else {
            whiteBoardContent.setShareWhiteboard(true);
            //judge if socket was connected
            //......
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        socket = new Socket(SocketAPI.SERVER_IP_ADDRESS, SocketAPI.SERVER_PORT);
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                         String line ;
                        while ((line = reader.readLine()) != null){
                            Log.d(TAG,"socket receive:"+line);
                            Gson gson = new Gson();
                            Message message = Message.obtain();
                            if (line.charAt(0) == '2'){
                                DrawShare drawShare = gson.fromJson(line.substring(1),DrawShare.class);
                                message.what = ShareConstant.SHARE_TYPE_OPERATION_DRAW;
                                message.obj = drawShare;
                                Log.d(TAG,"line[0]:"+line.charAt(0)+"   "+drawShare.toString());
                            }else if (line.charAt(0) == '1'){
                                ScrollShare scrollShare = gson.fromJson(line.substring(1),ScrollShare.class);
                                message.what = ShareConstant.SHARE_TYPE_OPERATION_SCROLL;
                                message.obj = scrollShare;
                                Log.d(TAG,"line[0]:"+line.charAt(0)+"   "+scrollShare.toString());
                            }else if (line.charAt(0) == '4'){
                                ScaleShare scaleShare = gson.fromJson(line.substring(1),ScaleShare.class);
                                message.what = ShareConstant.SHARE_TYPE_OPERATION_SCALE;
                                message.obj = scaleShare;
                            }

                            myHandler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    private Socket socket;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;


    @Override
    public void onShare(String content) {
        if (socket.isConnected() && writer != null){
            try {
                writer.write(content+"\n");
                writer.flush();
                Log.d(TAG,"[onShare]:content    "+content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ShareConstant.SHARE_TYPE_OPERATION_DRAW:
                    DrawShare drawShare = (DrawShare) msg.obj;
                    Log.d(TAG,"handleMessage"+drawShare.toString());
                    whiteBoardContent.actionDraw(drawShare.getEventAction(),drawShare.getX(),drawShare.getY());
                    break;

                case ShareConstant.SHARE_TYPE_OPERATION_SCROLL:
                    ScrollShare scrollShare = (ScrollShare) msg.obj;
                    Log.d(TAG,"handleMessage"+scrollShare.toString());
                    whiteBoardContent.onViewScroll(scrollShare);
                    break;

                case ShareConstant.SHARE_TYPE_OPERATION_SCALE:
                    ScaleShare scaleShare = (ScaleShare) msg.obj;
                    whiteBoardContent.onScaleChange(scaleShare);
                    break;
            }
        }
    };
}