package com.dilidili.of.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 基类Fragment
 * Created by Jessica on 2016/10/19.
 */

public class J_BaseFragment extends Fragment {

    public static J_BaseActivity mBaseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (J_BaseActivity) context;
    }
}
