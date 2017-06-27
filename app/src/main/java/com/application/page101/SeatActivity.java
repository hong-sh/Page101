package com.application.page101;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.model.CartData;
import com.application.model.MemberData;
import com.application.model.SeatData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SeatActivity extends AppCompatActivity {

    private PageApplication app;
    private ActivityManager activityManager;
    private CartData cartData;

    private ProgressBar progressBar;

    private ArrayList<View> tableList = new ArrayList<>();
    private ArrayList<SeatData> seatDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        initLayout();
        initActionBar();
        new SeatAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication) getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(this);
        cartData = app.instanceCartData();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        ((ImageView)findViewById(R.id.imageView_renew)).setOnClickListener(renewClickListener);

        initTable();

        ((ImageView)findViewById(R.id.button_home)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_reservation)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_seat)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_event)).setOnClickListener(bottomClickListener);
    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_main)));
            actionBar.setTitle("");
        }
    }


    View.OnClickListener renewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            seatDatas.clear();
            new SeatAsyncTask().execute();
        }
    };

    private void initTable() {

        tableList.add((View)findViewById(R.id.view_table1));
        tableList.add((View)findViewById(R.id.view_table2));
        tableList.add((View)findViewById(R.id.view_table3));
        tableList.add((View)findViewById(R.id.view_table4));
        tableList.add((View)findViewById(R.id.view_table5));
        tableList.add((View)findViewById(R.id.view_table6));
        tableList.add((View)findViewById(R.id.view_table7));
        tableList.add((View)findViewById(R.id.view_table8));
        tableList.add((View)findViewById(R.id.view_table9));
        tableList.add((View)findViewById(R.id.view_table10));
        tableList.add((View)findViewById(R.id.view_table11));
        tableList.add((View)findViewById(R.id.view_table12));
        tableList.add((View)findViewById(R.id.view_table13));
        tableList.add((View)findViewById(R.id.view_table14));
        tableList.add((View)findViewById(R.id.view_table15));
        tableList.add((View)findViewById(R.id.view_table16));
        tableList.add((View)findViewById(R.id.view_table17));
        tableList.add((View)findViewById(R.id.view_table18));
        tableList.add((View)findViewById(R.id.view_table19));
        tableList.add((View)findViewById(R.id.view_table20));
        tableList.add((View)findViewById(R.id.view_table21));
        tableList.add((View)findViewById(R.id.view_table22));
        tableList.add((View)findViewById(R.id.view_table23));
        tableList.add((View)findViewById(R.id.view_table24));
        tableList.add((View)findViewById(R.id.view_table25));
        tableList.add((View)findViewById(R.id.view_table26));


    }

    public class SeatAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonSeat();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                Toast.makeText(SeatActivity.this, "빈자리 조회 성공!!", Toast.LENGTH_SHORT).show();
                for(int i=0; i<seatDatas.size(); i++) {

                    if(seatDatas.get(i).getSeat_occupy_check().equals("1"))
                        tableList.get(i).setBackgroundColor(Color.parseColor("#E29700"));

                    else if(seatDatas.get(i).getSeat_occupy_check().equals("0"))
                        tableList.get(i).setBackgroundColor(Color.parseColor("#767677"));

                }

            } else {
                Toast.makeText(SeatActivity.this, "빈자리 조회 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    private String JsonSeat() {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.SEAT_URL);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    SeatData seatData = new SeatData();

                    JSONObject c = contacts.getJSONObject(i);
                    seatData.setSeat_idx(Integer.parseInt(c.getString("seat_idx")));
                    seatData.setSeat_occupy_check(c.getString("seat_occupy_check"));

                    seatDatas.add(seatData);

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

    View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId())
            {
                case R.id.button_home:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    activityManager.removeActvity(SeatActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_reservation:
                    intent = new Intent(getApplicationContext(), OrderActivity.class);
                    activityManager.removeActvity(SeatActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_seat:

                    break;
                case R.id.button_event:
                    intent = new Intent(getApplicationContext(), EventActivity.class);
                    activityManager.removeActvity(SeatActivity.this);
                    overridePendingTransition(0,0);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_point, menu);
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
                    cartData.removeAllCart();
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
