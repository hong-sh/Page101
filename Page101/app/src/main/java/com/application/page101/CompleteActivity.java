package com.application.page101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.application.model.MemberData;
import com.application.util.ActivityManager;
import com.application.util.PageApplication;

import java.lang.reflect.Member;

public class CompleteActivity extends Activity {

    PageApplication app;
    ActivityManager activityManager;
    MemberData memberData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        initLayout();

    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication)getApplication();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(CompleteActivity.this);
        memberData = app.instanceMemberData();

        Intent intent = getIntent();
        ((TextView)findViewById(R.id.textView_name)).setText(intent.getStringExtra("name"));
        ((TextView)findViewById(R.id.textView_phone)).setText(intent.getStringExtra("phone"));
        ((TextView)findViewById(R.id.textView_time)).setText(intent.getStringExtra("time"));
        ((TextView)findViewById(R.id.textView_used_point)).setText(intent.getStringExtra("use_point"));
        ((TextView)findViewById(R.id.textView_useful_point)).setText(intent.getStringExtra("useful_point"));
        ((TextView)findViewById(R.id.textView_total_price)).setText(intent.getStringExtra("order_total_price"));

        ((Button)findViewById(R.id.button_home)).setOnClickListener(buttonClickListener);
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(CompleteActivity.this, MainActivity.class);
            activityManager.removeActvity(CompleteActivity.this);
            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_point, menu);
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
}
