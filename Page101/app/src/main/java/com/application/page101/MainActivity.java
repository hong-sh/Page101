package com.application.page101;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.model.CartData;
import com.application.model.MemberData;
import com.application.util.ActivityManager;
import com.application.util.PageApplication;
import com.google.android.gms.wallet.Cart;

public class MainActivity extends AppCompatActivity {

    private PageApplication app;
    private MemberData memberData;
    private CartData cartData;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
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
        cartData = app.instanceCartData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(this);
        ((TextView)findViewById(R.id.textView_point)).setText(memberData.getMember_point());

        ((Button)findViewById(R.id.buttonPoint)).setOnClickListener(buttonClickListener);
        ((Button)findViewById(R.id.buttonMypage)).setOnClickListener(buttonClickListener);

        ((ImageView)findViewById(R.id.button_home)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_reservation)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_seat)).setOnClickListener(bottomClickListener);
        ((ImageView)findViewById(R.id.button_event)).setOnClickListener(bottomClickListener);

    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeButtonEnabled(true);
            //R.id.home
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_main)));
            actionBar.setTitle("");
        }
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId())
            {
                case R.id.buttonPoint:
                    intent = new Intent(getApplicationContext(), PointActivity.class);
                    activityManager.removeActvity(MainActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.buttonMypage:
                    intent = new Intent(getApplicationContext(), MyPageActivity.class);
                    activityManager.removeActvity(MainActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
            }

        }
    };

    View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId())
            {
                case R.id.button_home:
                    break;
                case R.id.button_reservation:
                    intent = new Intent(getApplicationContext(), OrderActivity.class);
                    activityManager.removeActvity(MainActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_seat:
                    intent = new Intent(getApplicationContext(), SeatActivity.class);
                    activityManager.removeActvity(MainActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_event:
                    intent = new Intent(getApplicationContext(), EventActivity.class);
                    activityManager.removeActvity(MainActivity.this);
                    overridePendingTransition(0,0);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
