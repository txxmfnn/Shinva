package com.yanz.machine.shinva.logisticsRecive;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SLogisticsPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanz on 2017-05-26.
 */

public class LogisticsPlanForAdapter extends BaseAdapter {
    List<SLogisticsPlan> list = new ArrayList<SLogisticsPlan>();
    private static SparseBooleanArray isSelected;
    Context context;
    HolderView holderView = null;

    /**
     * 全选回调接口
     */
    CheckedAllListener mListener;
    public void setCheckedAllListener(CheckedAllListener listener){
        mListener = listener;
    }
    public LogisticsPlanForAdapter(List<SLogisticsPlan> list,Context context){
        this.context = context;
        this.list = list;
        isSelected = new SparseBooleanArray();
        initData();
    }
    /**
     * 初始化数据
     * 默认选中状态
     */
    private void initData(){
        for (int i=0;i<list.size();i++){
            getIsSelected().put(i,true);
        }
    }
    public static SparseBooleanArray getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(SparseBooleanArray isSelected) {
        LogisticsPlanForAdapter.isSelected = isSelected;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view==null){
            holderView = new HolderView();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_logistics_recive,parent,false);
            holderView.cb_button= (CheckBox) view.findViewById(R.id.cb_logistics_recive_checkBox);
            holderView.tv_code = (TextView) view.findViewById(R.id.tv_logistic_recive_list_plan_code);
            holderView.tv_name = (TextView) view.findViewById(R.id.tv_logistic_recive_list_part_name);
            holderView.tv_num = (TextView) view.findViewById(R.id.tv_logistic_recive_list_quantity);
            holderView.tv_gxh = (TextView) view.findViewById(R.id.tv_logistic_recive_list_gxh);
            view.setTag(holderView);
        }
        else {
            holderView = (HolderView) view.getTag();
        }
        //initData();

        final SLogisticsPlan item = (SLogisticsPlan) getItem(position);
        if (item!=null){
            holderView.tv_name.setText(" "+item.getCpartName());
            holderView.tv_code.setText(" "+item.getCplanCode());
            holderView.tv_num.setText(" "+item.getFquantity());
            holderView.tv_gxh.setText(" "+item.getIgxh());
            boolean is = isSelected.get(position);
            holderView.cb_button.setChecked(is);
            /*holderView.cb_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected.get(position)){
                        isSelected.put(position,false);
                        setIsSelected(isSelected);
                    }else {
                        isSelected.put(position,true);
                        setIsSelected(isSelected);
                    }
                }
            });*/

        }else {
            Log.e("meng","查看item的值:"+item.getCpartName());
            holderView.tv_name.setText(" "+item.getCpartName());
            holderView.tv_code.setText(" "+item.getCplanCode());
            holderView.tv_num.setText(" "+item.getFquantity());
            holderView.tv_gxh.setText(" "+item.getIgxh());
            boolean is = isSelected.get(position);
            holderView.cb_button.setChecked(is);
        }
        /**
         * 增加checkbox的改变时间,每个item的点击事件
         */
        holderView.cb_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()){
                    isSelected.put(position,isChecked);
                    mListener.CheckAll(isSelected);
                }
                //添加slogisticsPlan中添加checkbox的属性
                item.setCheck(isChecked);
            }
        });
        return view;
    }
    class HolderView{
        private CheckBox cb_button;
        private TextView tv_name;
        private TextView tv_code;
        private TextView tv_num;
        private TextView tv_gxh;
    }
    /**
     * 当所有checkbox全选时回调
     */
    public interface CheckedAllListener{
        void CheckAll(SparseBooleanArray checkall);
    }
}
