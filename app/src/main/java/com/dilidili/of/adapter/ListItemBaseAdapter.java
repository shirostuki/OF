package com.dilidili.of.adapter;

import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dilidili.of.R;
import com.dilidili.of.base.J_BaseActivity;
import com.dilidili.of.entity.Lives;

import org.xutils.x;

import java.util.List;

/**
 * Created by mary on 2016/11/4.
 */

public class ListItemBaseAdapter extends BaseAdapter {
    private List mlist;
    private J_BaseActivity mBaseActivity;
    private int mType;//显示类型
    public static final int LIVE = 0;//直播列表

    public ListItemBaseAdapter(J_BaseActivity baseActivity, List list, int type) {
        this.mBaseActivity = baseActivity;
        this.mlist = list;
        mType = type;
    }

    @Override
    public int getCount() {
        return mlist != null ? mlist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mlist != null ? mlist.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder = null;
        if (v == null) {
            holder = new ViewHolder();
            switch (mType) {
                case LIVE:
                    v = mBaseActivity.getLayoutInflater().inflate(R.layout.item_live, null);
                    holder.cover = (ImageView) v.findViewById(R.id.img_cover);
                    holder.tv1 = (TextView) v.findViewById(R.id.tv_title);
                    holder.tv2 = (TextView) v.findViewById(R.id.tv_up_name);
                    holder.tv3 = (TextView) v.findViewById(R.id.tv_focus_count);
//                    LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)  holder.cover.getLayoutParams(); //取控件textView当前的布局参数
//                    linearParams.width = holder.cover.getMeasuredHeight();// 控件的宽强制设成30
//                    linearParams.height = linearParams.width/3*2;// 控件的高强制设成20
//                    holder.cover.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
                    break;
            }
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        switch (mType) {
            case LIVE:
                Lives live = (Lives) mlist.get(position);
                x.image().bind(holder.cover, live.getCover().getSrc());
                String html = "<font color=\"#F27075\">#"+live.getArea()+"#</font>" +" " + live.getTitle();
                holder.tv1.setText(Html.fromHtml(html));
                holder.tv2.setText(live.getOwner().getName());
                holder.tv3.setText(live.getOnline() + "");
                break;
        }
        return v;
    }

    class ViewHolder {
        ImageView cover;
        TextView tv1, tv2, tv3;
    }

    /**
     * 动态设置图片大小
     *
     * @param viewHolder
     */
    private void setImageSize(ViewHolder viewHolder, View v) {
        //DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        //mBaseActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        // int screenWidth = dm.widthPixels;
        /* 设置图片的位置 */
        ViewGroup.MarginLayoutParams margin9 = new ViewGroup.MarginLayoutParams(viewHolder.cover.getLayoutParams());
        margin9.setMargins(0, 0, 0, 0);
        RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(margin9);
        layoutParams9.height = v.getMeasuredWidth() / 3 * 2;//设置图片的高度
        // layoutParams9.width = screenWidth / 3; //设置图片的宽度
        viewHolder.cover.setLayoutParams(layoutParams9);
    }
}
