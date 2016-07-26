package com.yanz.machine.shinva.orderinfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.Adapter.GirdDropDownAdapter;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SOrderInformation;
import com.yanz.machine.shinva.planSearch.ListDropDownAdapter;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yyydjk.library.DropDownMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

public class OrderInfoSearchActivity extends Activity {
    private String uri="/orderInfo/findList";
    private EditText orderCode;
    private EditText auditer ;
    private EditText partCode;
    private EditText partName ;
    private EditText startDate ;
    private EditText endDate ;
    String factory="";
    String maker="";
    String state="";
    @InjectView(R.id.v_orderInfo_search_dropDownMenu)
    DropDownMenu mDropDownMenu;
    private OrderInfoAdapter adapter;
    private List<SOrderInformation> orderInfoList = new ArrayList<SOrderInformation>();
    private String headers[]={
            "分厂",
            "计划员",
            "订单状态",
            "更多"
    };
    private List<View> popupViews = new ArrayList<>();
    private GirdDropDownAdapter factoryAdapter;
    private ListDropDownAdapter makerAdapter;
    private ListDropDownAdapter stateAdapter;
    private String states[]={"不限","主体订单","零件订单"};
    private String factorys[]={"不限","DG|冻干技术","FL|放疗设备","GK|感控设备","XPSS|新品十四","ZY|制药设备"};

    private int constellationPosition =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_search);
        ButterKnife.inject(this);
        initView();
    }
    private void initView(){
        final ListView factoryView = new ListView(this);
        final ListView makerView = new ListView(this);
        final ListView stateView = new ListView(this);
        factoryAdapter = new GirdDropDownAdapter(this, Arrays.asList(factorys));
        factoryView.setDividerHeight(0);
        factoryView.setAdapter(factoryAdapter);


        makerView.setDividerHeight(0);
        makerAdapter = new ListDropDownAdapter(this,Arrays.asList(HttpUtil.MAKERS));
        makerView.setAdapter(makerAdapter);

        stateAdapter = new ListDropDownAdapter(this,Arrays.asList(states));
        stateView.setDividerHeight(0);
        stateView.setAdapter(stateAdapter);

        final View filterView = getLayoutInflater().inflate(R.layout.layout_drop_down_menu_filter,null);
        orderCode = ButterKnife.findById(filterView,R.id.et_planTrack_outCode);
        auditer = ButterKnife.findById(filterView,R.id.et_planTrack_cntNo);
        partCode = ButterKnife.findById(filterView,R.id.et_planTrack_partCode);
        partName = ButterKnife.findById(filterView,R.id.et_planTrack_partName);
        startDate = ButterKnife.findById(filterView,R.id.et_planTrack_startDate);
        endDate = ButterKnife.findById(filterView,R.id.et_planTrack_endDate);
        TextView ok = ButterKnife.findById(filterView,R.id.tv_planSearch_search);
        auditer.setHint("编辑员");
        //partCode.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        //partName.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        final Calendar c = Calendar.getInstance();
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(OrderInfoSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        startDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(OrderInfoSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        endDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropDownMenu.setTabText(constellationPosition==0?headers[3]:"正在查询...");
                mDropDownMenu.closeMenu();
                loadData();
            }
        });

        popupViews.add(factoryView);
        popupViews.add(makerView);
        popupViews.add(stateView);
        popupViews.add(filterView);

        factoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                factoryAdapter.setCheckItem(i);
                String[] test = factorys[i].split("\\|");
                if (i!=0){
                    factory =test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[0]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        makerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                makerAdapter.setCheckItem(i);
                String[] test = HttpUtil.MAKERS[i].split("\\|");
                if (i!=0){
                    maker = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[1]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                stateAdapter.setCheckItem(i);

                if (i!=0){
                    state = states[i];
                }
                mDropDownMenu.setTabText(i==0?headers[2]:states[i]);
                mDropDownMenu.closeMenu();
            }
        });
        //init context view
        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter = new OrderInfoAdapter(orderInfoList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SOrderInformation orderInfo = (SOrderInformation)listView.getItemAtPosition(i);
                String orderCode = orderInfo.getCorderCode();
                String factory = orderInfo.getCfactoryName();
                if (orderInfo.getIworkNeedDays()==null){
                    AlertDialog.Builder builder =new AlertDialog.Builder(OrderInfoSearchActivity.this);
                    builder.setIcon(R.drawable.logo);
                    builder.setTitle("错误:");
                    builder.setMessage("该订单尚未排产，无法查询生产计划!");
                    builder.show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("orderCode",orderCode);
                intent.putExtra("factoryName",factory);
                intent.putExtra("deliveryDate",orderInfo.getDtDeliveryDate());
                intent.putExtra("dtPlanEdate",orderInfo.getDtPlanEdate());
                intent.setClass(OrderInfoSearchActivity.this,OrderInfoResultActivity.class);
                startActivity(intent);
            }
        });
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,listView);

    }
    private void loadData(){
        String url = HttpUtil.BASE_URL+uri;
        String orderCodeText = orderCode.getText().toString();
        String auditerText = auditer.getText().toString();
        String partCodeText = partCode.getText().toString();
        String partNameText = partName.getText().toString();
        String startDateText = startDate.getText().toString();
        String endDateText = endDate.getText().toString();

        RequestParams params = new RequestParams();
        params.put("orderCode",orderCodeText);
        params.put("maker",maker);
        params.put("auditer",auditerText);
        params.put("partCode",partCodeText);
        params.put("partName",partNameText);
        params.put("startDate",startDateText);
        params.put("endDate",endDateText);
        params.put("factory",factory);
        params.put("state",state);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(OrderInfoSearchActivity.this,"暂无计划信息，请确认是否排产",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Gson gson = new Gson();
                        List<SOrderInformation> list;
                        list = gson.fromJson(result,new TypeToken<List<SOrderInformation>>(){}.getType());
                        orderInfoList.clear();
                        orderInfoList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(OrderInfoSearchActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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
    class OrderInfoAdapter extends BaseAdapter{
        List<SOrderInformation> sOrderInfoList;
        public OrderInfoAdapter(List<SOrderInformation> sOrderInfoList){
            this.sOrderInfoList = sOrderInfoList;
        }

        @Override
        public int getCount() {
            return sOrderInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return sOrderInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_line2,null);
            }
            TextView tvOrderCode = BaseViewHolder.get(view,R.id.tv_item_head);
            TextView tvQuantity = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvMaker = BaseViewHolder.get(view,R.id.tv_item_report);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_code);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvDeliveryDate = BaseViewHolder.get(view,R.id.tv_item_foot);
            SOrderInformation sOrderInformation = sOrderInfoList.get(i);
            String flowFlag="";
            int j = 10;
            if (sOrderInformation.getIflowFlag()!=null){
                j = sOrderInformation.getIflowFlag();
            }
//            工作流 0=订单员取消，1=制作订单；2=提交订单；3=接收订单；4=下达生产计划；5=关联生产计划(未启用)；6=转发订单；7=订单完工；8=退回订单(未启用)；9=计划员终止订单。
            switch (j){
                case 0:
                    flowFlag="订单员取消 ";
                    break;
                case 1:
                    flowFlag="制作订单 ";
                    break;
                case 2:
                    flowFlag="提交订单 ";
                    break;
                case 3:
                    flowFlag="接收订单 ";
                    break;
                case 4:
                    flowFlag="下达生产计划 ";
                    break;
                case 5:
                    flowFlag="关联生产计划 ";
                    break;
                case 6:
                    flowFlag="转发订单 ";
                    break;
                case 7:
                    flowFlag="订单完工 ";
                    break;
                case 8:
                    flowFlag="退回订单(未启用) ";
                    break;
                case 9:
                    flowFlag="计划员种植订单 ";
                    break;
                case 10:
                    flowFlag=" ";
                    break;
            }
            tvDeliveryDate.setText(sOrderInformation.getDtDeliveryDate()+" "+flowFlag+sOrderInformation.getIworkNeedDays()+"$"+sOrderInformation.getIdeliveryRemainDays());
            tvOrderCode.setText(sOrderInformation.getCorderCode());
            tvQuantity.setText(" "+sOrderInformation.getFquantity());
            tvMaker.setText(sOrderInformation.getCmakerName());
            tvPartCode.setText(sOrderInformation.getCpartCode());
            tvPartName.setText(sOrderInformation.getCpartName());
            if (sOrderInformation.getIdeliveryRemainDays()!=null&&sOrderInformation.getIworkNeedDays()!=null){
                if (sOrderInformation.getIdeliveryRemainDays()<sOrderInformation.getIworkNeedDays()){
                    tvDeliveryDate.setBackgroundColor(getResources().getColor(R.color.tv_Red));
                }
            }
            tvPartCode.setTextColor(getResources().getColor(R.color.tv_bgblue));
            return view;
        }
    }

}