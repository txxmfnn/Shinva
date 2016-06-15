package com.yanz.machine.shinva.planSearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.yanz.machine.shinva.entity.SLogisticsPlan;
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


public class LogisticsPlanFragment extends Fragment {
    private String uri ="/splan/findLogistics";
    private ListView lvLogistics;
    private String planCode;
    List<SLogisticsPlan> sLogisticsPlanList = new ArrayList<SLogisticsPlan>();
    private SLogisticsPlanAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mMainView = inflater.inflate(R.layout.fragment_logistics_plan, (ViewGroup)getActivity().findViewById(R.id.pager), false);
        lvLogistics = (ListView) mMainView.findViewById(R.id.lv_logistics);
        lvLogistics.setDividerHeight(0);
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
                Toast.makeText(getActivity(), "连接错误DispatchingInfofragment", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        ObjectMapper objectMapper = new ObjectMapper();
                        sLogisticsPlanList = objectMapper.readValue(
                                result,
                                new TypeReference<List<SLogisticsPlan>>() {

                                }
                        );
                    }
                    Collections.sort(sLogisticsPlanList, new Comparator<SLogisticsPlan>() {
                        @Override
                        public int compare(SLogisticsPlan lhs, SLogisticsPlan rhs) {
                            int s1 = lhs.getIgxh();
                            int s2 = rhs.getIgxh();
                            if (s1>s2){
                                return 1;
                            }
                            return -1;
                        }
                    });
                    adapter = new SLogisticsPlanAdapter(sLogisticsPlanList);
                    lvLogistics.setAdapter(adapter);
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

    class SLogisticsPlanAdapter extends BaseAdapter{
        List<SLogisticsPlan> sLogisticsPlanList;
        public SLogisticsPlanAdapter(List<SLogisticsPlan> sLogisticsPlanList){
            this.sLogisticsPlanList = sLogisticsPlanList;
        }
        @Override
        public int getCount() {
            return sLogisticsPlanList.size();
        }

        @Override
        public Object getItem(int position) {
            return sLogisticsPlanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_line,null);
            }
            TextView tvCwpCode = (TextView) convertView.findViewById(R.id.tv_cwpCode);
            TextView tvReport = (TextView) convertView.findViewById(R.id.tv_report);
            TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);

            tvCwpCode.setText("工序:"+sLogisticsPlanList.get(position).getIgxh());
            tvReport.setText("合格:"+sLogisticsPlanList.get(position).getFquantity());
            tvContent.setText("应接收班组:"+sLogisticsPlanList.get(position).getCactReciveDepartmentName()
                +"|实际接收:"+sLogisticsPlanList.get(position).getCreciveDepartmentName()+"|"
                +sLogisticsPlanList.get(position).getCreciverName());
            return convertView;
        }
    }


}
