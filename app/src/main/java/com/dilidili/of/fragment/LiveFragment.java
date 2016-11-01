package com.dilidili.of.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dilidili.of.R;
import com.dilidili.of.activity.DetuAcitivity;
import com.dilidili.of.activity.LiveActivity;
import com.dilidili.of.base.J_BaseFragment;

/**
 * 直播
 * Created by Jessica on 2016/10/26.
 */

public class LiveFragment extends J_BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        Button b = (Button) view.findViewById(R.id.btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.intentActivity(mBaseActivity, LiveActivity.class);
            }
        });
        view.findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseActivity.intentActivity(mBaseActivity, DetuAcitivity.class);
            }
        });
        return view;
    }
}
