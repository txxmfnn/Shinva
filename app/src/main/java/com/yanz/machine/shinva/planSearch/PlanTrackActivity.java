package com.yanz.machine.shinva.planSearch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.yanz.machine.shinva.PlanSearchActivity;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yyydjk.library.DropDownMenu;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.protocol.HTTP;

public class PlanTrackActivity extends Activity {
    private String uri= "/splan/findList";
    private EditText outCode;
    private EditText cntNo ;
    private EditText partCode;
    private EditText partName ;
    private Button startDate ;
    private Button endDate ;
    private Button startMakeDate;
    private Button endMakeDate;

     String state="";
     String maker="";
     String department="";
    ProgressDialog proDialog;
    @InjectView(R.id.v_planTrack_dropDownMenu)
    DropDownMenu mDropDownMenu;
    private PlanTrackAdapter adapter;
    private List<SPlan> sPlanList= new ArrayList<SPlan>();
    private String headers[] = {
            "状态",
            "计划员",
            "班组"
            ,"更多"
    };
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter stateAdapter;
    private ListDropDownAdapter makerAdapter;
    private ListDropDownAdapter departmentAdapter;

    private String states[]= {"不限","完工","部分","脱期","在制","未汇报","未完工"};


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
        final ListView makerView = new ListView(this);
        final ListView departmentView = new ListView(this);
        stateAdapter = new ListDropDownAdapter(this, Arrays.asList(states));
        stateView.setDividerHeight(0);
        stateView.setAdapter(stateAdapter);


        makerView.setDividerHeight(0);
        makerAdapter = new ListDropDownAdapter(this,Arrays.asList(HttpUtil.MAKERS));
        makerView.setAdapter(makerAdapter);


        departmentAdapter = new ListDropDownAdapter(this,Arrays.asList(HttpUtil.DEPARTMENTS));
        departmentView.setDividerHeight(0);
        departmentView.setAdapter(departmentAdapter);

        final View filterView = getLayoutInflater().inflate(R.layout.layout_drop_down_menu_filter,null);
          outCode = ButterKnife.findById(filterView,R.id.et_planTrack_outCode);
          cntNo = ButterKnife.findById(filterView,R.id.et_planTrack_cntNo);
          partCode = ButterKnife.findById(filterView,R.id.et_planTrack_partCode);
          partName = ButterKnife.findById(filterView,R.id.et_planTrack_partName);
          startDate = ButterKnife.findById(filterView,R.id.bn_planTrack_startDate);
          endDate = ButterKnife.findById(filterView,R.id.bn_planTrack_endDate);
        startMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeStartDate);
        endMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeEndDate);

        TextView ok = ButterKnife.findById(filterView,R.id.tv_planSearch_search);
        cntNo.setHint("柜号");
        cntNo.setInputType(InputType.TYPE_CLASS_NUMBER);
        final Calendar c = Calendar.getInstance();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(PlanTrackActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        c.set(year,monthOfYear,dayOfMonth);
                        startDate.setText(DateFormat.format("yyyy-MM-dd",c));
                        endDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }

                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        startMakeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(PlanTrackActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        startDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startDate.setText(null);
                endDate.setText(null);
                return true;
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
        endDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startDate.setText(null);
                endDate.setText(null);
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
                DatePickerDialog dialog = new DatePickerDialog(PlanTrackActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        endMakeDate.setText(DateFormat.format("yyyy-MM-dd",c));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(PlanTrackActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                proDialog = ProgressDialog.show(PlanTrackActivity.this,"正在查询","请稍候...");
                mDropDownMenu.setTabText(constellationPosition==0?headers[3]:"正在查询...");
                mDropDownMenu.closeMenu();
                loadData();
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
                if (i!=0){
                    state = states[i];
                }
                mDropDownMenu.setTabText(i==0 ? headers[0]:states[i]);
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
                mDropDownMenu.setTabText(i==0?headers[1]:test[1]/*makers[i].substring(6,makers[i].length())*/);
                mDropDownMenu.closeMenu();
            }
        });
        departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                departmentAdapter.setCheckItem(i);
                String[] test = HttpUtil.DEPARTMENTS[i].split("\\|");
                if (i!=0){
                    department = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[2]:test[1]/*departments[i].substring(9,departments[i].length())*/);
                mDropDownMenu.closeMenu();
            }
        });

        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter = new PlanTrackAdapter(sPlanList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SPlan sPlan = (SPlan)listView.getItemAtPosition(i);
                String planCode = sPlan.getCwpPlanCode().substring(3);
                Intent intent = new Intent();
                intent.putExtra("planCode",planCode);
                intent.setClass(PlanTrackActivity.this, PlanSearchActivity.class);
                startActivity(intent);
            }
        });
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
            TextView tvCode = BaseViewHolder.get(view,R.id.tv_item_foot);
            SPlan sPlan = sPlanList.get(i);
            tvPlanCode.setText(sPlan.getCwpPlanCode());
            tvQuantity.setText(" "+sPlan.getFwpPlanQuantity());
            tvMaker.setText(sPlan.getCwpMakerName());
            tvPartCode.setText(sPlan.getCwpPartCode());
            tvPartName.setText(sPlan.getCwpPartName());
            tvCode.setText(sPlan.getCwpCntrNo());
            return view;
        }
    }

    private void loadData(){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        String outCodeText= outCode.getText().toString();
        String cntNoText= cntNo.getText().toString();
        String partCodeText = partCode.getText().toString();
        String partNameText = partName.getText().toString();
        String startDateText = startDate.getText().toString();
        String endDateText = endDate.getText().toString();
        params.put("outCode",outCodeText);
        params.put("cntNo",cntNoText);
        params.put("partCode",partCodeText);
        params.put("partName",partNameText);
        params.put("startDate",startDateText);
        params.put("endDate",endDateText);
        params.put("state",state);
        params.put("maker",maker);
        params.put("department",department);
        params.put("startMakeDate",startMakeDate.getText().toString());
        params.put("endMakeDate",endMakeDate.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                proDialog.dismiss();
                Toast.makeText(PlanTrackActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Gson gson = new Gson();
                        List<SPlan> list;
                        list = gson.fromJson(result,new TypeToken<List<SPlan>>(){}.getType());
                        sPlanList.clear();
                        sPlanList.addAll(list);
                        adapter.notifyDataSetChanged();
                        proDialog.dismiss();
                    }else {
                        Toast.makeText(PlanTrackActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
}
