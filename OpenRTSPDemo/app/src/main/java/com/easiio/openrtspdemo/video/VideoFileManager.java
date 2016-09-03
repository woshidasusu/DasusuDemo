package com.easiio.openrtspdemo.video;

import android.os.AsyncTask;
import android.util.Log;

import com.easiio.openrtspdemo.RtspConstans;
import com.easiio.openrtspdemo.utils.FileUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VideoFileManager {

    private static VideoFileManager mInstance = null;

    private VideoFileManager(){}

    public static VideoFileManager getInstance(){
        if (mInstance == null){
            mInstance = new VideoFileManager();
        }
        return mInstance;
    }

    private String videoDir = FileUtils.getAppStroagePath()
            + RtspConstans.EXTERNAL_STROAGE_VIDEO_SUBDIR + File.separator;

    public void loadVideoFiles(final CommonAdapter<VideoFileBean> bean, final List<VideoFileBean> beanList){
        AsyncTask loadTask = new AsyncTask<Object,Void,Void>() {

            private CommonAdapter<VideoFileBean> result = bean;
            private List videoFileList = beanList;

            @Override
            protected Void doInBackground(Object... params) {
                File file = new File(videoDir);
                if (file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        VideoFileBean bean = new VideoFileBean(f.getName(), f.getAbsolutePath());
                        videoFileList.add(0,bean);
                        Collections.sort(videoFileList);
                        publishProgress();
                    }

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                result.notifyDataSetChanged();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.notifyDataSetChanged();
            }

        };
        loadTask.execute();
    }

}
