package com.yanz.machine.shinva.application;

import android.app.Application;

import com.yanz.machine.shinva.entity.BPerson;

/**
 * Created by yanzi on 2016-05-16.
 */
public class MyApplication extends Application {
    //用户信息
    private BPerson userInfo;

    public MyApplication(){
        System.out.println("myApplication starting...");
    }

    public BPerson getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(BPerson userInfo) {
        this.userInfo = userInfo;
    }

    public void destroyInfomationInApplication(){
        userInfo = null;
    }
}
