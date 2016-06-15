package com.yanz.machine.shinva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.entity.SCurrentStock;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class SearchResultActivity extends Activity {
    private String uri ="/stock/find";

    ListView lv_result;
    List<SCurrentStock> stocks = new ArrayList<SCurrentStock>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        loadResult();

    }
    public void loadResult(){
        Intent intent = getIntent();
        String materialName= intent.getStringExtra("name");
        String materialCode = intent.getStringExtra("code");

        String url = HttpUtil.BASE_URL+uri;
        System.out.println("url");
        RequestParams params = new RequestParams();
        params.put("materialCode",materialCode);
        params.put("materialName", materialName);
        AsyncHttpClient client =new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchResultActivity.this,"连接错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String msg) {
                System.out.println(statusCode);
                System.out.println(headers.toString());
                System.out.println(msg);
                try {
                    //msg = new String(msg.getBytes("iso8859-1"),"utf-8");
                    if (msg.contains("true@@")){
                        String[] message = msg.split("@@");
                        String result = message[1];
                        ObjectMapper objectMapper = new ObjectMapper();
                        stocks = objectMapper.readValue(
                                result,
                                new TypeReference<List<SCurrentStock>>() {
                                }
                        );
                        loadInfo();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    //加载信息
    public void loadInfo() throws InterruptedException{
        final ListView list = (ListView) findViewById(R.id.material_info_list);
        List<Map<String,Object>> listItems = new ArrayList<Map<String, Object>>();
        if (stocks != null&& stocks.size()>0){
            for (int i = 0;i<stocks.size();i++){
                Map<String,Object> listItem = new HashMap<String,Object>();
                SCurrentStock stock = stocks.get(i);
                listItem.put("materialName",stock.getCcsMaterialName());
                listItem.put("materialCode",stock.getCcsMaterialCode());
                listItem.put("IcsautoId",stock.getIcsautoId());
                listItems.add(listItem);
            }
            System.out.println("listItems%^$#%^&^%&&&:"+listItems);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,
                R.layout.stock_simple_item,new String[]{"materialName","materialCode","IcsautoId"},
                new int[]{R.id.materialName,R.id.materialCode,R.id.autoId});
        list.setAdapter(simpleAdapter);

    }
}
