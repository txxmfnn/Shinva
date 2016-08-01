package com.yanz.machine.shinva;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanz.machine.shinva.Query.QueryFragment;
import com.yanz.machine.shinva.Setting.SettingFragment;
import com.yanz.machine.shinva.Tools.IBtnCallListener;


public class MainActivity extends FragmentActivity
        implements OnClickListener,IBtnCallListener {

    private boolean isExit;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    //界面底部的菜单按钮
    private ImageView[] bt_menu = new ImageView[4];
    //界面底部的菜单按钮id，id在main_fa布局中
    private int[] bt_menu_id = {
            R.id.iv_menu_home,
            R.id.iv_menu_query,
            R.id.iv_menu_2,
           // R.id.iv_menu_3,
            R.id.iv_menu_setting};
    //界面底部的选中菜单按钮资源
    private int[] select_on= {
            R.drawable.guide_home_on,
            R.drawable.guide_tfaccount_on,
            R.drawable.guide_discover_on,
            //R.drawable.guide_cart_on,
            R.drawable.guide_account_on
    };
    //界面底部未选中菜单资源按钮
    private int[] select_off = {
            R.drawable.bt_menu_home_select,
            R.drawable.bt_menu_query_select,
            R.drawable.bt_menu_2_select,
            //R.drawable.bt_menu_3_select,
            R.drawable.bt_menu_setting_select
    };
    //private HomeFragment home_F;//主界面
    private HomeFragment home_F;
    private QueryFragment query_F;//查询界面
    private SettingFragment setting_F;//设置界面
    private Blank2Fragment blank2_F;
    //private Blank3Fragment blank3_F;
    //响应从Fragment传过来的消息
    @Override
    public void transferMsg() {
        if (home_F==null){
            home_F = new HomeFragment();
            addFragment(home_F);
            showFragment(home_F);
        }else{
            showFragment(home_F);
        }
        bt_menu[3].setImageResource(select_off[3]);
        bt_menu[0].setImageResource(select_on[0]);
        System.out.println("由Fragment中传过来的消息");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }
    //初始化组件，并设置监听
    private void initView(){
        //找到底部菜单的按钮并设置监听
        for (int i = 0 ;i<bt_menu.length;i++){
            bt_menu[i]= (ImageView) findViewById(bt_menu_id[i]);
            bt_menu[i].setOnClickListener(this);
        }
        //初始化默认显示的界面
        if (home_F==null){
            home_F= new HomeFragment();
            addFragment(home_F);
            showFragment(home_F);
        }else {
            showFragment(home_F);
        }
        //设置默认首页为点击时的图片
        bt_menu[0].setImageResource(select_on[0]);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_menu_home:
                //主界面
                if (home_F == null){
                    home_F = new HomeFragment();
                    //判断当前界面是否隐藏，如果隐藏就添加显示，false标识显示，true标识隐藏
                    addFragment(home_F);
                    showFragment(home_F);
                }else {
                    if (home_F.isHidden()){
                        showFragment(home_F);
                    }
                }
                break;
            case R.id.iv_menu_query:
                //查询界面
                if (query_F==null){
                    query_F = new QueryFragment();
                    if (!query_F.isHidden()){
                        addFragment(query_F);
                        showFragment(query_F);
                    }
                }else {
                    if (query_F.isHidden()){
                        showFragment(query_F);
                    }
                }
                break;
            case R.id.iv_menu_setting:
                //设置界面
                if (setting_F==null){
                    setting_F = new SettingFragment();
                    if (!setting_F.isHidden()){
                        addFragment(setting_F);
                        showFragment(setting_F);
                    }
                }else {
                    if (setting_F.isHidden()){
                        showFragment(setting_F);
                    }
                }
                break;
            case R.id.iv_menu_2:
                if (blank2_F==null){
                    blank2_F = new Blank2Fragment();
                    if (!blank2_F.isHidden()){
                        addFragment(blank2_F);
                        showFragment(blank2_F);
                    }
                }else {
                    if (blank2_F.isHidden()){
                        showFragment(blank2_F);
                    }
                }
                break;
            /*case R.id.iv_menu_3:
                if (blank3_F==null){
                    blank3_F = new Blank3Fragment();
                    if (!blank3_F.isHidden()){
                        addFragment(blank3_F);
                        showFragment(blank3_F);
                    }
                }else {
                    if (blank3_F.isHidden()){
                        showFragment(blank3_F);
                    }
                }
                break;*/
            //可以继续添加点击事件
        }
        //设置按钮的选中和未选中资源
        for (int i = 0 ;i<bt_menu.length;i++){
            bt_menu[i].setImageResource(select_off[i]);
            if (v.getId()==bt_menu_id[i]){
                bt_menu[i].setImageResource(select_on[i]);
            }
        }
    }
    /**
     * 添加Fragment
     */
    public void addFragment(Fragment fragment){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.show_layout, fragment);
        ft.commit();
    }
    /**
     * 删除Fragment
     */
    public void removeFragment(Fragment fragment){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }
    /**
     * 显示Fragment
     */
    public void showFragment(Fragment fragment){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        //设置切换动画
        ft.setCustomAnimations(R.anim.cu_push_right_in,R.anim.cu_push_left_in);
        //判断页面是否已经创建，如果创建就隐藏
        if (home_F!=null){
            ft.hide(home_F);
        }
        if (query_F!=null){
            ft.hide(query_F);
        }
        if (setting_F!=null){
            ft.hide(setting_F);
        }
        if (blank2_F!=null){
            ft.hide(blank2_F);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * 返回按钮的监听
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this,"点击返回按钮",Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    /**
     * fragment的回调函数
     */
    @SuppressWarnings("unused")
    private IBtnCallListener btnCallListener;

    @Override
    public void onAttachFragment(Fragment fragment) {
        try {
            btnCallListener = (IBtnCallListener) fragment;
        } catch (Exception e) {
        }
        super.onAttachFragment(fragment);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public void exit(){
        if (!isExit){
            isExit= true;
            Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0,2000);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
}
