package com.yanz.machine.shinva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yanz.machine.shinva.entity.SCurrentStock;

import java.util.ArrayList;
import java.util.List;

public class SearchConditionActivity extends Activity {

    private EditText materialName,materialCode;
    private Button bnSearch;
    String uri ="/stock/find";
    List<SCurrentStock> stocks = new ArrayList<SCurrentStock>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_condition);
        materialName = (EditText) findViewById(R.id.et_materialName);
        materialCode = (EditText) findViewById(R.id.et_materialCode);
        bnSearch = (Button) findViewById(R.id.serachMaterial);

    }
    public void search(View view){
        int id = view.getId();
        if (R.id.serachMaterial == id){
            final String name = materialName.getText().toString();
            final String code = materialCode.getText().toString();
            Intent intent = new Intent();
            intent.setClass(SearchConditionActivity.this,SearchResultActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("code",code);
            startActivity(intent);
        }
    }

}
