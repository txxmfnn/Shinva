package com.yanz.machine.shinva.planSearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanz.machine.shinva.R;


public class AllPlanFragment extends Fragment {

    private View mMainView;
    private TextView tv_planAll_dingdanCode;
    public AllPlanFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_all_plan,(ViewGroup) getActivity().findViewById(R.id.pager),false);
        /*tv_planAll_test = (TextView) mMainView.findViewById(R.id.tv_planAll_test);
        tv_planAll_test.setText("加载成功");*/

        return mMainView;
    }
}
