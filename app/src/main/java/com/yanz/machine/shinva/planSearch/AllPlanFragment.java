package com.yanz.machine.shinva.planSearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.SPlan;
import com.yanz.machine.shinva.util.HttpUtil;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class AllPlanFragment extends Fragment {
    private String uri = "/splan/find";
    private View mMainView;
    private String planCode ;
    private TextView tvGuiHao,tvDingdan,tvU8,tvStockCode,tvStockName,tvStockGuige,tvGyr,tvLingliao;
    private List<SPlan> sPlanList;
    public AllPlanFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        planCode = getActivity().getIntent().getStringExtra("planCode");
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_all_plan,(ViewGroup) getActivity().findViewById(R.id.pager),false);
        tvGuiHao = (TextView) mMainView.findViewById(R.id.tv_planAll_guihaoCode);
        tvDingdan = (TextView) mMainView.findViewById(R.id.tv_planAll_dingdanCode);
        tvU8 = (TextView) mMainView.findViewById(R.id.tv_planAll_u8Code);
        tvStockCode = (TextView) mMainView.findViewById(R.id.tv_planAll_stockCode);
        tvStockName = (TextView) mMainView.findViewById(R.id.tv_planAll_stockNameCode);
        tvStockGuige = (TextView) mMainView.findViewById(R.id.tv_planAll_stockPtdCode);
        tvGyr = (TextView) mMainView.findViewById(R.id.tv_planAll_gyrCode);
        tvLingliao = (TextView) mMainView.findViewById(R.id.tv_planAll_llccCode);
        return mMainView;
    }

    protected void initData(){
        String url = HttpUtil.BASE_URL+uri;
        RequestParams params = new RequestParams();
        params.put("planCode",planCode);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString.contains("true@@")){
                    String[] message = responseString.split("@@");
                    String result = message[1];
                    Gson gson = new Gson();
                    sPlanList = gson.fromJson(result,new TypeToken<List<SPlan>>(){}.getType());
                    loadData();
                }
            }
        });
    }
    private void loadData(){
        SPlan sPlan = new SPlan();
        sPlan = sPlanList.get(0);
        tvGuiHao.setText(sPlan.getCwpCntrNo());
        tvDingdan.setText(sPlan.getCwpOutPlanCode());
        tvU8.setText(sPlan.getCwpErpCode());
        tvStockCode.setText(sPlan.getCwpMaterialCode());
        tvStockName.setText(sPlan.getCwpMaterialName());
        tvStockGuige.setText(sPlan.getCwpMaterialStd());
        tvGyr.setText(sPlan.getCwpSjr()+" 工艺路线:"+sPlan.getCwpSjrq()+" 每坯件数:"+sPlan.getCwpMpjs()+" 毛坯种类:"+sPlan.getCwpMpzl());
        tvLingliao.setText(sPlan.getCwpLlcc());
        tvStockGuige.setTextColor(getResources().getColor(R.color.tv_Red));
    }
}
