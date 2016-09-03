package com.easiio.openrtspdemo.rtsp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.easiio.openrtspdemo.utils.CommandUtils;
import com.easiio.openrtspdemo.utils.FileUtils;
import com.easiio.openrtspdemo.utils.HandlerUtlis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RtspRecordManager {

    private final String TAG = this.getClass().getSimpleName();

    public static final int WHAT_START_RECORD = 1;
    public static final int WHAT_STOP_RECORD = 2;
    public static final int RECORD_STATE_RUNNING = 3;
    public static final int RECORD_STATE_STOP = 4;

    public static final String OPENRTSP_BINARY_FILE = "openRTSP";

    private static RtspRecordManager mInstance = null;

    private RtspRecordManager(){}

    public static RtspRecordManager getInstance(){
        if (mInstance == null){
            mInstance = new RtspRecordManager();
        }
        return mInstance;
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
    private taskHandle mHandle = new taskHandle();
    private boolean isRunning = false;
    private int recordState = RECORD_STATE_STOP;
    private OpenRtspBean openRtspBean;

    public void startRecord(OpenRtspBean bean){
        isRunning = true;
        this.openRtspBean = bean;
        HandlerUtlis.sendMessage(mHandle,WHAT_START_RECORD);
    }

    public void stopRecord(){
        isRunning = false;
        recordState = RECORD_STATE_STOP;
        mHandle.removeMessages(WHAT_START_RECORD);
    }

    public int getRecordState(){
        return recordState;
    }

    private void startRecord(){
        if (isRunning){
            recordState = RECORD_STATE_RUNNING;
            Date now = new Date();
            String saveFilePre = dateFormat.format(now);
            openRtspBean.setFileName(saveFilePre);
            openRtspBean.setExecutor(OPENRTSP_BINARY_FILE);
            new Thread(new RtspRecordTask(openRtspBean)).start();
            HandlerUtlis.sendMessage(mHandle,WHAT_START_RECORD,openRtspBean.getD() * 1000);
        }
    }

    public void varifyFile(Context context, String fileName) {
        FileInputStream in = null;
        try {
            /* 查看文件是否存在, 如果不存在就会走异常中的代码 */
            in = context.openFileInput(fileName);
        } catch (FileNotFoundException notfoundE) {
            try {
            	/* 拷贝文件到app安装目录的files目录下 */
                FileUtils.copyFromAssetsToFileDir(context, fileName, fileName);
                /* 修改文件权限脚本 */
                String script = "chmod 777 " + fileName;
                /* 执行脚本 */
                String result = CommandUtils.complexExec(script,FileUtils.getFileDirPath());
                CommandUtils.complexExec("ls -l",FileUtils.getFileDirPath());
                Log.d(TAG, "varifyFile: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class taskHandle extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_START_RECORD:
                    startRecord();
                    break;
                case WHAT_STOP_RECORD:

                    break;
                default:
                    break;
            }
        }
    }
}
