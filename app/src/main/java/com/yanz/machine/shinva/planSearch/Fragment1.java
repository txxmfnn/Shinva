package com.yanz.machine.shinva.planSearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Fragment1 extends Fragment {
    private View mMainView;

    private ProgressBar pro;
    private int mIndex;
    List<SPlan> sPlanList = new ArrayList<SPlan>();
    private ListView lvSPlans ;
    private boolean isPrepared;
    private String planCode;
    String uri = "/splan/find";
    private SPlanAdapter adapter;


    public Fragment1(int index) {
        // Required empty public constructor
        this.mIndex = index;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("yanz","第一个fragment的oncreate方法");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment_fragment1, (ViewGroup) getActivity().findViewById(R.id.viewPager),false);

        pro = (ProgressBar) mMainView.findViewById(R.id.pro);
        lvSPlans = (ListView) mMainView.findViewById(R.id.lv_planDetail);
        planCode = getActivity().getIntent().getStringExtra("planCode");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p!=null){
            p.removeAllViewsInLayout();
        }
        isPrepared = true;
        return mMainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("yanz","第一个fragment的onactivitycreated方法创建");
        load();
        //lvSPlans.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIndex==1){
            setUserVisibleHint(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.v("yanz","fragment1-setUservisibleHint()"+isVisibleToUser);
        if (isVisibleToUser){
            if (pro!=null){
                pro.setVisibility(View.GONE);
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    private void load(){
        initializeAdapter();
       /* SPlan s1 = new SPlan();
        s1.setCwpCode(100);
        s1.setCwpPstatusFlag("测试孟");
        s1.setDwpPlanEdate("2016-06-16");
        s1.setCwpDepartmentName("信息组");
        s1.setCwpName("测试使用的我操来");
        sPlanList.add(s1);
        Log.d("yanz","fragment列表长度"+sPlanList.size());
        adapter = new SPlanAdapter(sPlanList);*/

    }
    private void initializeAdapter(){
        String url = HttpUtil.BASE_URL + uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("yanz","asynchttp错误");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    String[] message = responseString.split("@@");
                    String result = message[1];
                    ObjectMapper objectMapper = new ObjectMapper();
                    sPlanList = objectMapper.readValue(
                            result,
                            new TypeReference<List<SPlan>>() {

                            }

                    );
                    SPlan s1 = new SPlan();
                    s1.setCwpCode(100);
                    s1.setCwpPstatusFlag("测试孟");
                    s1.setDwpPlanEdate("2016-06-16");
                    s1.setCwpDepartmentName("信息组");
                    s1.setCwpName("测试使用的我操来");
                    sPlanList.add(s1);
                    Log.d("yanz","fragment列表长度"+sPlanList.size());
                    adapter = new SPlanAdapter(sPlanList);
                    lvSPlans.setAdapter(adapter);
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    class SPlanAdapter extends BaseAdapter{
        List<SPlan> sPlanList;
        public SPlanAdapter(List<SPlan> sPlanList){
            this.sPlanList = sPlanList;
        }
        @Override
        public int getCount() {
            return sPlanList.size();
        }

        @Override
        public Object getItem(int position) {
            return sPlanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line,null);

            }
            TextView tvCwpCode = (TextView) convertView.findViewById(R.id.tv_cwpCode);
            tvCwpCode.setText("工序:"+sPlanList.get(position).getCwpCode());
            TextView tvReport = (TextView) convertView.findViewById(R.id.tv_report);
            tvReport.setText(sPlanList.get(position).getCwpPstatusFlag());
            TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            tvContent.setText(sPlanList.get(position).getCwpName());
            return convertView;
        }
    }
}
