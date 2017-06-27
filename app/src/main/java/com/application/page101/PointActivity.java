package com.application.page101;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.adapter.PointDataAdapter;
import com.application.model.MemberData;
import com.application.model.PointData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PointActivity extends AppCompatActivity {

    PageApplication app;
    ActivityManager activityManager;
    MemberData memberData;

    private ProgressBar progressBar;

    ListView listView;
    ArrayAdapter<PointData> adapter;
    ArrayList<PointData> pointDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        initLayout();
        initActionBar();

        new PointAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication)getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(PointActivity.this);
        memberData = app.instanceMemberData();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        ((TextView)findViewById(R.id.textView_useful_point)).setText(memberData.getMember_point());

        listView = (ListView)findViewById(R.id.listView_history);
        adapter = new PointDataAdapter(PointActivity.this, R.layout.listview_point, pointDatas);
        listView.setAdapter(adapter);
    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //R.id.home
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_point)));
            actionBar.setTitle("");
        }
    }

    public class PointAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pointDatas.clear();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonPoint();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(PointActivity.this, "포인트 내역 조회 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    private String JsonPoint() {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.POINT_URL +"member_idx=" +memberData.getMember_idx());


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    PointData pointData = new PointData();

                    JSONObject c = contacts.getJSONObject(i);

                    pointData.setFirst_menu(c.getString("first_menu"));
                    pointData.setAmount(c.getString("amount"));
                    pointData.setRecode_point(c.getString("record_point"));
                    pointData.setOrder_date(c.getString("order_date"));

                    pointDatas.add(pointData);

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
        if (id == android.R.id.home) {
            Intent intent = new Intent(PointActivity.this, MainActivity.class);
            activityManager.removeActvity(PointActivity.this);
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
                Intent intent = new Intent(PointActivity.this, MainActivity.class);
                activityManager.removeActvity(PointActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);
        }
        return false;
    }
}
