package com.yanz.machine.shinva;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.planSearch.AllPlanFragment;
import com.yanz.machine.shinva.planSearch.DispatchingInfoFragment;
import com.yanz.machine.shinva.planSearch.LogisticsPlanFragment;
import com.yanz.machine.shinva.planSearch.PlanDetailFragment;
import com.yanz.machine.shinva.planSearch.PlanInfoFragment;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class PlanSearchActivity extends FragmentActivity implements View.OnClickListener {

    private String uri = "/splan/find";
    List<SPlan> plans = new ArrayList<SPlan>();
    private TextView tv_planCode;
    private  String planCode;
    //以后统一使用item作为加载数据list
    private List<Map<String,Object>> listItems;
    private TextView tv_partCode;
    private TextView tv_partName;
    private TextView tv_fwpQuantity;
    private TextView tv_makerName;
    private ViewPager viewPager ;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private MainPagerAdapter mainPagerAdapter;
    private TextView mPlanInfo;
    private TextView mPlanDetail;
    private TextView mDispatching;
    private TextView mLogisticPlan;
    private TextView mPlanAll;
    /*@Override
    protected void onResume() {
        super.onResume();
        fragments.get(4).setUserVisibleHint(true);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_plan_search);
        Intent intent = getIntent();
        planCode = intent.getStringExtra("planCode");
        tv_planCode = (TextView) findViewById(R.id.tv_planCode);
        tv_partCode = (TextView) findViewById(R.id.tv_partCode);
        tv_partName = (TextView) findViewById(R.id.tv_partName);
        tv_fwpQuantity = (TextView) findViewById(R.id.tv_fwpQuantity);
        tv_makerName = (TextView) findViewById(R.id.tv_makerName);
        tv_planCode.setText(planCode);
        viewPager = (ViewPager) findViewById(R.id.pager);
        findViewById(R.id.id_ll_planAll).setOnClickListener(this);
        mPlanAll = (TextView)findViewById(R.id.id_tv_planAll);//综合信息
        findViewById(R.id.id_ll_planInfo).setOnClickListener(this);
        mPlanInfo = (TextView) findViewById(R.id.id_tv_planInfo);//计划信息
        findViewById(R.id.id_ll_planDetail).setOnClickListener(this);
        mPlanDetail = (TextView) findViewById(R.id.id_tv_planDetail);//派工明细
        findViewById(R.id.id_ll_dispatching).setOnClickListener(this);
        mDispatching = (TextView) findViewById(R.id.id_tv_dispathing);//汇报明细
        findViewById(R.id.id_ll_logisticPlan).setOnClickListener(this);
        mLogisticPlan = (TextView) findViewById(R.id.id_tv_logisticPlan);//物流明细
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                changeSelectedState(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        fragments.add(new AllPlanFragment());
        fragments.add(new PlanInfoFragment(1));
        //fragments.add(new DispatchingInfoFragment());
        fragments.add(new DispatchingInfoFragment());
        fragments.add(new PlanDetailFragment());
        fragments.add(new LogisticsPlanFragment());
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setOffscreenPageLimit(5);//改变预加载页面
        viewPager.setAdapter(mainPagerAdapter);
        changeSelectedState(0);
        initData();
    }
    public  void initData(){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(PlanSearchActivity.this, "连接错误PlanSearchActivity", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String msg) {
                if (statusCode == 200) {
                    try {
                        if (msg.contains("true@@")) {
                            String[] message = msg.split("@@");
                            String result = message[1];
                            ObjectMapper objectMapper = new ObjectMapper();
                            plans = objectMapper.readValue(
                                    result,
                                    new TypeReference<List<SPlan>>() {
                                    }
                            );
                            loadInfo();
                        } else {
                            Toast.makeText(PlanSearchActivity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(PlanSearchActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //处理显示信息
    public void loadInfo() throws InterruptedException{
        listItems = new ArrayList<Map<String, Object>>();
        if (plans!=null&&plans.size()>0){
            Map<String,Object> listItem = new HashMap<String, Object>();
            SPlan plan = plans.get(0);
            final SPlan splan = plan;
            PlanSearchActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_makerName.setText(splan.getCwpMakerName().toString());
                    tv_fwpQuantity.setText(splan.getFwpPlanQuantity().toString());
                    tv_partCode.setText(splan.getCwpPartCode().toString());
                    tv_partName.setText(splan.getCwpPartName().toString());
                }
            });
        }
    }
    @Override
    @SuppressLint("Recycle")
    public void onClick(View v) {
        int currentPosition = 0;
        switch (v.getId()){
            case R.id.id_ll_planInfo://计划信息
                currentPosition = 0 ;
                break;
            case R.id.id_ll_dispatching://派工明细
                currentPosition = 1;
                break;
            case R.id.id_ll_planDetail://汇报明细
                currentPosition = 2;
                break;
            case R.id.id_ll_logisticPlan://物流明细
                currentPosition = 3;
                break;
            case R.id.id_ll_planAll://综合信息
                currentPosition = 4;
                break;
        }
        changeSelectedState(currentPosition);
        viewPager.setCurrentItem(currentPosition, false);
    }
    class MainPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList) {
            super(fragmentManager);
            this.fragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void changeSelectedState(int currentPosition){
        Resources resources = getResources();
        mPlanInfo.setTextColor(currentPosition == 0 ? resources.getColor(R.color.text_color_press) : resources.getColor(R.color.text_color_normal));

        mPlanDetail.setTextColor(currentPosition == 2 ? resources.getColor(R.color.text_color_press) : resources.getColor(R.color.text_color_normal));

        mDispatching.setTextColor(currentPosition == 1 ? resources.getColor(R.color.text_color_press):resources.getColor(R.color.text_color_normal));

        mLogisticPlan.setTextColor(currentPosition == 3 ? resources.getColor(R.color.text_color_press) : resources.getColor(R.color.text_color_normal));

        mPlanAll.setTextColor(currentPosition==4 ? resources.getColor(R.color.text_color_press):resources.getColor(R.color.text_color_normal));
    }



}
