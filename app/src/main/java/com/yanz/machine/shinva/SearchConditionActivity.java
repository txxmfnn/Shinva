package com.yanz.machine.shinva;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.entity.SCurrentStock;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchConditionActivity extends Activity {
    private String uri = "/stock/findStock";
    private EditText materialName;
    private EditText partCode;
    private AutoCompleteTextView partStd;
    private Button wHome;
    private Button bnSearch;
    private ListView lvResult;
    int pageNumber = 1;
    boolean isLastRow = false;
    ProgressDialog proDialog;
    List<SCurrentStock> stocks = new ArrayList<SCurrentStock>();
    List<SCurrentStock> list = new ArrayList<SCurrentStock>();
    private StockAdapter adapter;
    //设置仓库数据的数据
    final String[] wHomes = {
            "011101|不锈钢板材库",
            "011102|不锈钢型材库",
            "011103|黑材库",
            "011104|外购件库",
            "011105|耗材库",
            "011106|焊材库",
            "011107|刀具库",
            "011121|半成品库",
            ""
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_condition);
        materialName = (EditText) findViewById(R.id.et_stockSearch_materialName);
        partStd = (AutoCompleteTextView) findViewById(R.id.actv_StockSearch_ptd);
        wHome = (Button) findViewById(R.id.bn_stockSearch_whome);
        partCode = (EditText) findViewById(R.id.et_stockSearch_partCode);
        bnSearch = (Button) findViewById(R.id.bn_stockSearch_search);
        lvResult = (ListView) findViewById(R.id.lv_stockSearch_result);
        adapter = new StockAdapter(this,stocks);
        lvResult.setAdapter(adapter);
        materialName.requestFocus();
        //加载物料规格
        List<String> stdList = new ArrayList<String>();
        stdList.add("φ");
        stdList.add("δ");
        stdList.add("&");
        stdList.add("ъ");
        stdList.add("@");
        stdList.add("1Cr18Ni9");
        stdList.add("0Cr18Ni9");
        stdList.add("1Cr18Ni9Ti");
        stdList.add("316L(ASME标准)");
        stdList.add("QSn6.5-0.1");
        stdList.add("QAL9-4φ");
        stdList.add("GGr15");
        stdList.add("9CrSi");
        stdList.add("W18Cr4V");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.autolist_item,stdList);
        partStd.setAdapter(stringArrayAdapter);
        partStd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AutoCompleteTextView autoView = (AutoCompleteTextView) view;
                if (b){
                    autoView.showDropDown();
                }
            }
        });
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchConditionActivity.this,"点击功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
        lvResult.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (isLastRow&&scrollState==SCROLL_STATE_IDLE){
                    pageNumber = pageNumber+1;
                    loadMoreDate();
                    isLastRow = false;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int itemCount, int totalCount) {
                int lastItemId = lvResult.getLastVisiblePosition();
                if ((lastItemId+1)==totalCount){
                    if (totalCount>0&&totalCount>9){
                        isLastRow = true;
                    }
                }
            }
        });

    }
    public void wHomeClick(View view){
        int id = view.getId();
        if (R.id.bn_stockSearch_whome == id){
            AlertDialog.Builder builder = new AlertDialog.Builder(SearchConditionActivity.this);
            builder.setIcon(R.drawable.logo);
            builder.setTitle("选择仓库:");
            builder.setItems(wHomes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    wHome.setText(wHomes[i]);
                }
            });
            builder.show();
        }
    }
    public void search(View view){
        int id = view.getId();
        if (R.id.bn_stockSearch_search == id){
            proDialog = ProgressDialog.show(SearchConditionActivity.this,"正在查询","请稍候...");
            loadData();
        }
    }
    private void loadData(){
        String name = materialName.getText().toString();
        String code = partStd.getText().toString();
        String whName = wHome.getText().toString();
        if (whName!=null&&whName!=""){
            whName = whName.substring(0,6);
        }
        pageNumber=1;
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("partStd",code);
        params.put("materialName",name);
        params.put("whCode",whName);
        params.put("partCode",partCode.getText().toString());
        params.put("pageNum",pageNumber);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                proDialog.dismiss();
                Toast.makeText(SearchConditionActivity.this,"链接错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        String[] message = responseString.split("@@");
                        String result = message[1];
                        if (responseString.contains("true@@")){
                            Gson gson = new Gson();
                            list = gson.fromJson(result,new TypeToken<List<SCurrentStock>>(){}.getType());
                            stocks.clear();
                            stocks.addAll(list);
                            adapter.notifyDataSetChanged();
                            proDialog.dismiss();
                        }else {
                            proDialog.dismiss();
                            Toast.makeText(SearchConditionActivity.this,result,Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        });
    }
    private void loadMoreDate(){
        proDialog = ProgressDialog.show(SearchConditionActivity.this,"正在查询","请稍候...");
        proDialog.setCancelable(true);
        String name = materialName.getText().toString();
        String code = partStd.getText().toString();
        String whName = wHome.getText().toString();
        if (whName!=null&&whName!=""){
            whName = whName.substring(0,6);
        }
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("pageNum",pageNumber);
        params.put("partStd",code);
        params.put("materialName",name);
        params.put("whCode",whName);
        params.put("partCode",partCode.getText().toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                proDialog.dismiss();
                Toast.makeText(SearchConditionActivity.this,"链接错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    String[] message = responseString.split("@@");
                    String result = message[1];
                    if (responseString.contains("true@@")){
                        Gson gson = new Gson();
                        list = gson.fromJson(result,new TypeToken<List<SCurrentStock>>(){}.getType());
                        stocks.addAll(list);
                        adapter.notifyDataSetChanged();
                        proDialog.dismiss();
                    }else {
                        proDialog.dismiss();
                        Toast.makeText(SearchConditionActivity.this,result,Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    class StockAdapter extends BaseAdapter{
        Context mContext;
        List<SCurrentStock> list;
        public StockAdapter(Context mContext, List<SCurrentStock> list){
            this.list = list;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_line2,viewGroup,false);
            }
            TextView tvWhName = BaseViewHolder.get(view,R.id.tv_item_head);
            TextView tvQuantity = BaseViewHolder.get(view,R.id.tv_item_num);
            TextView tvPartName = BaseViewHolder.get(view,R.id.tv_item_mid);
            TextView tvPartStd = BaseViewHolder.get(view,R.id.tv_item_foot);
            TextView tvPositionText = BaseViewHolder.get(view,R.id.tv_item_position_text);
            TextView tvPosition = BaseViewHolder.get(view,R.id.tv_item_position);
            TextView tvBottomQuantity = BaseViewHolder.get(view,R.id.tv_item_down);
            TextView tvUpQuantity = BaseViewHolder.get(view,R.id.tv_item_up);
            TextView tvCode = BaseViewHolder.get(view,R.id.tv_item_code);
            LinearLayout llPosition = BaseViewHolder.get(view,R.id.ll_item_position);
            LinearLayout llUpDown = BaseViewHolder.get(view,R.id.ll_item_upDown);
            llUpDown.setVisibility(View.VISIBLE);
            llPosition.setVisibility(View.VISIBLE);
            SCurrentStock stock = list.get(i);
            tvWhName.setText(stock.getCcsWhName());
            tvPartName.setText(stock.getCcsPartName());
            tvPartStd.setText(stock.getCcsPartStd());
            tvQuantity.setText(" "+stock.getFcsQuantity());
            tvPositionText.setText("货位:");
            tvPosition.setText(stock.getCcsPosition());
            tvCode.setText(stock.getCcspartCode());
            tvBottomQuantity.setText("↓"+stock.getFcsBottomQuantity());
            tvUpQuantity.setText("↑"+stock.getFcsTopQuantity());
            tvUpQuantity.setTextColor(getResources().getColor(R.color.tv_Red));
            tvBottomQuantity.setTextColor(getResources().getColor(R.color.tv_Red));
            tvQuantity.setTextColor(getResources().getColor(R.color.tv_bgblue));
            if (stock.getFcsQuantity()!=null&&stock.getFcsBottomQuantity()!=null&&stock.getFcsTopQuantity()!=null){
                if (stock.getFcsBottomQuantity()>stock.getFcsQuantity()||stock.getFcsTopQuantity()<stock.getFcsQuantity()){
                    tvQuantity.setTextColor(getResources().getColor(R.color.red));
                }
            }
            return view;
        }
    }
}
