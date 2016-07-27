package com.yanz.machine.shinva.rdRecord;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SRdRecord;
import com.yanz.machine.shinva.planSearch.ListDropDownAdapter;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

public class RdRecordOutActivity extends Activity {
    private String uri="/rdRecord/findInList";
    private EditText planCode;
    private EditText partCode;
    private EditText partName;
    private EditText cCode;
    private EditText startDate;
    private EditText endDate;
    String dept="";
    String whCode="";
    @InjectView(R.id.v_rdRecord_out_dropDownMenu)
    DropDownMenu mDropDownMenu;
    private String headers[]={
            "仓库",
            "班组",
            "更多"
    };
    private List<SRdRecord> recordList = new ArrayList<SRdRecord>();
    private List<View> popupviews = new ArrayList<>();
    private ListDropDownAdapter deptAdapter;
    private ListDropDownAdapter whomeAdapter;

    private int constellationPosition =0;
    private RdRecordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_record_out);
        ButterKnife.inject(this);
        initView();
    }
    private void initView(){
        final ListView deptView = new ListView(this);
        final ListView whomeView = new ListView(this);
        deptAdapter = new ListDropDownAdapter(this, Arrays.asList(HttpUtil.DEPARTMENTS));
        deptView.setDividerHeight(0);
        deptView.setAdapter(deptAdapter);
        whomeAdapter = new ListDropDownAdapter(this,Arrays.asList(HttpUtil.WHOMES));
        whomeView.setDividerHeight(0);
        whomeView.setAdapter(whomeAdapter);

        final View filterView = getLayoutInflater().inflate(R.layout.layout_drop_down_menu_filter,null);
        planCode = ButterKnife.findById(filterView,R.id.et_planTrack_outCode);
        planCode.setHint("计划号");
        cCode = ButterKnife.findById(filterView,R.id.et_planTrack_cntNo);
        cCode.setHint("出库单号");
        partCode = ButterKnife.findById(filterView,R.id.et_planTrack_partCode);
        partName = ButterKnife.findById(filterView,R.id.et_planTrack_partName);
        startDate = ButterKnife.findById(filterView, R.id.et_planTrack_startDate);
        endDate = ButterKnife.findById(filterView, R.id.et_planTrack_endDate);
        TextView ok = ButterKnife.findById(filterView, R.id.tv_planSearch_search);
        final Calendar c = Calendar.getInstance();
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordOutActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        startDate.setText(DateFormat.format("yyyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordOutActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        endDate.setText(DateFormat.format("yyyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[2] : "正在查询...");
                mDropDownMenu.closeMenu();
                loadData();
            }
        });
        popupviews.add(whomeView);
        popupviews.add(deptView);
        popupviews.add(filterView);
        deptView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deptAdapter.setCheckItem(i);
                String[] test = HttpUtil.DEPARTMENTS[i].split("\\|");
                if (i!=0){
                    dept = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[0]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        whomeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                whomeAdapter.setCheckItem(i);
                String[] test = HttpUtil.WHOMES[i].split("\\|");
                if (i!=0){
                    whCode=test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[0]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });

        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter = new RdRecordAdapter(recordList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"如需点击功能,请联系信息组...", Toast.LENGTH_SHORT).show();
            }
        });
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupviews,listView);
    }
    private void loadData(){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode.getText().toString());
        params.put("partCode",partCode.getText().toString());
        params.put("partName",partName.getText().toString());
        params.put("whCode",whCode);
        params.put("Code",cCode.getText().toString());
        params.put("dept",dept);
        params.put("startDate",startDate.getText().toString());
        params.put("endDate",endDate.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(RdRecordOutActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Log.e("meng",result);
                        Gson gson = new Gson();
                        List<SRdRecord> list;
                        list = gson.fromJson(result,new TypeToken<List<SRdRecord>>(){}.getType());
                        recordList.clear();
                        recordList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(RdRecordOutActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
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
    class RdRecordAdapter extends BaseAdapter {
        List<SRdRecord> sRdRecords;

        public RdRecordAdapter(List<SRdRecord> sRdRecords) {
            this.sRdRecords = sRdRecords;
        }

        @Override
        public int getCount() {
            return sRdRecords.size();
        }

        @Override
        public Object getItem(int i) {
            return sRdRecords.get(i);
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
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_head);
            TextView tvWhHome = BaseViewHolder.get(view,R.id.tv_item_other);
            TextView tvInNum = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvPosition = BaseViewHolder.get(view,R.id.tv_item_report);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_code);
            TextView tvHome = BaseViewHolder.get(view,R.id.tv_item_foot);
            SRdRecord record = sRdRecords.get(i);

            tvPlanCode.setText(record.getCplanCode());
            tvWhHome.setText(record.getCwhName());
            tvInNum.setText(" "+record.getFquantity());
            tvInNum.setTextColor(getResources().getColor(R.color.tv_Red));
            tvPosition.setText(record.getCposition()+record.getFcurrentStock());
            tvPosition.setTextColor(getResources().getColor(R.color.tv_bgblue));
            tvPartName.setText(record.getCpartName()+record.getDdate().substring(0,10));
            tvPartCode.setText(record.getCpartCode()+record.getCdepartmentName()+record.getCpersonName());
            tvHome.setText(record.getCcode()+record.getCmakerName());
            return view;
        }
    }
}
