package com.yanz.machine.shinva.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yanzi on 2016-05-17.
 */
public class FileUtils {
    /*
    * 获取sd卡目录
    * */


    private String SDPATH;
    public FileUtils(){
        SDPATH = Environment.getExternalStorageDirectory()+"/";
    }

    //在sdcard上创建文件
    public File createSDFile(String fileName) throws IOException{
        File file = new File(SDPATH+fileName);
        file.createNewFile();
        return file;
    }
    //在sdcard上创建目录
    public File createSDDir(String dirName){
        File dir = new File(SDPATH+dirName);
        //mkdir只能创建以及目录，mkdirs可创建多级目录
        dir.mkdir();
        return dir;
    }
    //判断文件是否存在
    public boolean isFileExist(String fileName){
        File file = new File(SDPATH+fileName);
        return file.exists();
    }
    //
    public void deleteFile(String fileName){
        File file = new File(SDPATH+fileName);
        file.delete();
    }
    /**
     * 将一个inputstream里面的数据写入SD卡中 第一个参数为目录名 第二个参数为文件名
     */
    public File write2SDFromInput(String path, InputStream inputstream) {
        File file = null;
        OutputStream output = null;
        try {
            file = createSDFile(path);
            output = new FileOutputStream(file);
            // 4k为单位，每4K写一次
            byte buffer[] = new byte[4 * 1024];
            int temp = 0;
            while ((temp = inputstream.read(buffer)) != -1) {
                // 获取指定信,防止写入没用的信息
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return file;
    }
}

