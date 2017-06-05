package com.yanz.machine.shinva.logisticsRecive;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.application.MyApplication;
import com.yanz.machine.shinva.db.DataBaseHelper;
import com.yanz.machine.shinva.db.LogisticsDaoImpl;
import com.yanz.machine.shinva.entity.SLogisticsPlan;
import com.yanz.machine.shinva.login.LoginActivity;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yanz.machine.shinva.util.StrUtil;


import org.codehaus.jackson.map.JsonMappingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LogisticsForActivity extends AppCompatActivity implements LogisticsPlanForAdapter.CheckedAllListener {
    private String uri ="/logistics/findDeliv";
    private final static int SCANNIN_GREQUEST_CODE = 1;
    //扫一扫功能
    private Button bt_sao;
    //页面按钮
    private Button bt_fanxuan;
    private Button bt_cancel;
    private Button bt_shu;
    private Button bt_delete;
    private CheckBox cb_all;
    private EditText et_planCode;
    //下方列表提取数据库内容
    DataBaseHelper dbHelper;
    List<SLogisticsPlan> sLogisticsPlans;
    List<SLogisticsPlan> list;
    ListView listView;
    String[] itemList;
    List<Integer> listItemId = new ArrayList<Integer>();
    //通过autoId锁定物流计划进行传递
    List<Integer> autoList = new ArrayList<Integer>();
    LogisticsPlanForAdapter adapter;

    SparseBooleanArray isCheckeds;
    public boolean flag = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
        p.deleteAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_for);

        list = new ArrayList<SLogisticsPlan>();
        sLogisticsPlans =  new ArrayList<SLogisticsPlan>();
        isCheckeds = new SparseBooleanArray();
        initView();

        adapter = new LogisticsPlanForAdapter(list,this);
        adapter.setCheckedAllListener(this);
        listView.setAdapter(adapter);
        initList();

        //反选功能
        bt_fanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //遍历list的长度,将已选的设为未选,未选的设为已选
                for (int i=0;i<list.size();i++){
                    if (LogisticsPlanForAdapter.getIsSelected().get(i)){
                        LogisticsPlanForAdapter.getIsSelected().put(i,false);
                    }else {
                        LogisticsPlanForAdapter.getIsSelected().put(i,true);
                    }
                }
                //刷新listview和textView的显示
                adapter.notifyDataSetChanged();
            }
        });
        //取消功能
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //遍历list长度,将已选的设为未选
                for (int i=0;i<list.size();i++){
                    if (LogisticsPlanForAdapter.getIsSelected().get(i)){
                        LogisticsPlanForAdapter.getIsSelected().get(i,false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void initView(){
        isCheckeds = new SparseBooleanArray();
        listItemId = new ArrayList<Integer>();
        listView = (ListView) findViewById(R.id.lv_logistics_for_list);
        cb_all = (CheckBox) findViewById(R.id.cb_logistics_for_all);
        bt_fanxuan = (Button) findViewById(R.id.btn_logistics_for_fanxuan);
        bt_cancel = (Button) findViewById(R.id.btn_logistics_for_cancel);
        bt_delete = (Button) findViewById(R.id.btn_logistics_for_delete);
        bt_shu = (Button) findViewById(R.id.btn_logistics_for_input);
        et_planCode = (EditText) findViewById(R.id.edt_logistics_for_input);
        bt_sao = (Button) findViewById(R.id.btn_logistics_for_scan);
        bt_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行二维码扫描
                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                startActivityForResult(intent,SCANNIN_GREQUEST_CODE);
            }
        });
        //删除按钮
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemId.clear();
                for (int i=0;i<adapter.getIsSelected().size();i++){
                    if (adapter.getIsSelected().get(i)){
                        listItemId.add(i);
                    }
                }
                if (listItemId.size()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsForActivity.this);
                    builder1.setTitle("ERROR");
                    builder1.setMessage("没有选中任何记录");
                    builder1.create().show();
                }else {
                    StringBuilder sb = new StringBuilder();
                    dbHelper = new DataBaseHelper(getApplicationContext());
                    dbHelper.getWritableDatabase();
                    LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
                    for (int i=0;i<listItemId.size();i++){
                        sb.append("itemId:"+listItemId.get(i)+";");
                        SLogisticsPlan item = list.get(listItemId.get(i));
                        Log.e("meng",item.getCplanCode());
                        p.deleteById(item);
                    }
                    sLogisticsPlans = p.findAll();
                    list.clear();
                    list.addAll(sLogisticsPlans);
                    adapter.notifyDataSetChanged();
                    dbHelper.close();
                    initList();
                }
            }
        });
    }
    private void initList(){
        dbHelper = new DataBaseHelper(getApplicationContext());
        LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
        sLogisticsPlans = p.findAll();
        dbHelper.close();
        if (sLogisticsPlans.size()>0){
            list.clear();
            list.addAll(sLogisticsPlans);
        }
        adapter.notifyDataSetChanged();

    }
    /**
     * 全选的点击时间
     */
    public void logisticsForAllSelect(View v){
        System.out.println("==============>>点击全选按钮"+cb_all.isChecked());
        //使用checkbox,判断checkbox状态实现全选
        if (cb_all.isChecked()){
            flag = true;
        }else {
            flag = false;
        }
        if (flag){
            for (int i=0;i<list.size();i++){
                isCheckeds.put(i,true);
                LogisticsPlanForAdapter.setIsSelected(isCheckeds);
            }
        }else {
            for (int i=0;i<list.size();i++){
                isCheckeds.put(i,false);
                LogisticsPlanForAdapter.setIsSelected(isCheckeds);
            }
        }
        adapter.notifyDataSetChanged();
    }
    /**
     * 全选按钮的回调时间,是否进行全选
     */
    @Override
    public void CheckAll(SparseBooleanArray checkall){
        int a = checkall.indexOfValue(false);
        int b = checkall.indexOfValue(true);
        System.out.println(a+"-----"+b);
        if (checkall.indexOfValue(true)<0){
            if (cb_all.isChecked()){
                this.flag = false;
                cb_all.setChecked(false);
            }
        }else if (checkall.indexOfValue(false)<0){
            if (!cb_all.isChecked()){
                this.flag = false;
                cb_all.setChecked(true);
            }
        }else if (checkall.indexOfValue(false)>=0&&checkall.indexOfValue(true)>=0){
            if (cb_all.isChecked()){
                this.flag = true;
                cb_all.setChecked(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            if (result!=null){
                //跳转页面
                Log.e("meng","执行回调函数");
                loadData(result);
            }
        }
    }
    //获取数据
    private void loadData(String planCode){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        params.put("planCode",planCode);
        MyApplication app = (MyApplication) getApplication();
        String appTest = app.getUserInfo().getCpsCode();
        if (appTest==null||appTest==""){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("ERROR");
            builder.setMessage("无法获取登录信息,请重新登录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"重新登录!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogisticsForActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.create().show();
            return;
        }
        params.put("deptCode",app.getUserInfo().getCpsDepartmentCode());
        params.put("userId",app.getUserInfo().getCpsCode());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("ERROR");
                builder.setMessage("未查询到相关物流计划");
                builder.create().show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsForActivity.this);
                String[] message = responseString.split("@@");
                String result = message[1];
                try {
                    Gson gson = new Gson();
                    if (responseString.contains("zero@@")){
                        builder.setTitle("ERROR");
                        builder.setMessage("未查询到相关物流计划");
                        builder.create().show();
                    }
                    else if (responseString.contains("more@@")){
                        final List<SLogisticsPlan> logisticsList;
                        logisticsList = gson.fromJson(result,new TypeToken<List<SLogisticsPlan>>(){}.getType());
                        itemList= new String[logisticsList.size()];
                        for (int i=0;i<logisticsList.size();i++){
                            itemList[i]=(logisticsList.get(i).getCplanCode()+"-"+logisticsList.get(i).getIgxh()+logisticsList.get(i).getCpartName()+"数量:"+logisticsList.get(i).getFquantity());
                        }
                        builder.setTitle("选择需要提取的物流计划");
                        builder.setItems(itemList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int test = logisticsList.get(which).getIautoId();
                                boolean repeat = false;
                                for (int i=0;i<list.size();i++){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsForActivity.this);
                                    builder.setTitle("提取错误");
                                    builder.setMessage("不能提取重复物流计划");
                                    builder.create().show();
                                    repeat= true;
                                }
                                if (!repeat){
                                    initView(logisticsList.get(which));
                                }
                            }

                        });
                        builder.create().show();
                    }
                    //包含one@@,直接显示
                    else if (responseString.contains("one@@")){
                        SLogisticsPlan sLogistics = gson.fromJson(result,SLogisticsPlan.class);
                        initView(sLogistics);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView(SLogisticsPlan sLogistics){
        //存入手机本地数据库中
        dbHelper = new DataBaseHelper(getApplicationContext());
        dbHelper.getWritableDatabase();
        LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
        sLogistics.setCheck(false);
        p.save(sLogistics);
        dbHelper.close();
        Log.e("meng","插入成功");
        initList();
    }
    public void logisticsGetReceive(View view){
        listItemId.clear();
        for (int i=0;i<adapter.getIsSelected().size();i++){
            if (adapter.getIsSelected().get(i)){
                listItemId.add(i);
            }
        }
        if (listItemId.size()==0){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsForActivity.this);
            builder1.setTitle("ERROR");
            builder1.setMessage("没有选中任何记录!");
            builder1.create().show();
        }else {
            StringBuilder sb = new StringBuilder();
            dbHelper = new DataBaseHelper(getApplicationContext());
            dbHelper.getWritableDatabase();
            LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
            autoList.clear();
            for (int i = 0; i < listItemId.size(); i++) {
                SLogisticsPlan item = list.get(listItemId.get(i));
                //存储相关autoId
                autoList.add(item.getIautoId());

            }
            Log.e("meng","autoId数量:"+autoList.size()+"获取第一个autoId的值"+autoList.get(0));
            recivePlan(autoList);
            dbHelper.close();

            initList();
        }
    }
    public void logisticsInputFor(View view){
        //String planCodeInput = et_planCode.getText().toString();
        if ("".equals(et_planCode.getText().toString())){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsForActivity.this);
            builder1.setTitle("ERROR");
            builder1.setMessage("请输入计划号!");
            builder1.create().show();
        }else {
            loadData(et_planCode.getText().toString());
        }
    }
    //选中物流计划实现接收
    private void recivePlan(List<Integer> autoList){
        String url = HttpUtil.BASE_URL + "/logistics/markDeliv";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String autoIds = StrUtil.ListToString(autoList);
        params.put("autoList",autoIds );

        MyApplication app = (MyApplication) getApplication();
        if (app.getUserInfo().getCpsCode()==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("ERROR");
            builder.setMessage("无法获取登录信息,请重新登录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"重新登录!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogisticsForActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.create().show();
        }
        params.put("deptCode",app.getUserInfo().getCpsDepartmentCode());
        params.put("userId",app.getUserInfo().getCpsCode());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsForActivity.this);
                builder.setTitle("ERROR");
                builder.setMessage("返回值失败哦");
                builder.create().show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsForActivity.this);
                if (responseString.contains("false@@")){
                    builder.setTitle("ERROR");
                    builder.setMessage("您的提交有误,请核对");
                    builder.create().show();
                }else if (responseString.contains("true@@")){
                    builder.setTitle("SUCCESS");
                    builder.setMessage("成功");
                    builder.create().show();
                    LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
                    p.deleteAll();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
