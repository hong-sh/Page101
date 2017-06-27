package com.application.page101;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends Activity {

    private PageApplication app;
    private MemberData memberData;
    private ActivityManager activityManager;
    private ProgressBar progressBar;
    private String member_email, member_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initLayout();
        loginCheck();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void loginCheck() {

        String loginData[] = memberData.loadLoginData();
        if (!loginData[0].equals("")) {
            member_email = loginData[0];
            member_pass = loginData[1];

            ((EditText) findViewById(R.id.editText_email)).setText(member_email);
            ((EditText) findViewById(R.id.editText_password)).setText(member_pass);

            Toast.makeText(LoginActivity.this, "자동 로그인 중...", Toast.LENGTH_SHORT).show();

            new LoginAsyncTask().execute();
        }
    }

    private void initLayout() {

        app = (PageApplication) getApplication();
        memberData = app.instanceMemberData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        ((Button) findViewById(R.id.button_login)).setOnClickListener(buttonClickListener);
        ((Button) findViewById(R.id.button_join)).setOnClickListener(buttonClickListener);

    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent;

            switch (v.getId()) {

                case R.id.button_join:
                    intent = new Intent(getApplicationContext(), JoinActivity.class);
                    activityManager.removeActvity(LoginActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;

                case R.id.button_login:
                    member_email = ((EditText) findViewById(R.id.editText_email)).getText().toString();
                    member_pass = ((EditText) findViewById(R.id.editText_password)).getText().toString();

                    new LoginAsyncTask().execute();
                    break;
            }

        }
    };

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

                Toast.makeText(LoginActivity.this, memberData.getMember_name() +" 님 환영합니다~^^", Toast.LENGTH_SHORT).show();

                memberData.saveLoginData(member_email, member_pass);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                activityManager.removeActvity(LoginActivity.this);
                overridePendingTransition(0, 0);
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
