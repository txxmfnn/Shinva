package com.yanz.machine.shinva.unqualifyPartDeal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.Adapter.BaseViewHolder;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SUnqualifyPartDeal;
import com.yanz.machine.shinva.entity.SUnqualifyPartDetail;
import com.yanz.machine.shinva.util.HttpUtil;

import cz.msebera.android.httpclient.Header;

public class QualifyItemActivity extends AppCompatActivity {

    String uri = "/unqualify/findItem";
    String cwlCode = "";
    SUnqualifyPartDeal deal = new SUnqualifyPartDeal();
    SUnqualifyPartDetail detail = new SUnqualifyPartDetail();
    ProgressDialog proDialog;
    TextView tvType ;
    TextView tvWareHouse;
    TextView tvBackDate ;
    TextView tvWlCode ;
    TextView tvPlCode ;
    TextView tvGxh ;
    TextView tvPartCode;
    TextView tvPartName ;
    TextView tvGxnr ;
    TextView tvPlanQuality;
    TextView tvUnQuality ;
    TextView tvUnType ;
    TextView tvUnReason;
    TextView tvTotalCast;
    TextView tvResult ;
    TextView tvCorrect ;
    TextView tvTechName;
    TextView tvTechTime ;
    TextView tvCheckName ;
    TextView tvCheckTime ;
    TextView tvDeptName ;
    TextView tvPlanName ;
    TextView tvGyName ;
    TextView tvTechSubmit;
    TextView tvFinanceName;
    TextView tvFinanceTime ;
    TextView tvAuditName ;
    TextView tvAuditTime ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualify_item);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
         tvType = (TextView) findViewById(R.id.qualify_item_type);
         tvWareHouse = (TextView) findViewById(R.id.qualify_item_ware_house);
         tvBackDate = (TextView) findViewById(R.id.qualify_item_back_date);
         tvWlCode = (TextView) findViewById(R.id.qualify_item_back_wl_code);
         tvPlCode = (TextView) findViewById(R.id.qualify_item_back_pl_code);
         tvGxh = (TextView) findViewById(R.id.qualify_item_gxh);
         tvPartCode = (TextView) findViewById(R.id.qualify_item_back_part_code);
         tvPartName = (TextView) findViewById(R.id.qualify_item_back_part_name);
         tvGxnr = (TextView) findViewById(R.id.qualify_item_back_gxnr);
         tvPlanQuality = (TextView) findViewById(R.id.qualify_item_back_plan_quality);
         tvUnQuality = (TextView) findViewById(R.id.qualify_item_back_un_quality);
         tvUnType = (TextView) findViewById(R.id.qualify_item_back_un_type);
         tvUnReason = (TextView) findViewById(R.id.qualify_item_back_un_reason);
         tvTotalCast = (TextView) findViewById(R.id.qualify_item_back_total_cast);
         tvResult = (TextView) findViewById(R.id.qualify_item_back_result);
         tvCorrect = (TextView) findViewById(R.id.qualify_item_back_correct);
         tvTechName = (TextView) findViewById(R.id.qualify_item_back_tech_name);
         tvTechTime = (TextView) findViewById(R.id.qualify_item_tech_time);
         tvCheckName = (TextView) findViewById(R.id.qualify_item_back_check_name);
         tvCheckTime = (TextView) findViewById(R.id.qualify_item_check_time);
         tvDeptName = (TextView) findViewById(R.id.qualify_item_back_dept_name);
         tvPlanName = (TextView) findViewById(R.id.qualify_item_plan_name);
         tvGyName = (TextView) findViewById(R.id.qualify_item_back_gy_name);
         tvTechSubmit = (TextView) findViewById(R.id.qualify_item_tech_submit);
         tvFinanceName = (TextView) findViewById(R.id.qualify_item_back_finance_name);
         tvFinanceTime = (TextView) findViewById(R.id.qualify_item_finance_time);
         tvAuditName = (TextView) findViewById(R.id.qualify_item_back_audit_name);
         tvAuditTime = (TextView) findViewById(R.id.qualify_item_audit_time);
        proDialog = ProgressDialog.show(QualifyItemActivity.this,"正在查询","请稍候...");
        cwlCode = getIntent().getStringExtra("cwlCode");
        if (cwlCode!=""){
            loadData();
        }
    }
    public  void loadData(){
        String url = HttpUtil.BASE_URL+uri;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("cwlCode",cwlCode);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(QualifyItemActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    if (responseString.contains("true@@")){
                        String[] message = responseString.split("@@");
                        String  dealString= message[1];
                        Gson gson = new Gson();
                        deal = gson.fromJson(dealString,SUnqualifyPartDeal.class);
                        detail = deal.getDetail();
                        initData();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void initData(){

        QualifyItemActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvType.setText(deal.getCtype());
                tvType.setTextColor(getResources().getColor(R.color.tv_Black));
                tvWareHouse.setText(deal.getCwareHouse());
                tvWareHouse.setTextColor(getResources().getColor(R.color.tv_Black));
                tvBackDate.setText(deal.getDtBackDate());//退回日期
                tvBackDate.setTextColor(getResources().getColor(R.color.tv_Black));
                tvWlCode.setText(cwlCode);
                tvWlCode.setTextColor(getResources().getColor(R.color.tv_Black));
                tvPlCode.setText(deal.getCplanCode());
                tvPlCode.setTextColor(getResources().getColor(R.color.tv_Black));
                tvGxh.setText(deal.getIgxh()+" ");
                tvGxh.setTextColor(getResources().getColor(R.color.tv_Black));
                tvPartCode.setText(deal.getCpartCode());
                tvPartCode.setTextColor(getResources().getColor(R.color.tv_Black));
                tvPartName.setText(deal.getCpartName());
                tvPartName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvGxnr.setText(deal.getCgxnr());
                tvGxnr.setTextColor(getResources().getColor(R.color.tv_Black));
                tvPlanQuality.setText(" "+deal.getFplanQuantity());
                tvPlanQuality.setTextColor(getResources().getColor(R.color.tv_Black));
                tvUnQuality.setText(" "+deal.getFunQualifyQuantity());
                tvUnQuality.setTextColor(getResources().getColor(R.color.tv_Red));
                tvUnType.setText(deal.getCunQualifyType());
                tvUnType.setTextColor(getResources().getColor(R.color.tv_Black));
                tvUnReason.setText(detail.getCunQualifyContent());
                tvUnReason.setTextColor(getResources().getColor(R.color.tv_Black));
                tvTotalCast.setText(" "+deal.getFunQualifyTotalCost());
                tvTotalCast.setTextColor(getResources().getColor(R.color.tv_Red));
                tvResult.setText(deal.getCunqualifyDealResult());
                tvResult.setTextColor(getResources().getColor(R.color.tv_Black));
                tvCorrect.setText(detail.getCdealSuggest());
                tvCorrect.setTextColor(getResources().getColor(R.color.tv_Black));
                tvTechName.setText(deal.getCtechnicalPersonName());
                tvTechName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvTechTime.setText(deal.getDtechnicalDealDate());
                tvTechTime.setTextColor(getResources().getColor(R.color.tv_Black));
                tvCheckName.setText(deal.getCcheckerName());
                tvCheckName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvCheckTime.setText(deal.getDcheckDate());
                tvCheckTime.setTextColor(getResources().getColor(R.color.tv_Black));
                tvDeptName.setText(deal.getCdepartmentName());
                tvDeptName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvPlanName.setText(deal.getCplanerName());
                tvPlanName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvGyName.setText(deal.getCsheji());
                tvGyName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvTechSubmit.setText(" "+deal.getBtechnicSubmit());
                tvTechSubmit.setTextColor(getResources().getColor(R.color.tv_Black));
                tvFinanceName.setText(deal.getCfinancePersonName());
                tvFinanceName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvFinanceTime.setText(deal.getCfinanceDealDate());
                tvFinanceTime.setTextColor(getResources().getColor(R.color.tv_Black));
                tvAuditName.setText(deal.getCqcauditPersonName());
                tvAuditName.setTextColor(getResources().getColor(R.color.tv_Black));
                tvAuditTime.setText(deal.getDqcauditDate());
                tvAuditTime.setTextColor(getResources().getColor(R.color.tv_Black));
                proDialog.dismiss();

            }
        });



    }
}
