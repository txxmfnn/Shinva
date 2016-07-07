package com.yanz.machine.shinva.planSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.yanz.machine.shinva.util.ClickUtil;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.map.ser.std.ToStringSerializer;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InputPlanActivity extends Activity {
    private String uri = "/splan/find";
    private Context mContext;
    private ImageView ivDeleteText;
    private EditText etPlanCode;
    private Button btSearch;
    private ListView lvResult;
    private List<SPlan> sPlans = new ArrayList<SPlan>();
    private InputPlanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_plan);
        ivDeleteText = (ImageView) findViewById(R.id.iv_inputPlan_deleteText);
        etPlanCode = (EditText) findViewById(R.id.et_inputPlan_search);
        btSearch = (Button) findViewById(R.id.bt_inputPlan_search);
        lvResult = (ListView) findViewById(R.id.lv_inputPlan_result);
        adapter = new InputPlanAdapter(sPlans);
        lvResult.setAdapter(adapter);
        ivDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPlanCode.setText("");
            }
        });
        etPlanCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    ivDeleteText.setVisibility(View.GONE);
                }else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });
        btSearch.setOnClickListener(new ClickUtil() {
            @Override
            protected void onNoDoubleClick(View view) {
                initData();
            }
        });
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SPlan sPlan = (SPlan) lvResult.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.setClass(InputPlanActivity.this, PlanSearchActivity.class);
                intent.putExtra("planCode",sPlan.getCwpPlanCode().substring(3));
                startActivity(intent);
            }
        });
    }
    public void initData(){
        Log.i("xiu","开始加载数据");
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",etPlanCode.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(InputPlanActivity.this,"请检查网络配置情况", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    String[] message = responseString.split("@@");
                    String result = message[1];
                    Gson gson = new Gson();
                    List<SPlan> list;
                    list = gson.fromJson(result,new TypeToken<List<SPlan>>(){}.getType());
                    Intent intent = new Intent();
                    intent.setClass(InputPlanActivity.this,PlanSearchActivity.class);
                    intent.putExtra("planCode",list.get(0).getCwpPlanCode().substring(3));
                    startActivity(intent);
                    /*sPlans.clear();
                    sPlans.addAll(list);
                    adapter.notifyDataSetChanged();*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    class InputPlanAdapter extends BaseAdapter{
        List<SPlan> planList;
        public InputPlanAdapter(List<SPlan> planList){
            this.planList = planList;
        }

        @Override
        public int getCount() {
            return planList.size();
        }

        @Override
        public Object getItem(int i) {
            return planList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.item_line2,viewGroup,false);
            }
            TextView tvPlanCode = BaseViewHolder.get(view,R.id.tv_item_head);
            TextView tvQuantity = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvPerson = BaseViewHolder.get(view,R.id.tv_item_report);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvPartCode = BaseViewHolder.get(view,R.id.tv_item_foot);
            SPlan sPlan = planList.get(i);
            tvPlanCode.setText(sPlan.getCwpPlanCode());
            tvQuantity.setText(" "+sPlan.getFwpPlanQuantity());
            tvPerson.setText(sPlan.getCwpMakerName()+" 工艺人:"+sPlan.getCwpSjr());
            tvPartName.setText(sPlan.getCwpPartName());
            tvPartCode.setText(sPlan.getCwpPartCode());
            return view;
        }
    }
}

