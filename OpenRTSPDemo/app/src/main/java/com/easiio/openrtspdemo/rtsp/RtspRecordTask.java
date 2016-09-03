package com.easiio.openrtspdemo.rtsp;

import android.text.TextUtils;
import android.util.Log;

import com.easiio.openrtspdemo.RtspConstans;
import com.easiio.openrtspdemo.utils.CommandUtils;
import com.easiio.openrtspdemo.utils.FileUtils;

import java.io.File;

public class RtspRecordTask implements Runnable {

    private final String TAG = this.getClass().getSimpleName();

    private static final String VIDEO_DIR = RtspConstans.EXTERNAL_STROAGE_VIDEO_SUBDIR;
    private String recordCommond;
    private String copyCommond;
    private String removeCommond;
    private boolean isReady = false;

    static {
        FileUtils.creatSubDir(VIDEO_DIR);
    }

    public RtspRecordTask(OpenRtspBean bean) {
        if (bean.getRtspUrl() == null || TextUtils.isEmpty(bean.getRtspUrl())) {
            isReady = false;
            return;
        }
        if (bean.getFileName() == null || TextUtils.isEmpty(bean.getFileName())) {
            isReady = false;
            return;
        }
        OpenRtspBean openRtspBean = new OpenRtspBean().Builder(bean);

//        recordCommond = "./openRTSP -4 -d 20 -w 640 -h 480 -f 15 " + rtspSource + " >" + saveFileName + ".mp4";
        recordCommond = "./" + openRtspBean.getExecutor()
                + " -" + openRtspBean.getFormat()
                + " -d " + openRtspBean.getD()
                + " -w " + openRtspBean.getW()
                + " -h " + openRtspBean.getH()
                + " -f " + openRtspBean.getFps()
                + " -b " + "400000"
                + " " + openRtspBean.getRtspUrl()
                + " >" + openRtspBean.getFileName()+openRtspBean.getFileType();
        String videoPath = FileUtils.getAppStroagePath() + VIDEO_DIR + File.separator;

        copyCommond = "cp " + openRtspBean.getFileName() + "*" + " " + videoPath;
        removeCommond = "rm " + openRtspBean.getFileName() + "*";
        isReady = true;
    }

    @Override
    public void run() {
        if (isReady) {
            //start record
            Log.d(TAG,"exec: " + recordCommond);
            String result = CommandUtils.complexExec(recordCommond, FileUtils.getFileDirPath());

            if (result != null) {
                //move video file from memory to sd card
                Log.d(TAG,"exec: " + copyCommond);
                CommandUtils.complexExec(copyCommond, FileUtils.getFileDirPath());
                Log.d(TAG,"exec: " + removeCommond);
                CommandUtils.complexExec(removeCommond, FileUtils.getFileDirPath());
            }
        }
    }
}
