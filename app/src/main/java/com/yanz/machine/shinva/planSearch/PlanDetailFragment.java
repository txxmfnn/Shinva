package com.yanz.machine.shinva.planSearch;


import android.content.Context;
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
import com.yanz.machine.shinva.entity.SPlanDetail;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class PlanDetailFragment extends Fragment {



    private String uri = "/splan/findDetail";

    private ProgressBar pro;
    private boolean isPrepared;
    List<SPlanDetail> sPlanDetails = new ArrayList<SPlanDetail>();
    private ListView lvPlanDetail;
    private String planCode;
    private PlanDetailAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        planCode = getActivity().getIntent().getStringExtra("planCode");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        //adapter = new PlanDetailAdapter(sPlanDetails);
        //lvPlanDetail.setAdapter(adapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        View mMainView = inflater.inflate(R.layout.fragment_plan_detail, (ViewGroup) getActivity().findViewById(R.id.pager),false);
        pro = (ProgressBar) mMainView.findViewById(R.id.pro_planDetail);
        lvPlanDetail = (ListView) mMainView.findViewById(R.id.lv_planDetail);
        lvPlanDetail.setDividerHeight(0);
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p!=null){
            p.removeAllViewsInLayout();
        }

        isPrepared = true;
        return mMainView;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (pro!=null){
                //initializeAdapter();


                pro.setVisibility(View.GONE);
            }

        }
    }

    protected void initData(){
        Log.e("yanz","开始加载数据");
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String msg) {
                try {
                    if (msg.contains("true@@")){
                        String[] message = msg.split("@@");
                        String result = message[1];
                        ObjectMapper objectMapper = new ObjectMapper();
                        sPlanDetails = objectMapper.readValue(
                                result,
                                new TypeReference<List<SPlanDetail>>() {

                                }
                        );
                    }
                    Collections.sort(sPlanDetails, new Comparator<SPlanDetail>() {
                        @Override
                        public int compare(SPlanDetail lhs, SPlanDetail rhs) {
                            int s1 = lhs.getCwpCode();
                            int s2 = rhs.getCwpCode();
                            if (s1>s2){
                                return 1;
                            }
                            return -1;
                        }
                    });

                            adapter = new PlanDetailAdapter(sPlanDetails);
                            //adapter.notifyDataSetChanged();
                            lvPlanDetail.setAdapter(adapter);
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
    class PlanDetailAdapter extends BaseAdapter{
        List<SPlanDetail> sPlanDetails;
        public PlanDetailAdapter(List<SPlanDetail> sPlanDetails){
            this.sPlanDetails = sPlanDetails;
        }
        @Override
        public int getCount() {
            return sPlanDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return sPlanDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.plan_detail_item, null);
            }
            //
            TextView tvCwpCode = (TextView) convertView.findViewById(R.id.tv_planDetail_cwpCode);
            TextView tvReport = (TextView) convertView.findViewById(R.id.tv_planDetail_report);
            TextView tvContent = (TextView) convertView.findViewById(R.id.tv_planDetail_content);

            tvCwpCode.setText("工序:"+sPlanDetails.get(position).getCwpCode());
            tvReport.setText(sPlanDetails.get(position).getCwpDepartmentName().substring(0,3));

            tvContent.setText("加工人:"+sPlanDetails.get(position).getCwpFinisherName()
                    +"|合格:"+sPlanDetails.get(position).getFwpFinishQuantity()
                    +"|品质:"+sPlanDetails.get(position).getCwpQuality()
                    +"\n|完成时间:"+sPlanDetails.get(position).getDwpReportDate().substring(0,16));
            return convertView;
        }
    }




}
