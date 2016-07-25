package com.yanz.machine.shinva.orderinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.PlanSearchActivity;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class OrderInfoResultActivity extends Activity {
    String uri = "/orderInfo/findPlanbyOrder";
    String outCode="";
    String factory="";
    String deliveryDate="";
    String endDate="";
    List<SPlan> sPlanList = new ArrayList<SPlan>();
    private ListView lvResult;
    private OrderInfoResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_result);
        outCode = getIntent().getStringExtra("orderCode");
        factory = getIntent().getStringExtra("factoryName");
        deliveryDate = getIntent().getStringExtra("deliveryDate");
        endDate = getIntent().getStringExtra("dtPlanEdate");
        lvResult = (ListView) findViewById(R.id.lv_orderInfoResult);
        adapter = new OrderInfoResultAdapter(sPlanList,deliveryDate,endDate,factory);
        lvResult.setAdapter(adapter);
        initData();
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SPlan sPlan = (SPlan) lvResult.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.putExtra("planCode",sPlan.getCwpPlanCode().substring(3));
                intent.setClass(OrderInfoResultActivity.this,PlanSearchActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        String url = HttpUtil.BASE_URL+uri;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("orderCode",outCode);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(OrderInfoResultActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        Log.e("meng",result);
                        Gson gson = new Gson();
                        List<SPlan> list;
                        list = gson.fromJson(result,new TypeToken<List<SPlan>>(){}.getType());
                        sPlanList.clear();
                        sPlanList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    class OrderInfoResultAdapter extends BaseAdapter{
        List<SPlan> sPlanList = new ArrayList<SPlan>();
        String factory="";
        String deliveryDate="";
        String dtPlanEdate="";
        public OrderInfoResultAdapter (List<SPlan> sPlanList,String deliveryDate,String dtPlanEdate,String factory){
            this.sPlanList = sPlanList;
            this.deliveryDate = deliveryDate;
            this.dtPlanEdate = dtPlanEdate;
            this.factory = factory;
        }
        @Override
        public int getCount() {
            return sPlanList.size();
        }
        @Override
        public Object getItem(int i) {
            return sPlanList.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = getLayoutInflater().inflate(R.layout.item_order_info_result,viewGroup,false);
            }
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_order_head);
            TextView tvOrderCode = BaseViewHolder.get(view,R.id.tv_item_order_outCode);
            TextView tvMaker = BaseViewHolder.get(view,R.id.tv_item_order_maker);
            TextView tvQuantity = BaseViewHolder.get(view,R.id.tv_item_order_quantity);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_order_partName);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_order_partCode);
            TextView tvFactory = BaseViewHolder.get(view,R.id.tv_item_order_factory);
            TextView tvDeliveryDate = BaseViewHolder.get(view,R.id.tv_item_order_deliveryDate);
            TextView tvEndDate = BaseViewHolder.get(view,R.id.tv_item_code_order_endDate);

            SPlan sPlan = sPlanList.get(i);
            tvPartCode.setText(sPlan.getCwpPartCode());
            tvPlanCode.setText(sPlan.getCwpPlanCode());
            tvOrderCode.setText(sPlan.getCwpOutPlanCode());
            tvMaker.setText(sPlan.getCwpMakerName());
            tvQuantity.setText(" "+sPlan.getFwpPlanQuantity());
            tvPartName.setText(sPlan.getCwpPartName());
            tvFactory.setText(factory);
            tvDeliveryDate.setText(deliveryDate);
            tvEndDate.setText(dtPlanEdate);

            return view;
        }
    }
}
