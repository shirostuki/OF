package com.dilidili.of.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dilidili.of.R;
import com.dilidili.of.activity.DetuAcitivity;
import com.dilidili.of.activity.LiveActivity;
import com.dilidili.of.adapter.ListItemBaseAdapter;
import com.dilidili.of.base.J_BaseFragment;
import com.dilidili.of.entity.HeadBean;
import com.dilidili.of.entity.LiveListBean;
import com.dilidili.of.entity.Lives;
import com.dilidili.of.util.J_IP;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * 直播
 * Created by Jessica on 2016/10/26.
 */

public class LiveFragment extends J_BaseFragment implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private TextView mCurrLiveCount;
    private ListItemBaseAdapter mAdapter;
    private List<Lives> mList;
    private LiveListBean mLiveListBean;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_live, container, false);

        Button b = (Button) mView.findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.intentActivity(mBaseActivity, LiveActivity.class);

            }
        });
        mView.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.intentActivity(mBaseActivity, DetuAcitivity.class);
            }
        });
        initView();
        getJson();
        return mView;
    }

    private void initView() {
        mGridView = (GridView) mView.findViewById(R.id.gv_live);
        mCurrLiveCount = (TextView) mView.findViewById(R.id.tv_curr_live_count);
    }

    private void getJson() {
        Log.e("onSuccess", "onSuccess");
        x.http().get(new RequestParams(J_IP.LiveListUrl), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                HeadBean headBean = JSON.parseObject(result, HeadBean.class);
                if (headBean.getCode() == 0) {
                    mLiveListBean = JSON.parseObject(result, LiveListBean.class);
                    mList = mLiveListBean.getData().getRecommend_data().getLives();
                    //Log.e("onSuccess", mList + "");
                    mCurrLiveCount.setText(mLiveListBean.getData().getRecommend_data().getPartition().getCount() + "");
                    mAdapter = new ListItemBaseAdapter(mBaseActivity, mList, ListItemBaseAdapter.LIVE);
                    mGridView.setAdapter(mAdapter);
                    mGridView.setOnItemClickListener(LiveFragment.this);
                } else {
                    Log.e("onSuccess", headBean.getMessage() + "");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("", ex + "--" + isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.isEmpty(mList.get(position).getPlayurl())){
            mBaseActivity.intentActivity(mBaseActivity,LiveActivity.class,false,"LIVEURL",mList.get(position).getPlayurl());
        }
    }
}
