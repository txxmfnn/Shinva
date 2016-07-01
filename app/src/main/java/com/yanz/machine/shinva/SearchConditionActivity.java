package com.yanz.machine.shinva;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.yanz.machine.shinva.entity.SCurrentStock;

import java.util.ArrayList;
import java.util.List;

public class SearchConditionActivity extends Activity {

    private EditText materialName;
    private AutoCompleteTextView partStd;
    private Button wHome;
    private Button bnSearch;
    String uri ="/stock/find";
    List<SCurrentStock> stocks = new ArrayList<SCurrentStock>();
    //设置仓库数据的数据
    final String[] wHomes = {
            "011101|不锈钢板材库",
            "011102|不锈钢型材库",
            "011103|黑材库",
            "011104|外购件库",
            "011105|耗材库",
            "011106|焊材库",
            "011107|刀具库",
            "011121|半成品库"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_condition);
        materialName = (EditText) findViewById(R.id.et_stockSearch_materialName);
        partStd = (AutoCompleteTextView) findViewById(R.id.actv_StockSearch_ptd);
        wHome = (Button) findViewById(R.id.bn_stockSearch_whome);
        bnSearch = (Button) findViewById(R.id.bn_stockSearch_search);
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
    }
    public void wHomeClick(View view){
        int id = view.getId();
        if (R.id.bn_stockSearch_whome == id){
            Toast.makeText(this,"wocaowocao",Toast.LENGTH_SHORT).show();

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
            loadData();
        }
    }
    private void loadData(){
        String name = materialName.getText().toString();
        String code = partStd.getText().toString();
        String whName = wHome.getText().toString();

    }

}
