package com.yanz.machine.shinva.Setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yanz.machine.shinva.MainActivity;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.application.MyApplication;
import com.yanz.machine.shinva.entity.BPerson;
import com.yanz.machine.shinva.login.LoginActivity;
import com.yanz.machine.shinva.util.HttpUtil;
import com.yanz.machine.shinva.util.JsonUtil;
import com.yanz.machine.shinva.util.StrUtil;

import cz.msebera.android.httpclient.Header;

public class ChangePWDActivity extends Activity {
    String uri = "/login/changePWD";
    EditText etCode,etPassword,etNewPWD,etCheckPWD;
    Button btChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        etCode = (EditText) findViewById(R.id.et_changePWD_code);
        etPassword = (EditText) findViewById(R.id.et_changePWD_password);
        etNewPWD = (EditText) findViewById(R.id.et_changePWD_newPWD);
        etCheckPWD = (EditText) findViewById(R.id.et_changePWD_newPWD_check);
        btChange = (Button) findViewById(R.id.bt_changePWD_submit);
    }

    //登陆点击事件
    public void change(View v){
        final String code = etCode.getText().toString();
        String password = etPassword.getText().toString();
        final String newPWD = etNewPWD.getText().toString();
        String checkPWD = etCheckPWD.getText().toString();
        String url ;
        if (checkPWD.equals(newPWD)==false){
            new AlertDialog.Builder(ChangePWDActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.waring_icon,null))
                    .setTitle("警告")
                    .setMessage("两次新密码不一致")
                    .create().show();
        }else if (StrUtil.isNotEmpty(code)&&StrUtil.isNotEmpty(password)&&StrUtil.isNotEmpty(newPWD)){
            AsyncHttpClient client = new AsyncHttpClient();
            url = HttpUtil.BASE_URL+uri;
            RequestParams params = new RequestParams();
            params.put("cpsCode",code);
            params.put("password",password);
            params.put("newPWD",newPWD);

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String message, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String message) {
                    if (statusCode == 200){
                        try {
                            message = new String(message.getBytes("iso8859-1"),"UTF-8");
                            if (message.contains("true@@")){
                                saveUserInfo(code,newPWD,true);
                                Intent intent = new Intent(ChangePWDActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(ChangePWDActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        Toast.makeText(ChangePWDActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if ("".equals(etCode.getText().toString())||"".equals(etPassword.getText().toString())||"".equals(etNewPWD.getText().toString())){
            new AlertDialog.Builder(ChangePWDActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.waring_icon,null))
                    .setTitle("警告")
                    .setMessage("用户名或密码不能为空")
                    .create().show();
        }
    }
    //存储用户信息
    public void saveUserInfo(String name,String password,Boolean stat){
        SharedPreferences sp = getSharedPreferences("USERINFO",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.putString("password",password);
        editor.putBoolean("stat", stat);
        System.out.println("stat:"+stat);
        editor.commit();
    }
}
