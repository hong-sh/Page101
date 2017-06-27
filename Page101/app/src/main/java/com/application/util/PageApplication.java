package com.application.util;

import android.app.Application;

import com.application.model.CartData;
import com.application.model.MemberData;

/**
 * Created by Administrator on 2015-09-23.
 */
public class PageApplication extends Application {

    public MemberData memberData;
    public ActivityManager activityManager;
    public CartData cartData;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MemberData instanceMemberData() {
        if(memberData == null)
            memberData = new MemberData(this);

        return memberData;
    }

    public ActivityManager instanceActivityManager() {
        if(activityManager == null)
            activityManager = new ActivityManager();

        return activityManager;
    }

    public CartData instanceCartData() {
        if(cartData == null)
            cartData = new CartData();

        return cartData;
    }
}
