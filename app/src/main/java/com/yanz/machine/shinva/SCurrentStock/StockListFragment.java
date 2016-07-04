package com.yanz.machine.shinva.SCurrentStock;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.StickHeaderListFragment;
import com.yanz.machine.shinva.R;


public class StockListFragment extends StickHeaderListFragment {

    public static StockListFragment newInstance(){
        StockListFragment fragment = new StockListFragment();
        return fragment;
    }
    public static StockListFragment newInstance(String title){
        StockListFragment fragment = new StockListFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void bindData() {
        setAdapter();
    }
    public void setAdapter(){
        if (getActivity()==null){
            return;
        }
        
    }

}
