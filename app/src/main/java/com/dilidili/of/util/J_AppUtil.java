package com.dilidili.of.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Created by Jessica on 2016/10/12.
 */
public class J_AppUtil {
    public J_AppUtil() {

    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo pi = e.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } finally {
            return versionName == null || versionName.length() <= 0 ? "" : versionName;
        }
    }

    public static int getNumCores() {
        try {
            File e = new File("/sys/devices/system/cpu");
            File[] files = e.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }
            });
            return files.length;
        } catch (Exception var2) {
            return 1;
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        Resources mResources;
        if(context == null){
            mResources = Resources.getSystem();
        }else{
            mResources = context.getResources();
        }

        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }

    public static void showSoftInput(Context context){
       // InputMethod
    }
}
