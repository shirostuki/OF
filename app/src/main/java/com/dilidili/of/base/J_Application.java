package com.dilidili.of.base;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Vibrator;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;

/**
 * Created by Jessica on 2016/9/29.
 */
public class J_Application extends Application {
    public static J_Application application = null;
    /**
     * 全局字体(EditText 密码显示要单独再设一遍)
     */
    public static Typeface mTypeFace;
    /**
     * 振动器
     */
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        setmTypeFace();
        this.mVibrator = (Vibrator) this.getApplicationContext().getSystemService(VIBRATOR_SERVICE);
    }

    public static J_Application getInstance() {
        if (application == null) {
            synchronized (J_Application.class) {
                if (application == null) {
                    application = new J_Application();
                }
            }
        }
        return application;
    }

    /**
     * 设置全局字体
     */
    public void setmTypeFace() {
        mTypeFace = Typeface.createFromAsset(getAssets(), "fonts/danmaku.ttf");
        try
        {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, mTypeFace);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}