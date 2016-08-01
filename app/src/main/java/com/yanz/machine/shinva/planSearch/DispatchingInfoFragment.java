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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SDispachingSecond;
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


public class DispatchingInfoFragment extends Fragment {
    private String uri ="/splan/findDispaching";
    private ListView lvDispatching;
    private String planCode;
    List<SDispachingSecond> itemList = new ArrayList<SDispachingSecond>();
    List<SDispachingSecond> sDispachingSeconds = new ArrayList<SDispachingSecond>();
    private DispatchingInfoAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.fragment_dispatching_info, (ViewGroup) getActivity().findViewById(R.id.pager), false);
        lvDispatching = (ListView) mMainView.findViewById(R.id.lv_dispatching);
        lvDispatching.setDividerHeight(0);
        adapter = new DispatchingInfoAdapter(itemList);
        lvDispatching.setAdapter(adapter);
        return mMainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planCode = getActivity().getIntent().getStringExtra("planCode");
        initData();
    }

    protected void initData(){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(getActivity(), "连接错误DispatchingInfofragment", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        ObjectMapper objectMapper = new ObjectMapper();
                        sDispachingSeconds = objectMapper.readValue(
                                result,
                                new TypeReference<List<SDispachingSecond>>() {
                                }
                        );
                    }
                    itemList.clear();
                    itemList.addAll(sDispachingSeconds);
                    adapter.notifyDataSetChanged();
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

    class DispatchingInfoAdapter extends BaseAdapter{
        final int VIEW_TYPE= 0;
        final int TYPE_ALL = 1;
        final int TYPE_SAME = 2;
        List<SDispachingSecond> sDispachingSeconds = new ArrayList<SDispachingSecond>();
        public DispatchingInfoAdapter(List<SDispachingSecond> sDispachingSeconds){
            this.sDispachingSeconds = sDispachingSeconds;
        }

        @Override
        public int getItemViewType(int position) {
            if (position==0){
                return TYPE_ALL;
            }
            int fXh = sDispachingSeconds.get(position-1).getIgxh();
            int sXh = sDispachingSeconds.get(position).getIgxh();
            if (fXh==sXh){
                return TYPE_SAME;
            }else{
                return TYPE_ALL;
            }
        }

        @Override
        public int getCount() {
            return sDispachingSeconds.size();
        }

        @Override
        public Object getItem(int position) {
            return sDispachingSeconds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (convertView == null){
                switch (type){
                    case TYPE_ALL:
                        convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line,null);
                        TextView tvCwpCode = (TextView) convertView.findViewById(R.id.tv_cwpCode);
                        TextView tvReport = (TextView) convertView.findViewById(R.id.tv_report);
                        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                        //加载数据
                        tvCwpCode.setText("工序:"+sDispachingSeconds.get(position).getIgxh());
                        tvReport.setText(sDispachingSeconds.get(position).getCfinisherName());
                        tvContent.setText(sDispachingSeconds.get(position).getCmemo()+sDispachingSeconds.get(position).getCdepartmentName()
                                +"|计划:"+sDispachingSeconds.get(position).getDtPlanEdate().substring(0,10)
                                +"\n完工:"+sDispachingSeconds.get(position).getFfinishQuantity()
                                +"|派工:"+sDispachingSeconds.get(position).getFquantity()
                                +"\n"+sDispachingSeconds.get(position).getDtMakeDate().substring(0,16));
                        break;
                    case TYPE_SAME:
                        convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line_same,null);
                        TextView tvReport2 = (TextView) convertView.findViewById(R.id.tv_same_report);
                        TextView tvContent2 = (TextView)convertView.findViewById(R.id.tv_same_content);
                        tvReport2.setText(sDispachingSeconds.get(position).getCfinisherName());
                        tvContent2.setText(sDispachingSeconds.get(position).getCmemo()+sDispachingSeconds.get(position).getCdepartmentName()
                                +"|计划:"+sDispachingSeconds.get(position).getDtPlanEdate().substring(0,10)
                                +"\n完工:"+sDispachingSeconds.get(position).getFfinishQuantity()
                                +"|派工:"+sDispachingSeconds.get(position).getFquantity()+"\n"+sDispachingSeconds.get(position).getDtMakeDate().substring(0,16));
                        break;
                }
            }else {
                switch (type){
                    case TYPE_ALL:
                        convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line,null);
                        TextView tvCwpCode = (TextView) convertView.findViewById(R.id.tv_cwpCode);
                        TextView tvReport = (TextView) convertView.findViewById(R.id.tv_report);
                        TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
                        //加载数据
                        tvCwpCode.setText("工序:"+sDispachingSeconds.get(position).getIgxh());
                        tvReport.setText(sDispachingSeconds.get(position).getCfinisherName());
                        tvContent.setText(sDispachingSeconds.get(position).getCmemo()+sDispachingSeconds.get(position).getCdepartmentName()
                                +"|计划:"+sDispachingSeconds.get(position).getDtPlanEdate().substring(0,10)
                                +"\n完工:"+sDispachingSeconds.get(position).getFfinishQuantity()
                                +"|派工:"+sDispachingSeconds.get(position).getFquantity()
                                +"\n"+sDispachingSeconds.get(position).getDtMakeDate().substring(0,16));
                        break;
                    case TYPE_SAME:
                        convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line_same,null);
                        TextView tvReport2 = (TextView) convertView.findViewById(R.id.tv_same_report);
                        TextView tvContent2 = (TextView)convertView.findViewById(R.id.tv_same_content);
                        tvReport2.setText(sDispachingSeconds.get(position).getCfinisherName());
                        tvContent2.setText(sDispachingSeconds.get(position).getCmemo()+sDispachingSeconds.get(position).getCdepartmentName()
                                +"|计划:"+sDispachingSeconds.get(position).getDtPlanEdate().substring(0,10)
                                +"\n完工:"+sDispachingSeconds.get(position).getFfinishQuantity()
                                +"|派工:"+sDispachingSeconds.get(position).getFquantity()+"\n"+sDispachingSeconds.get(position).getDtMakeDate().substring(0,16));
                        break;
                }
            }

            return convertView;
        }
    }



}
