package com.application.page101;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.application.model.CartData;
import com.application.model.DrinkData;
import com.application.model.InputFilterMinMax;
import com.application.model.MemberData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PayActivity extends AppCompatActivity {

    private PageApplication app;
    private CartData cartData;
    private MemberData memberData;
    private ActivityManager activityManager;

    private String sDate[];
    private String order_total_price, member_email, order_reservation_time, intent_order_reservation_time, save_point;

    private TextView textView_total_pay;
    private EditText editText_date;
    private ProgressBar progressBar;


    AlertDialog orderDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initLayout();
        initActionBar();

    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication) getApplication();
        cartData = app.instanceCartData();
        memberData = app.instanceMemberData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(PayActivity.this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ((EditText) findViewById(R.id.editText_name)).setText(memberData.getMember_name());
        ((EditText) findViewById(R.id.editText_phone)).setText(memberData.getMember_phone());
        ((TextView) findViewById(R.id.textView_total)).setText(String.valueOf(cartData.getTotalPrice()));
        ((TextView) findViewById(R.id.textView_total_save)).setText(String.valueOf(cartData.getTotalSavemoney()));
        ((TextView) findViewById(R.id.textView_useful_point)).setText(memberData.getMember_point());
        ((EditText) findViewById(R.id.editText_use_point)).setFilters(new InputFilter[]{new InputFilterMinMax("1", memberData.getMember_point())});
        ((EditText) findViewById(R.id.editText_use_point)).addTextChangedListener(textWatcher);
        textView_total_pay = (TextView) findViewById(R.id.textView_total_pay);
        textView_total_pay.setText(String.valueOf(cartData.getTotalPrice()));
        ((Button)findViewById(R.id.button_next)).setOnClickListener(buttonClickListener);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String myDate = format.format(new Date());
        sDate = myDate.split(":");

        editText_date = (EditText) findViewById(R.id.editText_date);
        editText_date.setText(myDate);
        editText_date.setOnClickListener(timeClickListener);

    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_order)));
            actionBar.setTitle("");
        }
    }

    private View.OnClickListener timeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new TimePickerDialog(PayActivity.this, timeSetListener, Integer.parseInt(sDate[0]), Integer.parseInt(sDate[1]), false).show();
        }
    };


    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String timeSet = String.format("%d:%d", hourOfDay, minute);
            editText_date.setText(timeSet);

        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() > 0)
                textView_total_pay.setText(String.valueOf((cartData.getTotalPrice()) - Integer.parseInt(s.toString())));
            if (s.length() == 0)
                textView_total_pay.setText(String.valueOf((cartData.getTotalPrice())));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            orderDialog = createOrderDialog();
            orderDialog.show();

        }
    };

    private AlertDialog createOrderDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("COFFE PAGE 101");
        ab.setMessage("음료를 예약 하시겠어연??");
        ab.setCancelable(false);
        ab.setIcon(getResources().getDrawable(R.drawable.app_icon));

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                setDismiss(orderDialog);

                order_total_price = ((TextView)findViewById(R.id.textView_total_pay)).getText().toString();
                member_email = memberData.getMember_email();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String myDate = format.format(new Date());


                order_reservation_time = myDate.toString() +"%20" +((EditText)findViewById(R.id.editText_date)).getText().toString();
                intent_order_reservation_time = myDate.toString() +" " +((EditText)findViewById(R.id.editText_date)).getText().toString();

                save_point = ((TextView)findViewById(R.id.textView_total_save)).getText().toString();
                new OrderAsyncTask().execute();
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(orderDialog);
            }
        });

        return ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public class OrderAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonOrder(order_total_price, member_email, order_reservation_time, save_point);
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals("true")) {

                new OrderDetailAsyncTask().execute();

            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PayActivity.this, "음료 예약 실패", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    String isSuccess;
    String order_idx;

    private String JsonOrder(String order_total_price, String member_email, String order_reservation_time, String save_point) {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.ORDER_URL + "order_total_price=" + order_total_price + "&member_email=" + member_email +"&order_reservation_time=" +order_reservation_time +"&order_point=" +save_point);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    isSuccess = c.getString("isSuccess");
                    if (isSuccess.equals("false")) {
                        result = "false";
                        return result;
                    } else if(isSuccess.equals("true")) {
                        order_idx = c.getString("order_idx");
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

    public class OrderDetailAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            String result ="";

            for(int i=0; i<cartData.countCart(); i++) {

                DrinkData tmp = cartData.getCart(i);
                String menu_idx = String.valueOf(tmp.getDrinkIdx());
                String order_detail_amount = String.valueOf(tmp.getEa());
                String order_detail_shot_check;

                if(tmp.isShot() == true) {
                    order_detail_shot_check = "1";
                } else {
                    order_detail_shot_check = "0";
                }

                String order_detail_option = "";

                try {

                    if(tmp.getDrinkOption() != null)
                        order_detail_option = URLEncoder.encode(tmp.getDrinkOption(), "EUC-KR");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                result =  JsonOrderDetail(menu_idx, order_detail_amount, order_detail_shot_check, order_detail_option, order_idx);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

                cartData.removeAllCart();

                Intent intent = new Intent(getApplicationContext(), CompleteActivity.class);
                intent.putExtra("name",memberData.getMember_name());
                intent.putExtra("phone", memberData.getMember_phone());
                intent.putExtra("time", intent_order_reservation_time);
                intent.putExtra("use_point", ((EditText)findViewById(R.id.editText_use_point)).getText().toString());
                intent.putExtra("useful_point", memberData.getMember_point());
                intent.putExtra("order_total_price", order_total_price);
                activityManager.removeActvity(PayActivity.this);
                overridePendingTransition(0, 0);
                startActivity(intent);

            } else {
                Toast.makeText(PayActivity.this, "음료 예약 실패", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    String isSuccess_detail;

    private String JsonOrderDetail(String menu_idx, String order_detail_amount, String order_detail_shot_check, String order_detail_option, String order_idx) {

        String result = "false";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.ORDER_DETAIL_URL + "menu_idx=" + menu_idx + "&order_detail_amount=" + order_detail_amount +"&order_detail_shot_check=" +order_detail_shot_check +"&order_detail_option='" +order_detail_option +"'&order_idx=" +order_idx);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    isSuccess_detail = c.getString("isSuccess");
                    if (isSuccess_detail.equals("false")) {
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
            Intent intent = new Intent(PayActivity.this, CartActivity.class);
            activityManager.removeActvity(PayActivity.this);
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
                Intent intent = new Intent(PayActivity.this, CartActivity.class);
                activityManager.removeActvity(PayActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);
        }
        return false;
    }


}
