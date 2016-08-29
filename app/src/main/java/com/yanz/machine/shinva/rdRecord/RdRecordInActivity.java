package com.yanz.machine.shinva.rdRecord;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.yanz.machine.shinva.unqualifyPartDeal.UnqualifyPartDealActivity;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;

public class RdRecordInActivity extends Activity {
    private String uri="/rdRecord/findInList";
    private EditText planCode;
    private EditText partCode;
    private EditText partName;
    private EditText cCode;
    private Button startDate;
    private Button endDate;
    private Button startMakeDate;
    private Button endMakeDate;
    ProgressDialog proDialog;
    int pageNumber=1;
    boolean isLastRow = false;
    String dept="";
    String whCode="";
    @InjectView(R.id.v_rdRecord_search_dropDownMenu)
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
        setContentView(R.layout.activity_rd_record_in);
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
        cCode.setHint("入库单号");
        partCode = ButterKnife.findById(filterView,R.id.et_planTrack_partCode);
        partName = ButterKnife.findById(filterView,R.id.et_planTrack_partName);
        startDate = ButterKnife.findById(filterView, R.id.bn_planTrack_startDate);
        endDate = ButterKnife.findById(filterView, R.id.bn_planTrack_endDate);
        startMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeStartDate);
        endMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeEndDate);
        TextView ok = ButterKnife.findById(filterView, R.id.tv_planSearch_search);
        startMakeDate.setVisibility(View.GONE);
        endMakeDate.setVisibility(View.GONE);
        final Calendar c = Calendar.getInstance();
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordInActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        startDate.setText(DateFormat.format("yyyy-MM-dd", c));
                        endDate.setText(DateFormat.format("yyyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordInActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        endDate.setText(DateFormat.format("yyyy-MM-dd", c));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        startDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startDate.setText(null);
                endDate.setText(null);
                return true;
            }
        });
        endDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startDate.setText(null);
                endDate.setText(null);
                return true;
            }
        });
        startMakeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordInActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        startMakeDate.setText(DateFormat.format("yyyy-MM-dd",c));
                        endMakeDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        startMakeDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startMakeDate.setText(null);
                endMakeDate.setText(null);
                return true;
            }
        });
        endMakeDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startMakeDate.setText(null);
                endMakeDate.setText(null);
                return true;
            }
        });
        endMakeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(RdRecordInActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        endMakeDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[2] : "正在查询...");
                mDropDownMenu.closeMenu();
                pageNumber=1;
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
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isLastRow&&scrollState==SCROLL_STATE_IDLE){
                    pageNumber=pageNumber+1;
                    loadData();
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView,int firstItem, int itemCount, int totalCount) {
                int lastItemId = listView.getLastVisiblePosition();
                if ((lastItemId+1)==totalCount){
                    if (totalCount>0&&totalCount>9){
                        isLastRow = true;
                    }
                }
            }
        });
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupviews,listView);
    }
    private void loadData(){
        proDialog = ProgressDialog.show(RdRecordInActivity.this,"正在查询","请稍候...");
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("pageNum",pageNumber);
        params.put("planCode",planCode.getText().toString());
        params.put("partCode",partCode.getText().toString());
        params.put("partName",partName.getText().toString());
        params.put("whCode",whCode);
        params.put("Code",cCode.getText().toString());
        params.put("dept",dept);
        params.put("startDate",startDate.getText().toString());
        params.put("endDate",endDate.getText().toString());
        params.put("startMakeDate",startMakeDate.getText().toString());
        params.put("endMakeDate",endMakeDate.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                proDialog.dismiss();
                Toast.makeText(RdRecordInActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Gson gson = new Gson();
                        List<SRdRecord> list;
                        list = gson.fromJson(result,new TypeToken<List<SRdRecord>>(){}.getType());
                        if (pageNumber==1){
                            recordList.clear();
                        }
                        recordList.addAll(list);
                        adapter.notifyDataSetChanged();
                        proDialog.dismiss();
                    }else {
                        proDialog.dismiss();
                        Toast.makeText(RdRecordInActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
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

    class RdRecordAdapter extends BaseAdapter{
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
            TextView tvCode = BaseViewHolder.get(view,R.id.tv_item_head);//入库单号
            TextView tvWhHome = BaseViewHolder.get(view,R.id.tv_item_other);//仓库
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_num);//入库方式
            TextView tvMaker = BaseViewHolder.get(view,R.id.tv_item_report);//保管员
            TextView tvPartStd = BaseViewHolder.get(view,R.id.tv_item_foot);//编码
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);//图名
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_code);//图号
            LinearLayout llPosition = BaseViewHolder.get(view,R.id.ll_item_position);//货位行
            TextView tvPosition = BaseViewHolder.get(view,R.id.tv_item_position);//货位
            TextView tvPositionText = BaseViewHolder.get(view,R.id.tv_item_position_text);//货位标签
            LinearLayout llIn = BaseViewHolder.get(view,R.id.ll_item_men);//入库信息行`
            TextView tvDept = BaseViewHolder.get(view,R.id.tv_item_maker);//入库班组
            TextView tvDeptText = BaseViewHolder.get(view,R.id.tv_item_maker_text);//入库班组标签
            TextView tvWorkerText = BaseViewHolder.get(view,R.id.tv_item_auditor_text);//入库人标签
            TextView tvWorker = BaseViewHolder.get(view,R.id.tv_item_auditor);//入库人
            TextView tvDateText = BaseViewHolder.get(view,R.id.tv_item_finish_text);//入库时间标签
            TextView tvDate = BaseViewHolder.get(view,R.id.tv_item_finish);//入库时间
            LinearLayout llNum = BaseViewHolder.get(view,R.id.ll_item_upDown);//数量行
            TextView tvNumText = BaseViewHolder.get(view,R.id.tv_item_up_text);//入库数量标签
            TextView tvNum = BaseViewHolder.get(view,R.id.tv_item_up);//入库数量
            TextView tvNumBeforeText = BaseViewHolder.get(view,R.id.tv_item_down_text);//入库前数量标签
            TextView tvNumBefore = BaseViewHolder.get(view,R.id.tv_item_down);//入库前数量
            SRdRecord record = sRdRecords.get(i);
            //显示行
            llPosition.setVisibility(View.VISIBLE);
            llIn.setVisibility(View.VISIBLE);
            llNum.setVisibility(View.VISIBLE);

            tvCode.setVisibility(View.VISIBLE);
            tvCode.setText(record.getCcode());
            tvPlanCode.setText(record.getCplanCode());
            tvWhHome.setTextColor(getResources().getColor(R.color.tv_Black));
            tvWhHome.setText(record.getCwhName());

            tvMaker.setText(record.getCmakerName());
            tvPartStd.setText(record.getCpartStd());
            tvPartName.setText(record.getCpartName());
            tvPartCode.setText(record.getCpartCode());
            tvPositionText.setText("货位:");
            tvPosition.setText(record.getCposition());
            tvDeptText.setText("班组:");
            tvDept.setText(record.getCdepartmentName());
            tvWorkerText.setText("入库人:");
            tvWorker.setText(record.getCpersonName());
            tvDateText.setText(" ");
            tvDate.setText(record.getDdate());
            tvNum.setText(" "+record.getFquantity());
            tvNumText.setText("入库数量:");
            tvNumBeforeText.setText("入库前数量:");
            tvNumBefore.setText(" "+record.getFcurrentStock());
            tvNum.setTextColor(getResources().getColor(R.color.tv_Red));
            tvPosition.setTextColor(getResources().getColor(R.color.tv_bgblue));
            return view;
        }
    }
}
