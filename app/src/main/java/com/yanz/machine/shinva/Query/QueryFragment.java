package com.yanz.machine.shinva.Query;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanz.machine.shinva.R;



public class QueryFragment extends Fragment {

    private TextView tv_query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_query,null);
        initView(view);
        return inflater.inflate(R.layout.fragment_query, container, false);
    }
    //自定义初始化view方法,原方法使用listview，此处未定义
    private void initView(View view){

    }



}
