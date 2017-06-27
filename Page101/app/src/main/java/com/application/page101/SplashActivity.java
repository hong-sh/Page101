package com.application.page101;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.model.FragmentListener;
import com.application.model.MemberData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.RegistrationIntentService;
import com.application.util.URLDefine;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {


    private PageApplication app;
    private ActivityManager activityManager;
    private MemberData memberData;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ImageView imageView_page1, imageView_page2, imageView_page3, imageView_page4, imageView_page5;
    private ProgressBar progressBar;
    private String member_email, member_pass, member_device_id;

    public static final String REGISTRATION_READY = "registrationReady";
    public static final String REGISTRATION_GENERATING = "registrationGenerating";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private static final int PLAY_SERVICES_REOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    public void getInstanceIdToken() {
        if (checkPlayservices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);

        }
    }

    private boolean checkPlayservices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_REOLUTION_REQUEST).show();
            } else {
                Log.d("MyTag", "This device is not Supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void registBroadcastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (action.equals(REGISTRATION_READY)) {

                    //progressBar.setVisibility(View.GONE);

                } else if (action.equals(REGISTRATION_GENERATING)) {

                    //progressBar.setVisibility(View.VISIBLE);

                } else if (action.equals(REGISTRATION_COMPLETE)) {

                    member_device_id = intent.getStringExtra("token");
                    memberData.setMember_device_id(member_device_id);
                    loginCheck();
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initLayout();
        registBroadcastReceiver();
        getInstanceIdToken();

    }

    @Override
    protected void onResume() {

        this.overridePendingTransition(0, 0);
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }



    private void initLayout() {

        app = (PageApplication) getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(SplashActivity.this);
        memberData = app.instanceMemberData();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        imageView_page1 = (ImageView) findViewById(R.id.imageView_page1);
        imageView_page2 = (ImageView) findViewById(R.id.imageView_page2);
        imageView_page3 = (ImageView) findViewById(R.id.imageView_page3);
        imageView_page4 = (ImageView) findViewById(R.id.imageView_page4);
        imageView_page5 = (ImageView) findViewById(R.id.imageView_page5);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                mViewPager.setCurrentItem(position);

                switch (position) {
                    case 0:
                        imageView_page1.setImageResource(R.drawable.button_round_orange);
                        imageView_page2.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page3.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page4.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page5.setImageResource(R.drawable.button_round_darkgray);
                        break;
                    case 1:
                        imageView_page1.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page2.setImageResource(R.drawable.button_round_orange);
                        imageView_page3.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page4.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page5.setImageResource(R.drawable.button_round_darkgray);
                        break;
                    case 2:
                        imageView_page1.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page2.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page3.setImageResource(R.drawable.button_round_orange);
                        imageView_page4.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page5.setImageResource(R.drawable.button_round_darkgray);
                        break;
                    case 3:
                        imageView_page1.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page2.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page3.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page4.setImageResource(R.drawable.button_round_orange);
                        imageView_page5.setImageResource(R.drawable.button_round_darkgray);
                        break;
                    case 4:
                        imageView_page1.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page2.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page3.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page4.setImageResource(R.drawable.button_round_darkgray);
                        imageView_page5.setImageResource(R.drawable.button_round_orange);
                        break;
                }

            }
        });

        ((Button) findViewById(R.id.button_start)).setOnClickListener(buttonClickListener);

    }

    private void loginCheck() {

        String loginData[] = memberData.loadLoginData();
        if (!loginData[0].equals("")) {
            member_email = loginData[0];
            member_pass = loginData[1];

            Toast.makeText(SplashActivity.this, "자동 로그인 중...", Toast.LENGTH_SHORT).show();
            new LoginAsyncTask().execute();
        }
    }

    public class LoginAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonLogin(member_email, member_pass);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

                Toast.makeText(SplashActivity.this, memberData.getMember_name() +" 님 환영합니다~^^", Toast.LENGTH_SHORT).show();

                memberData.saveLoginData(member_email, member_pass);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                activityManager.removeActvity(SplashActivity.this);
                overridePendingTransition(0, 0);
                startActivity(intent);

            } else {
                Toast.makeText(SplashActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    String isLogin;

    private String JsonLogin(String member_email, String member_pass) {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.LOGIN_URL + "member_email=" + member_email + "&member_pass=" + member_pass + "&member_device_id=" + memberData.getMember_device_id());


            if (json != null) {
                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    isLogin = c.getString("isLogin");
                    if (isLogin.equals("false")) {
                        result = "false";
                        return result;
                    }

                    memberData.setMember_idx(c.getString("member_idx"));
                    memberData.setMember_device_id(c.getString("member_device_id"));
                    memberData.setMember_name(c.getString("member_name"));
                    memberData.setMember_email(c.getString("member_email"));
                    memberData.setMember_pass(c.getString("member_pass"));
                    memberData.setMember_point(c.getString("member_point"));
                    memberData.setMember_rank(c.getString("member_rank"));
                    memberData.setMember_phone(c.getString("member_phone"));

                    Log.d("MyTag", memberData.getMember_email());
                    Log.d("MyTag", memberData.getMember_pass());
                    Log.d("MyTag", memberData.getMember_name());

                }
                result = "true";
            } else {
                result = "false";
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("MyTag", "Error = " + e.toString());
            result = "false";
        }
        return result;
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            activityManager.removeActvity(SplashActivity.this);
            overridePendingTransition(0,0);
            startActivity(intent);
        }
    };

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).

            Fragment fragment = SplashFragment.newInstance(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

    int exit_count = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (exit_count == 0) {
                    Toast.makeText(this, "뒤로가기를 한번 더 누르시면 종료됩니다.",
                            Toast.LENGTH_SHORT).show();
                    exitHandler.sendEmptyMessageDelayed(0, 2000);
                    exit_count++;

                } else if (exit_count == 1) {
                    activityManager.removeAllActvity();
                    finish();
                    break;
                }

        }
        return false;
    }

    Handler exitHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                exit_count = 0;
            }
            return false;
        }
    });


}
