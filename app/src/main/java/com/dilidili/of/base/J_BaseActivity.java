package com.dilidili.of.base;
/**
 * 基类Activity
 * Created by Jessica on 2016/9/28.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.dilidili.of.R;
import com.dilidili.of.util.J_Parameter;
import com.dilidili.of.view.RLoadingDialog;


public abstract class J_BaseActivity extends AppCompatActivity implements View.OnClickListener {
    //public YunHttpUtil mHttpUtil = null;
    //public YunImageDownloader mImageDownloader = null;
    /* 消息显示 */
    private static Toast mToast;
    /* 屏幕分辨率 */
    public DisplayMetrics displayMetrics;
    /* 全局的SharedPreferences对象，已经完成初始化 */
    private SharedPreferences mSharedPreferences = null;
    /* 开始显示加载对话*/
    public RLoadingDialog mRLoadingDialog;
    /**
     * 状态栏通知的管理
     */
    private NotificationManager nm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.mHttpUtil = YunHttpUtil.getInstance(this);
//        this.displayMetrics = YunAppUtil.getDisplayMetrics(this);
//        this.mImageDownlpader = new YunImageDownloader(this);
//        this.mImageDownloader.setWidth(this.displayMetrics.widthPixels);
//        this.mImageDownloader.setHeight(this.displayMetrics.heightPixels);
        this.nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        this.mSharedPreferences = this.getSharedPreferences(J_Parameter.SP_FILE_NAME, 0);
    }

    /* 实例化控件 */
    protected abstract void initView();

    @Override
    public void onClick(View view) {
        //可选继承
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mToast != null) {
            //让消息随Activity一起关闭
            mToast.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 页面跳转
     *
     * @param context  当前页面
     * @param cls      需要跳转的页面class
     * @param isFinish 是否结束当前页面
     * @param key      传递消息的单个key
     * @param value    传递的消息
     */
    public void intentActivity(J_BaseActivity context, Class<?> cls, boolean isFinish, String key, String value) {
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        startActivity(intent);
        if (isFinish) {
            context.finish();
        }
    }

    /**
     * 页面跳转
     *
     * @param context  当前页面
     * @param cls      需要跳转的页面class
     * @param isFinish 是否结束当前页面
     */
    public void intentActivity(J_BaseActivity context, Class<?> cls, boolean isFinish) {
        intentActivity(context, cls, isFinish, "", "");
    }

    /**
     * 页面跳转
     *
     * @param context 当前页面
     * @param cls     需要跳转的页面class
     */
    public void intentActivity(J_BaseActivity context, Class<?> cls) {
        intentActivity(context, cls, false);
    }

    /**
     * 消息显示
     *
     * @param context  上下文
     * @param content  显示内容
     * @param duration 显示时间
     */
    public void showToast(Context context, CharSequence content, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast.show();
    }

    /**
     * 消息显示
     *
     * @param context 上下文
     * @param content 显示内容
     */
    public void showToast(Context context, CharSequence content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }


    /**
     * 保存SharedPreferences的值
     */
    public void saveSharedPreferences(String key, String value) {
        Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取SharedPreferences的值
     */
    public String getSharedPreferences(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 显示加载框
     *
     * @param text
     */
    public void startLoading(String text) {
        mRLoadingDialog = null;
        startLoading();
        if (mRLoadingDialog != null) {
            mRLoadingDialog.setMessage(text);
        }
    }

    /**
     * 显示加载框
     */
    public void startLoading() {
        if (mRLoadingDialog == null) {
            mRLoadingDialog = new RLoadingDialog(this, R.style.x_loading_dialog);
        }
        mRLoadingDialog.show();
    }

    /**
     * 停止显示加载对话
     */
    public void stopLoading() {
        if (mRLoadingDialog != null) {
            mRLoadingDialog.dismiss();
            mRLoadingDialog = null;
        }
    }

    /**
     * 初始化 后退按钮
     *
     * @param context
     */
    public void initBack(final J_BaseActivity context) {
// TODO: 2016/9/28 这里的控件由实际返回按钮来决定
//        TextView back = (TextView) context.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.finish();
//            }
//        });
    }

}