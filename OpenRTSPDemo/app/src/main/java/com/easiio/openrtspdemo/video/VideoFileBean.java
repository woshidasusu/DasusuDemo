package com.easiio.openrtspdemo.video;


import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class VideoFileBean implements Comparable<VideoFileBean> {
    private String name;
    private String path;
    private Bitmap thumbnail;
    private boolean selected;

    public VideoFileBean() {
    }

    public VideoFileBean(String name, String path) {
        this.name = name;
        this.path = path;
        try {
            this.thumbnail =
                    ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        }catch (Exception e){
            e.printStackTrace();
            this.thumbnail = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        try {
            this.thumbnail =
                    ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        }catch (Exception e){
            e.printStackTrace();
            this.thumbnail = null;
        }
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(VideoFileBean another) {
        return another.getName().compareToIgnoreCase(this.getName());
    }


}
