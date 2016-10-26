package com.dilidili.of.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dilidili.of.R;
import com.dilidili.of.base.J_BaseFragment;

/**
 * 关注
 * Created by Jessica on 2016/10/26.
 */

public class FocusFragment extends J_BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lv, container, false);
        return view;
    }
}
