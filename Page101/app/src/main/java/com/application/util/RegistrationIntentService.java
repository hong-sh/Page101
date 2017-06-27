package com.application.util;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.application.page101.JoinActivity;
import com.application.page101.LoginActivity;
import com.application.page101.SplashActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Administrator on 2015-09-28.
 */
public class RegistrationIntentService extends IntentService{

    private static final String TAG = "RegistrationIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SplashActivity.REGISTRATION_GENERATING));

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = null;

        try {
            synchronized(TAG) {

               // String default_senderId = "AIzaSyBoKzdl3AIev1hGvW4yjhJ3ocMralU4nvc";
                String default_senderId = "5717114152";
                String scope = GoogleCloudMessaging.INSTANCE_ID_SCOPE;

                token = instanceID.getToken(default_senderId, scope, null);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent registrationComplete = new Intent(SplashActivity.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token" , token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
