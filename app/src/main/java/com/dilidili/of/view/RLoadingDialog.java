package com.dilidili.of.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dilidili.of.R;


/**
 * 中心旋转加载框
 * Created by Jessica on 2016/10/19.
 */
public class RLoadingDialog extends Dialog {

    private Context mContext = null;
    private String MSG = "加载中...";

    public RLoadingDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RLoadingDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.loading_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        ImageView spaceshipImage = (ImageView) findViewById(R.id.loading_image);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.x_load_animation);
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        setMessage(MSG);
//      show();
    }

    /**
     * 设置提示消息
     */
    public RLoadingDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) findViewById(R.id.loading_text);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
        return this;
    }

    @Override
    public void onBackPressed() {
        if (isShowing()) {
            dismiss();
        }
        super.onBackPressed();
    }
}
