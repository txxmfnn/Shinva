package com.yanz.machine.shinva;

import android.os.Bundle;
import android.app.Activity;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class SearchActivity extends Activity {

    //搜索框
    private LinearLayout ll_search;
    //返回按钮
    private ImageView iv_back;
    private EditText ed_search;
    private AnimationSet animationSet;
    //第一次按下屏幕是的Y坐标
    float fist_down_Y = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

}
