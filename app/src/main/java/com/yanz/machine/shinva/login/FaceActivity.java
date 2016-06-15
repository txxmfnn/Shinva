package com.yanz.machine.shinva.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.yanz.machine.shinva.R;


public class FaceActivity extends Activity {
    private static final int GO_TO_LOGIN_ACTIVITY = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == GO_TO_LOGIN_ACTIVITY){
                Intent mainIntent = new Intent(FaceActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        setContentView(R.layout.face_activity);

        //getSupportActionBar().hide();//隐藏标题，继承自appActivity，所以用这种方式


        //更改UI
        Message message = new Message();
        message.what = GO_TO_LOGIN_ACTIVITY;
        handler.sendEmptyMessageDelayed(GO_TO_LOGIN_ACTIVITY,1000);
    }
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() ==0){
            Intent intent = new Intent();
            intent.setClass(FaceActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
