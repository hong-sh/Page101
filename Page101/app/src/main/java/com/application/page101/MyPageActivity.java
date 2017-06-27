package com.application.page101;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.adapter.OrderHistoryDataAdapter;
import com.application.model.CartData;
import com.application.model.MemberData;
import com.application.model.OrderHistoryData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    PageApplication app;
    ActivityManager activityManager;
    MemberData memberData;
    CartData cartData;

    ImageView arrow;
    View layout_user_sub3;
    ProgressBar progressBar;
    AlertDialog logoutDialog = null;
    AlertDialog passDialog = null;

    String current_pass, new_pass;
    boolean click_flag = false;

    ListView listView;
    ArrayAdapter<OrderHistoryData> adapter;
    ArrayList<OrderHistoryData> orderHistoryDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        initLayout();
        initActionBar();

        new OrderHistoryAsyncTask().execute();

    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication) getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(MyPageActivity.this);
        memberData = app.instanceMemberData();
        cartData = app.instanceCartData();

        arrow = (ImageView) findViewById(R.id.imageView_pass_arrow);
        layout_user_sub3 = (View) findViewById(R.id.layout_user_sub3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listView = (ListView)findViewById(R.id.listView_history);
        adapter = new OrderHistoryDataAdapter(MyPageActivity.this, R.layout.listview_order_history, orderHistoryDatas);
        listView.setAdapter(adapter);

        ((TextView)findViewById(R.id.textView_id)).setText(memberData.getMember_email());
        ((View) findViewById(R.id.layout_logout)).setOnClickListener(layoutClickListener);
        ((View) findViewById(R.id.layout_user_sub2)).setOnClickListener(layoutClickListener);
        ((Button) findViewById(R.id.button_renew)).setOnClickListener(buttonClickListener);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //R.id.home
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_mypage)));
            actionBar.setTitle("");
        }
    }

    public class OrderHistoryAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            orderHistoryDatas.clear();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonOrderHistory();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(MyPageActivity.this, "음료 예약 내역 조회 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    private String JsonOrderHistory() {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.ORDERHISTORY_URL +"member_idx=" +memberData.getMember_idx());


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    OrderHistoryData orderHistoryData = new OrderHistoryData();

                    JSONObject c = contacts.getJSONObject(i);

                    orderHistoryData.setFirst_menu(c.getString("first_menu"));
                    orderHistoryData.setAmount(c.getString("amount"));
                    orderHistoryData.setOrder_total_price(c.getString("order_total_price"));
                    orderHistoryData.setOrder_date(c.getString("order_date"));

                    orderHistoryDatas.add(orderHistoryData);

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

    View.OnClickListener layoutClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_logout:

                    logoutDialog = createLogoutDialog();
                    logoutDialog.show();

                    break;
                case R.id.layout_user_sub2:

                    if (click_flag) {

                        layout_user_sub3.setVisibility(View.GONE);
                        arrow.setRotation(0);
                        click_flag = false;

                    } else {

                        layout_user_sub3.setVisibility(View.VISIBLE);
                        arrow.setRotation(90);
                        click_flag = true;

                    }

                    break;
            }
        }
    };

    private AlertDialog createLogoutDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("COFFE PAGE 101");
        ab.setMessage("정말 로그아웃 하시겠어연ㅠㅠ?");
        ab.setCancelable(false);
        ab.setIcon(getResources().getDrawable(R.drawable.app_icon));

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                memberData.removeLoginData();
                setDismiss(logoutDialog);
                cartData.removeAllCart();
                Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                activityManager.removeActvity(MyPageActivity.this);
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(logoutDialog);
            }
        });

        return ab.create();
    }

    private void setDismiss(Dialog dialog) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            current_pass = ((EditText) findViewById(R.id.editText_current_pass)).getText().toString();
            new_pass = ((EditText) findViewById(R.id.editText_new_pass)).getText().toString();

            passDialog = createPassDialog();
            passDialog.show();

        }
    };

    private AlertDialog createPassDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("COFFE PAGE 101");
        ab.setMessage("비밀번호를 변경 하시겠어연??");
        ab.setCancelable(false);
        ab.setIcon(getResources().getDrawable(R.drawable.app_icon));

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                setDismiss(passDialog);
                if (current_pass.length() == 0) {
                    Toast.makeText(MyPageActivity.this, "현재 비밀번호를 입력해주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                } else if (!memberData.getMember_pass().equals(current_pass)) {
                    Toast.makeText(MyPageActivity.this, "현재 비밀번호가 일치하지 않아연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                } else if (new_pass.length() == 0) {
                    Toast.makeText(MyPageActivity.this, "새로운 비밀번호를 입력해주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                } else if (current_pass.length() > 0 && memberData.getMember_pass().equals(current_pass) && new_pass.length() > 0) {
                    new PassAsyncTask().execute();
                }

            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(passDialog);
            }
        });

        return ab.create();
    }


    public class PassAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonPass(new_pass);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                Toast.makeText(MyPageActivity.this, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();

                memberData.setMember_pass(new_pass);
                String member_info[] = memberData.loadLoginData();
                memberData.saveLoginData(member_info[0], new_pass);

                layout_user_sub3.setVisibility(View.GONE);
                arrow.setRotation(0);
                click_flag = false;

            } else {
                Toast.makeText(MyPageActivity.this, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    String isSuccess;

    private String JsonPass(String new_pass) {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.PASS_URL + "member_idx=" + memberData.getMember_idx() + "&member_pass_change=" + new_pass);


            if (json != null) {
                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    isSuccess = c.getString("isSuccess");
                    if (isSuccess.equals("false")) {
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
            Log.d("MyTag", "Error = " + e.toString());
            result = "false";
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mypage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            FragmentManager fm = getSupportFragmentManager();
            InfoDialog infoDialog = new InfoDialog();
            infoDialog.show(fm, memberData.getMember_name());
        }

        if (id == android.R.id.home) {
            Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
            activityManager.removeActvity(MyPageActivity.this);
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
                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                activityManager.removeActvity(MyPageActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);
        }
        return false;
    }
}
