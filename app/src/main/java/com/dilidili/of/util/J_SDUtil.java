package com.dilidili.of.util;

import android.os.Environment;

/**
 * SD卡信息
 * Created by Jessica on 2016/10/10.
 */
public class J_SDUtil {
    /**
     * 检测SD卡是否可用
     * @return true 可用， false 不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取SD卡绝对目录
     */
    public static String getSDAbsolutePath(){
        if(isCanUseSD()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
            return "";
        }
    }




}
