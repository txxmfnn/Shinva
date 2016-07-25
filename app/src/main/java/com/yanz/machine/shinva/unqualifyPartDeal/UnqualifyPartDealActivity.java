package com.yanz.machine.shinva.unqualifyPartDeal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class UnqualifyPartDealActivity extends Activity {
    private String uri = "/unqualify/findList";
    private EditText planCode;
    private EditText partCode;
    private EditText partName;
    private EditText planer;
    private EditText startDate;
    private TextView endDate;
    String department;
    String checker;
    String technoliger;
    String result;
    @InjectView(R.id.v_unqualifyPartDeal_search_dropDownMenu)
    DropDownMenu mDropDownMenu;
    //填写查询结果适配器
    private String headers[]={
            "班组",
            "检验员",
            "处理人",
            "处理结果",
            "更多"
    };
    private List<SUnqualifyPartDeal> dealList = new ArrayList<SUnqualifyPartDeal>();
    private String results[]
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    {
        "不限", "报废", "原计划返修", "报废/让步", "新计划返修", "返修", "让步", "返修/报废",
                "新计划返修/让步", "信息计划返修/报废", "报废/返修"
    }

    ;
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter deptAdapter;
    private ListDropDownAdapter checkerAdapter;
    private ListDropDownAdapter techAdapter;
    private ListDropDownAdapter resultAdapter;

    private int constellationPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unqualify_part_deal);
        ButterKnife.inject(this);
        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        partCode = ButterKnife.findById(filterView, R.id.et_planTrack_partName);
        startDate = ButterKnife.findById(filterView, R.id.et_planTrack_startDate);
        endDate = ButterKnife.findById(filterView, R.id.et_planTrack_endDate);
        TextView ok = ButterKnife.findById(filterView, R.id.tv_planSearch_search);
        final Calendar c = Calendar.getInstance();
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(UnqualifyPartDealActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : "正在查询...");
                mDropDownMenu.closeMenu();
                loadData();
            }
        });

        popupViews.add(deptView);
        popupViews.add(checkerView);
        popupViews.add(techView);
        popupViews.add(resultView);

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

    }


}
