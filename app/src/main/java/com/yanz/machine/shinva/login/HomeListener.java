package com.yanz.machine.shinva.login;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.yanz.machine.shinva.MainActivity;

/**
 * Created by yanzi on 2016-04-20.
 */
public class HomeListener implements View.OnClickListener {
    private Activity activity;
    public HomeListener(Activity activity){
        this.activity = activity;
    }
    @Override
    public void onClick(View source){
        Intent i = new Intent(activity, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }
}
