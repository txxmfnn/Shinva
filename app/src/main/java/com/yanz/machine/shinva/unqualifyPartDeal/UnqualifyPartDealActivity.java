package com.yanz.machine.shinva.unqualifyPartDeal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SUnqualifyPartDeal;
import com.yanz.machine.shinva.orderinfo.OrderInfoSearchActivity;
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

public class UnqualifyPartDealActivity extends Activity {
    private String uri = "/unqualify/findList";
    private EditText planCode;
    private EditText partCode;
    private EditText partName;
    private EditText planer;
    private Button startDate;
    private Button endDate;
    private Button startMakeDate;
    private Button endMakeDate;
    ProgressDialog proDialog;
    String department;
    String checker;
    String technoliger;
    String dealResult;
    @InjectView(R.id.v_unqualifyPartDeal_search_dropDownMenu)
    DropDownMenu mDropDownMenu;
    //填写查询结果适配器
    private String headers[]={
            "班组",
            "检验员",
            "处理人",
            "结果",
            "更多"
    };
    private List<SUnqualifyPartDeal> dealList = new ArrayList<SUnqualifyPartDeal>();
    private String results[]={
        "不限", "报废", "原计划返修", "报废/让步", "新计划返修", "返修", "让步", "返修/报废",
                "新计划返修/让步", "信息计划返修/报废", "报废/返修"
    };
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter deptAdapter;
    private ListDropDownAdapter checkerAdapter;
    private ListDropDownAdapter techAdapter;
    private ListDropDownAdapter resultAdapter;

    private int constellationPosition = 0;
    private UnqualifyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unqualify_part_deal);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        final ListView deptView = new ListView(this);
        final ListView checkerView = new ListView(this);
        final ListView techView = new ListView(this);
        final ListView resultView = new ListView(this);
        deptAdapter = new ListDropDownAdapter(this, Arrays.asList(HttpUtil.DEPARTMENTS));
        deptView.setDividerHeight(0);
        deptView.setAdapter(deptAdapter);
        checkerAdapter = new ListDropDownAdapter(this, Arrays.asList(HttpUtil.CHECKERS));
        checkerView.setDividerHeight(0);
        checkerView.setAdapter(checkerAdapter);
        techAdapter = new ListDropDownAdapter(this, Arrays.asList(HttpUtil.TECHNOS));
        techView.setDividerHeight(0);
        techView.setAdapter(techAdapter);
        resultAdapter = new ListDropDownAdapter(this, Arrays.asList(results));
        resultView.setDividerHeight(0);
        resultView.setAdapter(resultAdapter);

        final View filterView = getLayoutInflater().inflate(R.layout.layout_drop_down_menu_filter, null);
        planCode = ButterKnife.findById(filterView, R.id.et_planTrack_outCode);
        planCode.setHint("计划号");
        planer = ButterKnife.findById(filterView, R.id.et_planTrack_cntNo);
        planer.setHint("计划员");
        partCode = ButterKnife.findById(filterView, R.id.et_planTrack_partCode);
        partName = ButterKnife.findById(filterView, R.id.et_planTrack_partName);
        startDate = ButterKnife.findById(filterView,R.id.bn_planTrack_startDate);
        endDate = ButterKnife.findById(filterView,R.id.bn_planTrack_endDate);
        startMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeStartDate);
        endMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeEndDate);
        TextView ok = ButterKnife.findById(filterView, R.id.tv_planSearch_search);
        final Calendar c = Calendar.getInstance();
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(UnqualifyPartDealActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog dialog = new DatePickerDialog(UnqualifyPartDealActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog dialog = new DatePickerDialog(UnqualifyPartDealActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog dialog = new DatePickerDialog(UnqualifyPartDealActivity.this, new DatePickerDialog.OnDateSetListener() {

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
                proDialog = ProgressDialog.show(UnqualifyPartDealActivity.this,"正在查询","请稍候...");
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : "正在查询...");
                mDropDownMenu.closeMenu();
                loadData();
            }
        });

        popupViews.add(deptView);
        popupViews.add(checkerView);
        popupViews.add(techView);
        popupViews.add(resultView);
        popupViews.add(filterView);
        deptView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deptAdapter.setCheckItem(i);
                String[] test = HttpUtil.DEPARTMENTS[i].split("\\|");
                if (i!=0){
                    department = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[0]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        checkerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkerAdapter.setCheckItem(i);
                String[] test = HttpUtil.CHECKERS[i].split("\\|");
                if (i!=0){
                    checker = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[1]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        techView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                techAdapter.setCheckItem(i);
                String[] test = HttpUtil.TECHNOS[i].split("\\|");
                if (i!=0){
                    technoliger = test[0];
                }
                mDropDownMenu.setTabText(i==0?headers[2]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        resultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                techAdapter.setCheckItem(i);
                dealResult = results[i];
                mDropDownMenu.setTabText(i==0?headers[3]:dealResult);
                mDropDownMenu.closeMenu();
            }
        });

        final ListView listView = new ListView(this);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter = new UnqualifyAdapter(dealList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"点击功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,listView);
    }
    private void loadData(){
        String url = HttpUtil.BASE_URL+uri;
        String planCodeText = planCode.getText().toString();
        String partCodeText = partCode.getText().toString();
        String partNameText = partName.getText().toString();
        String planerText = planer.getText().toString();

        RequestParams params = new RequestParams();
        params.put("planCode",planCodeText);
        params.put("partCode",partCodeText);
        params.put("partName",partNameText);
        params.put("departmentCode",department);
        params.put("checkCode",checker);
        params.put("technicalCode",technoliger);
        params.put("dealResult",dealResult);
        params.put("maker",planerText);
        params.put("satrtDate",startDate.getText().toString());
        params.put("endDate",endDate.getText().toString());
        params.put("startMakeDate",startMakeDate.getText().toString());
        params.put("endMakeDate",endMakeDate.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                proDialog.dismiss();
                Toast.makeText(UnqualifyPartDealActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Gson gson = new Gson();
                        List<SUnqualifyPartDeal> list;
                        list = gson.fromJson(result,new TypeToken<List<SUnqualifyPartDeal>>(){}.getType());
                        dealList.clear();
                        dealList.addAll(list);
                        adapter.notifyDataSetChanged();
                        proDialog.dismiss();
                    }else {
                        proDialog.dismiss();
                        Toast.makeText(UnqualifyPartDealActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
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
    class UnqualifyAdapter extends BaseAdapter{
        List<SUnqualifyPartDeal> sDealList;

        public UnqualifyAdapter(List<SUnqualifyPartDeal> sDealList) {
            this.sDealList = sDealList;
        }

        @Override
        public int getCount() {
            return sDealList.size();
        }

        @Override
        public Object getItem(int i) {
            return sDealList.get(i);
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
            TextView tvUnQuantity = BaseViewHolder.get(view,R.id.tv_item_other);
            TextView tvDept = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvChecker = BaseViewHolder.get(view,R.id.tv_item_report);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_code);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvFoot = BaseViewHolder.get(view,R.id.tv_item_foot);
            SUnqualifyPartDeal partDeal = sDealList.get(i);

            tvPlanCode.setText(partDeal.getCplanCode());
            tvUnQuantity.setText(" "+partDeal.getFunQualifyQuantity());
            tvDept.setText(partDeal.getCdepartmentName());
            tvChecker.setText(partDeal.getCcheckerName());
            tvPartCode.setText(partDeal.getCpartCode()+" 计划员:"+partDeal.getCplanerName());
            tvPartName.setText(partDeal.getCpartName()+"  设计:"+partDeal.getCsheji());
            tvFoot.setText(partDeal.getCtechnicalPersonName()+"  处理意见:"+partDeal.getCunqualifyDealResult()+"  责任:"+partDeal.getCresponsiblePersonName()+"|"+partDeal.getCresponsibleDepartmentName());
            return view;
        }
    }
}
