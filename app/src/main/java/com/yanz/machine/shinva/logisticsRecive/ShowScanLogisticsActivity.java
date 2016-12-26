package com.yanz.machine.shinva.logisticsRecive;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.application.MyApplication;
import com.yanz.machine.shinva.db.DataBaseHelper;
import com.yanz.machine.shinva.db.LogisticsDaoImpl;
import com.yanz.machine.shinva.entity.SLogisticsPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ShowScanLogisticsActivity extends AppCompatActivity {
    private String uri = "/logistics/findLogistics";
    TextView tvPlanCode,tvPartCode,tvPartName,tvNum,tvWorker,tvDepartment,tvTime,tvOk ;
    DataBaseHelper dbHelper;
    String[] itemList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scan_logistics);
        Intent intent = getIntent();
        String planCode = intent.getStringExtra("planCode");
        tvPlanCode = (TextView) findViewById(R.id.tv_logistic_recive_plan_code);
        tvPlanCode.setText(planCode);
        loadData(planCode);
    }
    private void loadData(String planCode){
        String url = HttpUtil.BASE_URL + uri;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("planCode", planCode);
        MyApplication app = (MyApplication) getApplication();
        if (app.getUserInfo().getCpsCode()==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(ShowScanLogisticsActivity.this);
            builder.setTitle("ERROR");
            builder.setMessage("无法获取登录信息,请重新登录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"重新登录!",Toast.LENGTH_SHORT).show();
                }
            });
            return;

        }
        params.put("deptCode",app.getUserInfo().getCpsDepartmentCode());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //0条物流计划,提示错误
                //Toast.makeText(getApplicationContext(),"the network is error!",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowScanLogisticsActivity.this);
                builder.setTitle("ERROR");
                builder.setMessage("未查询到相关物流计划");
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowScanLogisticsActivity.this);
                String[] message = responseString.split("@@");
                String result = message[1];
                Gson gson = new Gson();

                try {
                    //包含zero@@,提示没有
                    if (responseString.contains("zero@@")){
                        builder.setTitle("ERROR");
                        builder.setMessage("未查询到相关物流计划");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Toast.makeText(getApplicationContext(),"zero!",Toast.LENGTH_SHORT).show();

                    }
                    //包含more@@,显示list选择对话框,选择后显示
                    else if (responseString.contains("more@@")){
                        //列表  //列表
                        final List<SLogisticsPlan> logisticsList ;
                        logisticsList = gson.fromJson(result,new TypeToken<List<SLogisticsPlan>>(){}.getType());
                        itemList = new String[logisticsList.size()];

                        for (int i=0;i<logisticsList.size();i++){
                            itemList[i]=(logisticsList.get(i).getCplanCode()+logisticsList.get(i).getCpartName());
                        }
                        builder.setTitle("选择需要提取的物流计划");
                        builder.setItems(itemList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"点击了"+itemList[which],Toast.LENGTH_SHORT).show();
                                initView(logisticsList.get(which));
                            }
                        });
                        builder.show();
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
                        Toast.makeText(getApplicationContext(),"more!",Toast.LENGTH_SHORT).show();
                    }
                    //包含one@@,直接显示
                    else if (responseString.contains("one@@")){
                        SLogisticsPlan sLogistics = gson.fromJson(result,SLogisticsPlan.class);
                        Toast.makeText(getApplicationContext(),"one!",Toast.LENGTH_SHORT).show();
                        initView(sLogistics);
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }
                /*catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                }
        });
    }
    private void initView(final SLogisticsPlan sLogistics){
        tvPartCode= (TextView) findViewById(R.id.tv_logistic_recive_part_code);
        tvPartName= (TextView) findViewById(R.id.tv_logistic_recive_part_name);
        tvDepartment= (TextView) findViewById(R.id.tv_logistic_recive_department);
        tvNum = (TextView) findViewById(R.id.tv_logistic_recive_num);
        tvWorker= (TextView) findViewById(R.id.tv_logistic_recive_worker);
        tvTime= (TextView) findViewById(R.id.tv_logistic_recive_time);
        tvOk= (TextView) findViewById(R.id.tv_logistic_recive_ok);
        tvPartCode.setText(sLogistics.getCpartCode());
        tvPartName.setText(sLogistics.getCpartName());
        //tvWorker.setText(app.getUserInfo().getCpsName());
        tvNum.setText(" "+sLogistics.getFquantity());
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //存入手机本地数据库中
                dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.getWritableDatabase();
                LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
                p.save(sLogistics);
                dbHelper.close();
                Log.e("meng","插入成功");
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),LogisticsMenuActivity.class);
                startActivity(intent);

            }
        });

    }
}
