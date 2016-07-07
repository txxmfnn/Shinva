package com.yanz.machine.shinva.util;


import android.view.View;

import java.util.Calendar;

/**
 * Created by yanz on 2016/7/7.
 */
public abstract class ClickUtil implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    protected abstract void onNoDoubleClick(View view);
    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(view);
        }
    }
}
