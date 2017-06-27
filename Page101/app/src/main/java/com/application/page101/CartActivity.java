package com.application.page101;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.application.adapter.CartDataAdapter;
import com.application.model.CartData;
import com.application.model.DrinkData;
import com.application.util.ActivityManager;
import com.application.util.PageApplication;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    private ArrayList<DrinkData> drinkDatas = new ArrayList<>();
    private TextView textView_total, textView_total_save;

    ArrayAdapter<DrinkData> adapter;

    private PageApplication app;
    private CartData cartDatas;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initLayout();
        initActionBar();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        textView_total = (TextView) findViewById(R.id.textView_total);
        textView_total_save = (TextView) findViewById(R.id.textView_save_moeny);

        app = (PageApplication) getApplication();
        cartDatas = app.instanceCartData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(CartActivity.this);

        drinkDatas = cartDatas.getAllCart();
        adapter = new CartDataAdapter(this, R.layout.listview_order, drinkDatas);
        ((ListView) findViewById(R.id.listView)).setAdapter(adapter);

        textView_total.setText(String.valueOf(cartDatas.getTotalPrice()));
        textView_total_save.setText(String.valueOf(cartDatas.getTotalSavemoney()));

        ((Button) findViewById(R.id.button_select)).setOnClickListener(buttonClickListener);

    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            //R.id.home
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_cart)));
            actionBar.setTitle("");
        }
    }

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(CartActivity.this, PayActivity.class);
            activityManager.removeActvity(CartActivity.this);
            startActivity(intent);
        }
    };

    public void deleteCart(int position) {

        cartDatas.removeCart(drinkDatas.get(position));
        textView_total.setText(String.valueOf(cartDatas.getTotalPrice()));
        textView_total_save.setText(String.valueOf(cartDatas.getTotalSavemoney()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_cart, menu);
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
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            activityManager.removeActvity(CartActivity.this);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                activityManager.removeActvity(CartActivity.this);
                startActivity(intent);
        }
        return false;
    }
}
