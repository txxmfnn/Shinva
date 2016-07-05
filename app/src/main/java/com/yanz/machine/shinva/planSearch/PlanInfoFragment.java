package com.yanz.machine.shinva.planSearch;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
@SuppressLint("ValidFragment")
public class PlanInfoFragment extends Fragment {

    private String uri = "/splan/find";
    private int mIndex;
    private View mMainView;
    List<SPlan> plans = new ArrayList<SPlan>();

    private ListView listView;

    private  String planCode;
    private List<Map<String,Object>> listItems;
    public PlanInfoFragment(int index){
        this.mIndex = index;
    }
    public PlanInfoFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment_plan_info, (ViewGroup) getActivity().findViewById(R.id.pager),false);
    }
    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView)getView().findViewById(R.id.lv_cwpList);
        listView.setDividerHeight(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*View view = inflater.inflate(R.layout.fragment_plan_info, container, false);*/
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p!=null){
            p.removeAllViewsInLayout();
        }
        planCode = getActivity().getIntent().getStringExtra("planCode");
        initData();
        return mMainView;
    }
    public  void initData(){

        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "连接错误", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "数据处理错误", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //处理显示信息
    public void loadInfo() throws InterruptedException{
        listItems = new ArrayList<Map<String, Object>>();
        if (plans!=null&&plans.size()>0){
            for (int i = 0 ;i<plans.size();i++){
                Map<String,Object> listItem = new HashMap<String, Object>();
                SPlan plan = plans.get(i);
                listItem.put("planCode","工序:"+plan.getCwpCode());
                listItem.put("report",plan.getCwpPstatusFlag());
                listItem.put("content","|"+plan.getDwpPlanEdate().substring(0,10)+"|"+plan.getCwpDepartmentName()+"|"+plan.getCwpName());
                listItems.add(listItem);
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),listItems,
                R.layout.item_line,new String[]{"planCode","report","content"},
                new int[]{R.id.tv_cwpCode,R.id.tv_report,R.id.tv_content});
        listView.setAdapter(simpleAdapter);
        //getScrollView().setAdapter(simpleAdapter);
    }






}
