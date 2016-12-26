package com.yanz.machine.shinva.logisticsRecive;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;



import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yanz.machine.shinva.Adapter.LogisticsPlanAdapter;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.application.MyApplication;
import com.yanz.machine.shinva.db.DataBaseHelper;
import com.yanz.machine.shinva.db.LogisticsDaoImpl;
import com.yanz.machine.shinva.entity.SLogisticsPlan;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yanz.machine.shinva.util.ListViewForScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LogisticsMenuActivity extends AppCompatActivity {
    private String uri = "/logistics/findLogistics";
    private final static int SCANNIN_GREQUEST_CODE = 1;
    //扫一扫功能
    private Button bt_sao;
    //页面按钮
    private Button bt_shu;
    private Button bt_delete;
    private Button bt_all;
    //下方列表提取数据库内容
    DataBaseHelper dbHelper;
    List<SLogisticsPlan> sLogisticsPlans;
    List<SLogisticsPlan> sLogisticsPlanList;
    ListViewForScrollView listView;

    String[] itemList ;
    List<Integer> listItemId = new ArrayList<Integer>();
    //checkBox声明
//    LogisticsPlanAdapter adapter;
    SLogisticsPlanAdapter adapter;

    SparseBooleanArray isCheckeds;
    //判断全选是否按下
    boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_menu);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        sLogisticsPlans = new ArrayList<SLogisticsPlan>();
        sLogisticsPlanList = new ArrayList<SLogisticsPlan>();
        initView();
        initList();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //执行扫描或输入工单提交操作
//                listItemId.clear();
                for (int i=0;i<sLogisticsPlans.size();i++){
                    if (LogisticsPlanAdapter.getIsSelected().get(i)){
//                        listItemId.add(i);
                        LogisticsPlanAdapter.getIsSelected().put(i,false);

                    }else {
                        LogisticsPlanAdapter.getIsSelected().put(i,true);
                    }
                }
                adapter.notifyDataSetChanged();
                if (listItemId.size()==0){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LogisticsMenuActivity.this);
                    builder1.setMessage("没有选中记录");
                    builder1.show();
                }else {
                    StringBuilder sb = new StringBuilder();
                    for (int i=0;i<listItemId.size();i++){
                        sb.append("itemId="+listItemId.get(i)+".");
                    }
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(LogisticsMenuActivity.this);
                    builder2.setMessage(sb.toString());
                    builder2.show();
                }

            }
        });
        //fab跟随listview效果
        fab.attachToListView(listView);
    }
    //显示list
    private void initList(){
        //测试
        SLogisticsPlan test = new SLogisticsPlan();
        //检测check的状态
        for (int i=0;i<10;i++){
            test.setCheck(false);
            test.setCpartName(i+"测试");
            test.setCplanCode(i+"code");
            test.setFquantity(100.0);
            sLogisticsPlanList.add(test);
        }
        adapter = new SLogisticsPlanAdapter(LogisticsMenuActivity.this,sLogisticsPlans,R.layout.item_logistics_recive);
        listView.setAdapter(adapter);
        Log.e("meng","列表查询执行完毕");


                if (sLogisticsPlanList.size()>0){
                    sLogisticsPlans.clear();
                    sLogisticsPlans.addAll(sLogisticsPlanList);
                    adapter.notifyDataSetChanged();
                }
    }
    //初始化
    private void initView(){
        bt_all = (Button) findViewById(R.id.bn_logistics_all);
        bt_shu = (Button) findViewById(R.id.bn_logistics_input);
        bt_delete = (Button) findViewById(R.id.bn_logistics_delete);
        bt_sao = (Button)findViewById(R.id.bn_logistics_sao);
        bt_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转二维码扫描界面
                Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
                startActivityForResult(intent,SCANNIN_GREQUEST_CODE);
            }
        });
        //checkBox
        isCheckeds = new SparseBooleanArray();
        listView = (ListViewForScrollView) findViewById(R.id.lv_logistics_receive);

    }



    @Override
    protected void onResume() {
        super.onResume();
//        initList();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            if (result!=null){
                Log.e("meng","执行回调函数");
                loadData(result);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭此activity后删除数据库中存储信息
        Log.e("meng","activity销毁");
//        LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
//        p.deleteAll();
    }

    class SLogisticsPlanAdapter extends BaseAdapter{

        List<SLogisticsPlan> sLogisticsPlanList;
        private Context context = null;
        private LayoutInflater inflater = null;
        private int resource;
        public SLogisticsPlanAdapter(Context context,List<SLogisticsPlan> sLogisticsPlanList,int resource){
            this.sLogisticsPlanList = sLogisticsPlanList;
            this.context = context;
            this.resource = resource;

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

            if (convertView == null){
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_logistics_recive,null);
            }
            TextView planCode = (TextView) convertView.findViewById(R.id.tv_logistic_recive_list_plan_code);
            TextView partName = (TextView) convertView.findViewById(R.id.tv_logistic_recive_list_part_name);
            TextView gxh = (TextView) convertView.findViewById(R.id.tv_logistic_recive_list_gxh);
            TextView quantity = (TextView) convertView.findViewById(R.id.tv_logistic_recive_list_quantity);
            //选择框
            CheckBox selected = (CheckBox) convertView.findViewById(R.id.cb_logistics_recive_checkBox);

            planCode.setText(sLogisticsPlanList.get(position).getCplanCode());
            partName.setText(sLogisticsPlanList.get(position).getCpartName());
            gxh.setText(" "+sLogisticsPlanList.get(position).getIgxh());
            quantity.setText(" "+sLogisticsPlanList.get(position).getFquantity());
            return convertView;
        }
    }
    private void loadData(String planCode){
        String url = HttpUtil.BASE_URL + uri;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("planCode", planCode);
        MyApplication app = (MyApplication) getApplication();
        if (app.getUserInfo().getCpsCode()==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsMenuActivity.this);
            builder.setTitle("ERROR");
            builder.setMessage("无法获取登录信息,请重新登录");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"重新登录!",Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
            return;

        }
        params.put("deptCode",app.getUserInfo().getCpsDepartmentCode());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //0条物流计划,提示错误
                //Toast.makeText(getApplicationContext(),"the network is error!",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsMenuActivity.this);
                builder.setTitle("ERROR");
                builder.setMessage("未查询到相关物流计划");
                builder.create().show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LogisticsMenuActivity.this);
                String[] message = responseString.split("@@");
                String result = message[1];
                Gson gson = new Gson();

                try {
                    //包含zero@@,提示没有
                    if (responseString.contains("zero@@")){
                        builder.setTitle("ERROR");
                        builder.setMessage("未查询到相关物流计划");
                        builder.create().show();
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
    private void initView(SLogisticsPlan sLogistics){
                //存入手机本地数据库中
                dbHelper = new DataBaseHelper(getApplicationContext());
                dbHelper.getWritableDatabase();
                LogisticsDaoImpl p = new LogisticsDaoImpl(getApplicationContext());
                p.save(sLogistics);
                dbHelper.close();
                Log.e("meng","插入成功");
                initList();
    }
    //选中物流计划实现接收
    private void recivePlan(){

    }
}
