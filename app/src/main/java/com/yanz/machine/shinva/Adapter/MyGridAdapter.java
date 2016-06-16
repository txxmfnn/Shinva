package com.yanz.machine.shinva.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanz.machine.shinva.R;

/**
 * Created by yanzi on 2016-06-16.
 */
public class MyGridAdapter extends BaseAdapter {
    private Context mContext;
    public String[] menu_text;
    public int[] menu_img;
    public MyGridAdapter(Context mContext,int[] menu_img,String[] menu_text){
        this.mContext = mContext;
        this.menu_img = menu_img;
        this.menu_text = menu_text;

    }
    @Override
    public int getCount() {
        return menu_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item,parent,false
            );
        }
        TextView tvMenuName = BaseViewHolder.get(convertView,R.id.tv_menu_item);
        ImageView ivMenuImg = BaseViewHolder.get(convertView,R.id.iv_menu_item);
        ivMenuImg.setBackgroundResource(menu_img[position]);
        tvMenuName.setText(menu_text[position]);
        return convertView;
    }
}
