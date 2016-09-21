package com.yanz.machine.shinva.unqualifyPartDeal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
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
import com.yanz.machine.shinva.entity.SUnqualifyPartDeal;
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
    int pageNumber=1;
    boolean isLastRow =false;
    String department;
    String checker;
    String technoliger;
    String dealResult;
    @InjectView(R.id.v_unqualifyPartDeal_search_dropDownMenu)
    DropDownMenu mDropDownMenu;
    //填写查询结果适配器
    private String headers[]={
            "班组",
            "计划员",
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
        checkerAdapter = new ListDropDownAdapter(this, Arrays.asList(HttpUtil.MAKERS));
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
        planer.setHint("处理号");
        partCode = ButterKnife.findById(filterView, R.id.et_planTrack_partCode);
        partName = ButterKnife.findById(filterView, R.id.et_planTrack_partName);
        partName.setInputType(InputType.TYPE_CLASS_TEXT);
        startDate = ButterKnife.findById(filterView,R.id.bn_planTrack_startDate);
        endDate = ButterKnife.findById(filterView,R.id.bn_planTrack_endDate);
        startMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeStartDate);
        endMakeDate = ButterKnife.findById(filterView,R.id.bn_planTrack_makeEndDate);
        TextView ok = ButterKnife.findById(filterView, R.id.tv_planSearch_search);
        final Calendar c = Calendar.getInstance();
        startDate.setHint("检验日期起始");
        endDate.setHint("检验日期截止");
        startMakeDate.setHint("处理日期起始");
        endMakeDate.setHint("处理日期截止");
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
                proDialog.setCancelable(true);
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[4] : "正在查询...");
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
                    department = test[1];
                }
                mDropDownMenu.setTabText(i==0?headers[0]:test[1]);
                mDropDownMenu.closeMenu();
            }
        });
        checkerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkerAdapter.setCheckItem(i);
                String[] test = HttpUtil.MAKERS[i].split("\\|");
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
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SUnqualifyPartDeal deal = (SUnqualifyPartDeal)listView.getItemAtPosition(position);
                String code = deal.getCwlcode();
                Intent intent = new Intent();
                intent.putExtra("cwlCode",code);
                intent.setClass(UnqualifyPartDealActivity.this, QualifyItemActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isLastRow&&scrollState==SCROLL_STATE_IDLE){
                    pageNumber=pageNumber+1;
                    loadMoreData();
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int itemCount, int totalCount) {
                int lastItemId = listView.getLastVisiblePosition();
                if ((lastItemId+1)==totalCount){
                    if (totalCount>0&&totalCount>9){
                        isLastRow = true;
                    }
                }
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
        pageNumber=1;
        RequestParams params = new RequestParams();
        params.put("pageNum",pageNumber);
        params.put("planCode",planCodeText);
        params.put("partCode",partCodeText);
        params.put("partName",partNameText);
        params.put("departmentCode",department);
        params.put("checkCode",checker);
        params.put("technicalCode",technoliger);
        params.put("dealResult",dealResult);
        params.put("wlCode",planerText);
        params.put("startDate",startDate.getText().toString());
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
    private void loadMoreData(){
        proDialog = ProgressDialog.show(UnqualifyPartDealActivity.this,"正在查询","请稍候...");
        proDialog.setCancelable(true);
        String url = HttpUtil.BASE_URL+uri;
        String planCodeText = planCode.getText().toString();
        String partCodeText = partCode.getText().toString();
        String partNameText = partName.getText().toString();
        String planerText = planer.getText().toString();
        RequestParams params = new RequestParams();
        params.put("pageNum",pageNumber);
        params.put("planCode",planCodeText);
        params.put("partCode",partCodeText);
        params.put("partName",partNameText);
        params.put("departmentCode",department);
        params.put("checkCode",checker);
        params.put("technicalCode",technoliger);
        params.put("dealResult",dealResult);
        params.put("maker",planerText);
        params.put("startDate",startDate.getText().toString());
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
            TextView tvWlCodeText = BaseViewHolder.get(view,R.id.tv_item_head);//处理号标签
            TextView tvWlCode = BaseViewHolder.get(view,R.id.tv_item_other);//处理号
            TextView tvPlanCodeText = BaseViewHolder.get(view,R.id.tv_item_num);//计划号标签
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_report);//计划号
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_code);//图号
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);//图名
            TextView tvUnNum = BaseViewHolder.get(view,R.id.tv_item_foot);//不合格数量
            LinearLayout llCheck = BaseViewHolder.get(view,R.id.ll_item_position);//检验行
            TextView tvChecker = BaseViewHolder.get(view,R.id.tv_item_position);//检验员
            TextView tvCheckerText = BaseViewHolder.get(view,R.id.tv_item_position_text);//检验员标签
            TextView tvCheckDateText = BaseViewHolder.get(view,R.id.tv_item_time_text);//检验时间标签
            TextView tvCheckDate = BaseViewHolder.get(view,R.id.tv_item_time);//检验时间
            //调整显示
            llCheck.setVisibility(View.VISIBLE);
            tvCheckDateText.setVisibility(View.VISIBLE);
            tvCheckDate.setVisibility(View.VISIBLE);
            tvWlCodeText.setText("处理号:");
            tvWlCodeText.setTextColor(getResources().getColor(R.color.tv_Gray));
            tvPlanCodeText.setText("计划号:");
            tvPlanCodeText.setTextColor(getResources().getColor(R.color.tv_Gray));
            tvCheckDateText.setText("检验日期:");
            tvCheckerText.setText("检验员:");
            SUnqualifyPartDeal partDeal = sDealList.get(i);

            tvPlanCode.setText(partDeal.getCplanCode());
            tvPlanCode.setTextColor(getResources().getColor(R.color.tv_bgblue));
            tvWlCode.setText(partDeal.getCwlcode());
            tvWlCode.setTextColor(getResources().getColor(R.color.tv_Black));
            tvCheckDate.setText(partDeal.getDcheckDate());
            tvChecker.setText(partDeal.getCcheckerName());
            tvPartCode.setText(partDeal.getCpartCode());
            tvPartName.setText(partDeal.getCpartName());
            tvUnNum.setText(" "+partDeal.getFunQualifyQuantity());
            tvUnNum.setTextColor(getResources().getColor(R.color.tv_Red));
            return view;
        }
    }
}
