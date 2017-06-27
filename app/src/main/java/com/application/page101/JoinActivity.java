package com.application.page101;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {



    private ActivityManager activityManager;
    private PageApplication app;
    private MemberData memberData;

    private ProgressBar progressBar;
    private String member_email, member_pass, member_name, phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        initLayout();
        initTextView();
        initActionBar();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication)getApplication();
        memberData = app.instanceMemberData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        ((Button)findViewById(R.id.button_register)).setOnClickListener(buttonClickListener);
        TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phoneNumber = systemService.getLine1Number();
        phoneNumber = phoneNumber.substring(phoneNumber.length() - 10, phoneNumber.length());
        phoneNumber = "0"+phoneNumber;
        ((EditText)findViewById(R.id.editText_phone)).setText(phoneNumber);


    }

    private void initTextView() {

        try{

            InputStream in = getResources().openRawResource(R.raw.agree);

            if(in != null){

                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder("");

                while((read=buffer.readLine())!=null){
                    sb.append(read);
                }

                in.close();

                ((TextView)findViewById(R.id.textView_agree)).setText(Html.fromHtml(sb.toString()));

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //R.id.home
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_join)));
            actionBar.setTitle("");
        }
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(((EditText)findViewById(R.id.editText_email)).getText().length() == 0) {
                Toast.makeText(JoinActivity.this, "이메일을 입력해 주세연ㅠㅠ", Toast.LENGTH_SHORT).show();
            } else if(((EditText)findViewById(R.id.editText_password)).getText().length() == 0) {
                Toast.makeText(JoinActivity.this, "비밀번호를 입력해 주세연ㅠㅠ", Toast.LENGTH_SHORT).show();
            } else if(((EditText)findViewById(R.id.editText_name)).getText().length() == 0) {
                Toast.makeText(JoinActivity.this, "이름을 입력해 주세연ㅠㅠ", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(((EditText)findViewById(R.id.editText_email)).getText().toString())){
                Toast.makeText(JoinActivity.this, "이메일 형식을 맞춰 주세연ㅠㅠ\n(예: page101@page101.com)", Toast.LENGTH_SHORT).show();
            } else {
                member_email = ((EditText)findViewById(R.id.editText_email)).getText().toString();
                member_pass = ((EditText)findViewById(R.id.editText_password)).getText().toString();
                member_name = ((EditText)findViewById(R.id.editText_name)).getText().toString();
                new JoinAsyncTask().execute();

            }
        }
    };

    public boolean isValidEmail(String inputStr) {

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputStr);

        if( !m.matches() ) {
            return false;
        }
        return true;
    }

    public class JoinAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                member_name = URLEncoder.encode(member_name, "EUC-KR");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return JsonLogin(member_email, member_pass, member_name);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if(result.equals("true")) {
                Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                memberData.saveLoginData(member_email, member_pass);
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                activityManager.removeActvity(JoinActivity.this);
                overridePendingTransition(0, 0);
                startActivity(intent);
            } else {
                Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    String isJoin;
    private String JsonLogin(String member_email, String member_pass, String member_name) {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.JOIN_URL +  "&member_name=" + member_name
                    + "&member_email=" +member_email +"&member_pass=" +member_pass +"&member_phone="+phoneNumber);


            if (json != null) {
                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    isJoin = c.getString("isJoin");
                    if (isJoin.equals("false")) {
                        result = "false";
                        return result;
                    }

                }
                result = "true";
            } else {
                result = "false";
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("MyTag", "Error = " +e.toString());
            result = "false";
        }
        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_join, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            activityManager.removeActvity(JoinActivity.this);
            overridePendingTransition(0, 0);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                activityManager.removeActvity(JoinActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);

        }
        return false;
    }
}
