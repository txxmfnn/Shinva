package com.yanz.machine.shinva.Setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yanz.machine.shinva.R;



public class SettingFragment extends Fragment {
    private ImageView tv_changePWD;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_setting,null);
        //initView 方法
        //inflater.inflate(R.layout.fragment_setting, container, false)
        tv_changePWD = (ImageView) mMainView.findViewById(R.id.tv_changPWD);
        tv_changePWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePWDActivity.class);
                startActivity(intent);

            }
        });
        return mMainView;
    }
}
