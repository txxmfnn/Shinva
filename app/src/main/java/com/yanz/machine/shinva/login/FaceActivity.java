package com.yanz.machine.shinva.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.db.DataBaseHelper;
import com.yanz.machine.shinva.update.UpdateManager;

import java.io.IOException;


public class FaceActivity extends Activity {
    private static final int GO_TO_LOGIN_ACTIVITY = 1;
    DataBaseHelper dbHelper;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        setContentView(R.layout.face_activity);

        //getSupportActionBar().hide();//隐藏标题，继承自appActivity，所以用这种方式
        //创建数据库DatabaseHelper对象,制定数据库版本为1,此处使用相对路径即可
        dbHelper = new DataBaseHelper(this);

        //自动检查更新
        Toast.makeText(FaceActivity.this,"正在检查更新...",Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                try {
                    UpdateManager um= new UpdateManager(FaceActivity.this);
                    um.checkUpdate();}
                catch (IOException e){
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }.start();

        //更改UI
        Message message = new Message();
        message.what = GO_TO_LOGIN_ACTIVITY;
        handler.sendEmptyMessageDelayed(GO_TO_LOGIN_ACTIVITY,2000);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper!=null){
            dbHelper.close();
        }
    }
}
