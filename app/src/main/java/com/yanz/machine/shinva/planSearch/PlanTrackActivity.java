package com.yanz.machine.shinva.planSearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlanTrackActivity extends Activity {
    @InjectView(R.id.v_planTrack_dropDownMenu)
    DropDownMenu mDropDownMenu;
    private PlanTrackAdapter adapter;
    private List<SPlan> sPlanList= new ArrayList<SPlan>();
    private String headers[] = {
            "状态",
            "计划员",
            "班组"
            ,"筛选"
    };
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter stateAdapter;
    private ListDropDownAdapter makerAdapter;
    private ListDropDownAdapter departmentAdapter;

    private String states[]= {"不限","完工计划","部分计划","脱期计划","在制计划","未汇报计划","未完工计划"};
    private String makers[]={"不限","11687|董强","11598|傅强","11012|孙红云","11873|孙培霖","11503|王栋","11601|杨建强","张镇","朱小明"};
    private String departments[]={"不限","01110405|焊工一班","01110406|焊工二班","01110410|钣金","01110411|配料","01110413|机加工","01110415|组焊班","01110416|零件班","01110420|电抛光班","0111045301|一部半成品仓库"};

    private int constellationPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_track);
        ButterKnife.inject(this);
        initView();
    }
    private void initView(){
        final ListView stateView = new ListView(this);
        stateAdapter = new ListDropDownAdapter(this, Arrays.asList(states));
        stateView.setDividerHeight(0);
        stateView.setAdapter(stateAdapter);

        final ListView makerView = new ListView(this);
        makerView.setDividerHeight(0);
        makerAdapter = new ListDropDownAdapter(this,Arrays.asList(makers));
        makerView.setAdapter(makerAdapter);

        final ListView departmentView = new ListView(this);
        departmentAdapter = new ListDropDownAdapter(this,Arrays.asList(departments));
        departmentView.setDividerHeight(0);
        departmentView.setAdapter(departmentAdapter);

        final View filterView = getLayoutInflater().inflate(R.layout.layout_drop_down_menu_filter,null);
        TextView ok = ButterKnife.findById(filterView,R.id.tv_planSearch_search);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropDownMenu.setTabText(constellationPosition==0?headers[3]:"正在查询...");
                mDropDownMenu.closeMenu();
            }
        });

        popupViews.add(stateView);
        popupViews.add(makerView);
        popupViews.add(departmentView);
        popupViews.add(filterView);

        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stateAdapter.setCheckItem(i);
                mDropDownMenu.setTabText(i==0 ? headers[0]:states[i]);
                mDropDownMenu.closeMenu();
            }
        });
        makerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                makerAdapter.setCheckItem(i);
                mDropDownMenu.setTabText(i==0?headers[1]:makers[i]);
                mDropDownMenu.closeMenu();
            }
        });
        departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                departmentAdapter.setCheckItem(i);
                mDropDownMenu.setTabText(i==0?headers[2]:departments[i]);
                mDropDownMenu.closeMenu();
            }
        });

        ListView listView = new ListView(this);
        SPlan s1 = new SPlan();
        s1.setCwpPartCode("215.52165.454");
        s1.setCwpPartName("图名测试");
        s1.setCwpPlanCode("575258");
        s1.setFwpQuantity(200.0);
        s1.setCwpMakerName("孟天翔");
        sPlanList.add(s1);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));;
        adapter = new PlanTrackAdapter(sPlanList);
        listView.setAdapter(adapter);

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,listView);
    }

    @Override
    public void onBackPressed() {
        //退出前关闭菜单
        if (mDropDownMenu.isShowing()){
            mDropDownMenu.closeMenu();
        }else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
    class PlanTrackAdapter extends BaseAdapter{
        List<SPlan> sPlanList;
        public PlanTrackAdapter(List<SPlan> sPlanList){
            this.sPlanList = sPlanList;
        }
        @Override
        public int getCount() {
            return sPlanList.size();
        }

        @Override
        public Object getItem(int i) {
            return sPlanList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
//                view = getLayoutInflater().inflate(R.layout.item_line2,null);
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_line2,null);
            }
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_head);
            TextView tvQuantity = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvMaker = BaseViewHolder.get(view,R.id.tv_item_report);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_code);

            SPlan sPlan = sPlanList.get(i);
            tvPlanCode.setText(sPlan.getCwpPlanCode());
            tvQuantity.setText(" "+sPlan.getFwpPlanQuantity());
            tvMaker.setText(sPlan.getCwpMakerName());
            tvPartCode.setText(sPlan.getCwpPartCode());
            tvPartName.setText(sPlan.getCwpPartName());

            return view;
        }
    }
}
