package com.application.page101;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.adapter.EventDataAdapter;
import com.application.model.CartData;
import com.application.model.EventData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private PageApplication app;
    private ActivityManager activityManager;
    private CartData cartData;

    private ProgressBar progressBar;

    ArrayAdapter<EventData> adapter;
    ArrayList<EventData> eventDatas = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initLayout();
        initActionBar();
        new EventAsyncTask().execute();

    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication)getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(EventActivity.this);
        cartData = app.instanceCartData();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        listView = (ListView)findViewById(R.id.listView_event);
        adapter = new EventDataAdapter(EventActivity.this, R.layout.listview_event, eventDatas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(itemClickListener);

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


    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
            intent.putExtra("img", eventDatas.get(position).getEvent_image());
            activityManager.removeActvity(EventActivity.this);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }

    };

    View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId())
            {
                case R.id.button_home:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    activityManager.removeActvity(EventActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_reservation:
                    intent = new Intent(getApplicationContext(), OrderActivity.class);
                    activityManager.removeActvity(EventActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_seat:
                    intent = new Intent(getApplicationContext(), SeatActivity.class);
                    activityManager.removeActvity(EventActivity.this);
                    overridePendingTransition(0,0);
                    startActivity(intent);
                    break;
                case R.id.button_event:

                    break;
            }
        }
    };

    public class EventAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventDatas.clear();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonEvent();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

              adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(EventActivity.this, "이벤트 조회 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    private String JsonEvent() {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.EVENTINFO_URL);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    EventData eventData = new EventData();

                    JSONObject c = contacts.getJSONObject(i);
                    eventData.setEvent_idx(Integer.parseInt(c.getString("event_idx")));
                    eventData.setEvent_title(c.getString("event_title"));
                    eventData.setEvent_start_date(c.getString("event_start_date"));
                    eventData.setEvent_end_date(c.getString("event_end_date"));
                    eventData.setEvent_image(c.getString("event_image"));

                    eventDatas.add(eventData);

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
        //getMenuInflater().inflate(R.menu.menu_event, menu);
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
                    cartData.removeAllCart();
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
