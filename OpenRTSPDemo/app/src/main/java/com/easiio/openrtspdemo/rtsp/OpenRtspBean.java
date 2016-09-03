package com.easiio.openrtspdemo.rtsp;

public class OpenRtspBean {
    //the resolution of the vedio
    private int w;
    private int h;
    //the number of frames
    private int fps;

    //the record's duration
    private long d;

    //the video's format,such as -i means .avi and -4 means .mp4 and -H means .mov
    private char format;

    //the file's name of the record's video
    private String fileName;

    //the file's type,according to the {@cord format}
    private String fileType;

    private String rtspUrl;

    //the binary executable file name
    private String executor;

    public OpenRtspBean() {
    }

    public OpenRtspBean Builder(OpenRtspBean bean){
        OpenRtspBean mBean = new OpenRtspBean();
        mBean.w = bean.getW();
        mBean.h = bean.getH();
        mBean.fps = bean.getFps();
        mBean.d = bean.getD();
        mBean.format = bean.getFormat();
        mBean.fileName = bean.getFileName();
        mBean.fileType = bean.getFileType();
        mBean.rtspUrl = bean.getRtspUrl();
        mBean.executor = bean.getExecutor();
        return mBean;
    }

    @Override
    public String toString() {
        return "OpenRtspBean{" +
                "w=" + w +
                ", h=" + h +
                ", fps=" + fps +
                ", d=" + d +
                ", format=" + format +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", rtspUrl='" + rtspUrl + '\'' +
                ", executor='" + executor + '\'' +
                '}';
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public long getD() {
        return d;
    }

    public void setD(long d) {
        this.d = d;
    }

    public char getFormat() {
        return format;
    }

    public void setFormat(char format) {
        this.format = format;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}
