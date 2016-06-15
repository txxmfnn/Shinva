package com.yanz.machine.shinva.planSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class PlanSearch2Activity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private String planCode;
    private ViewPager mViewPager;
    private TextView tvPlanCode;
    private TextView tvQuantity;
    private TextView tvMakerName;
    private TextView tvPartCode;
    private TextView tvPartName;
    private ArrayList<Fragment> fragmentList;
    ArrayList<String> titleList = new ArrayList<String>();
    private PagerTabStrip pagerTabStrip;

    private List<SPlan> plans ;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_search2);
        Intent intent = getIntent();
        planCode = intent.getStringExtra("planCode");
        tvPlanCode = (TextView) findViewById(R.id.tv_2planCode);
        tvPlanCode.setText(planCode);
        tvMakerName = (TextView) findViewById(R.id.tv_2makerName);
        tvPartCode = (TextView) findViewById(R.id.tv_2partCode);
        tvPartName = (TextView) findViewById(R.id.tv_2partName);
        tvQuantity = (TextView) findViewById(R.id.tv_2fwpQuantity);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(this);
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.holo_orange_dark));
        pagerTabStrip.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));

        Fragment1 mfragment1 = new Fragment1(1);
        PlanDetailFragment planDetailFragment = new PlanDetailFragment();
        DispatchingInfoFragment dispatchingInfoFragment = new DispatchingInfoFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(mfragment1);
        fragmentList.add(planDetailFragment);
        fragmentList.add(dispatchingInfoFragment);

        titleList.add("111");
        titleList.add("222");
        titleList.add("333");

        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        initData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentList.get(0).setUserVisibleHint(true);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter{
        public MyViewPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

    }
    public  void initData(){
        System.out.println("开始加载数据...");

        String url = HttpUtil.BASE_URL+"/splan/find";
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(PlanSearch2Activity.this, "连接错误PlanSearchActivity", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PlanSearch2Activity.this, "数据处理错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(PlanSearch2Activity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //处理显示信息
    public void loadInfo() throws InterruptedException{

        if (plans!=null&&plans.size()>0){
            Map<String,Object> listItem = new HashMap<String, Object>();
            SPlan plan = plans.get(0);
            final SPlan splan = plan;
            PlanSearch2Activity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMakerName.setText(splan.getCwpMakerName().toString());
                    tvQuantity.setText(splan.getFwpQuantity().toString());
                    tvPartCode.setText(splan.getCwpPartCode().toString());
                    tvPartName.setText(splan.getCwpPartName().toString());
                }
            });
            /*for (int i = 0 ;i<plans.size();i++){
                listItem.put("planCode","工序:"+plan.getCwpCode());
                listItem.put("report",plan.getCwpPstatusFlag());
                listItem.put("content","|"+plan.getDwpPlanEdate().substring(0,10)+"|"+plan.getCwpDepartmentName()+"|"+plan.getCwpName());
                listItems.add(listItem);
            }*/
        }
        /*SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.item_line,new String[]{"planCode","report","content"},
                new int[]{R.id.tv_cwpCode,R.id.tv_report,R.id.tv_content});
        listView.setAdapter(simpleAdapter);*/

    }
}
