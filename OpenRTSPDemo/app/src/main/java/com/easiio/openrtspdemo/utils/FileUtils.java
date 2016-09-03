package com.easiio.openrtspdemo.utils;

import android.content.Context;
import android.os.Environment;

import com.easiio.openrtspdemo.RtspAPP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    public static String getFileDirPath(){
        return RtspAPP.getAppContext().getFilesDir().getAbsolutePath();
    }

    public static String getSdCardPath(){
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static String getSdCardState(){
        return Environment.getExternalStorageState();
    }

    public static boolean isSdCardExist(){
        if (getSdCardState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    private static String appStroagePath = null;

    public static void initStroageDir(String dir){
        if (!isSdCardExist()){
            throw new IllegalArgumentException("sd card is not exist!");
        }
        appStroagePath = getSdCardPath().endsWith(File.separator)
                ?   getSdCardPath() + dir + File.separator
                :   getSdCardPath() + File.separator + dir + File.separator;
        File file = new File(appStroagePath);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * return the app's extenal stroage path
     * @return null return , if the app still creat it's extenal stroage dir
     */
    public static String getAppStroagePath(){
        return appStroagePath;
    }

    /**
     * creat sub dir in the app's extenal stroage dir
     * @param dir
     */
    public static void creatSubDir(String dir){
        if (appStroagePath == null){
            throw new IllegalArgumentException("app does not creat it's extenal stroage dir");
        }
        File file = new File(appStroagePath + dir );
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static void copyFromAssetsToFileDir(Context context, String sourceFile,
                                               String destFile) throws IOException {
        /* 获取assets目录下文件的输入流 */
        InputStream is = context.getAssets().open(sourceFile);
		/* 获取文件大小 */
        int size = is.available();
		/* 创建文件的缓冲区 */
        byte[] buffer = new byte[size];
		/* 将文件读取到缓冲区中 */
        is.read(buffer);
		/* 关闭输入流 */
        is.close();
		/* 打开app安装目录文件的输出流 */
        FileOutputStream output = context.openFileOutput(destFile,
                Context.MODE_PRIVATE);
		/* 将文件从缓冲区中写出到内存中 */
        output.write(buffer);
		/* 关闭输出流 */
        output.close();
    }
}
