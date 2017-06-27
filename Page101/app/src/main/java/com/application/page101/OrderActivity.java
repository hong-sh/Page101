package com.application.page101;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.model.CartData;
import com.application.model.DrinkData;
import com.application.model.FragmentListener;
import com.application.model.MemberData;
import com.application.util.ActivityManager;
import com.application.util.JsonParse;
import com.application.util.PageApplication;
import com.application.util.URLDefine;
import com.google.android.gms.actions.ReserveIntents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ProgressBar progressBar;
    private Menu menu;

    private ArrayList<DrinkData> drinkDatas = new ArrayList<DrinkData>();
    private CartData cartDatas;

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    private int current_position = 0;

    private PageApplication app;
    private ActivityManager activityManager;
    private MemberData memberData;

    AlertDialog cartDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initLayout();
        initActionBar();
        new MenuInfoAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(0, 0);
        super.onResume();
    }

    private void initLayout() {

        app = (PageApplication)getApplication();
        cartDatas = app.instanceCartData();
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(OrderActivity.this);
        memberData = app.instanceMemberData();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);



        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                actionBar.setSelectedNavigationItem(position);
                mViewPager.setCurrentItem(position);
                current_position = position;

                Fragment fragment = fragmentSparseArray.get(current_position);
                FragmentListener listener = (DrinkFragment)fragment;
                listener.BindList();

            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(tabListener));
        }

        actionBar.setSelectedNavigationItem(0);

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


    View.OnClickListener bottomClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId())
            {
                case R.id.button_home:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    activityManager.removeActvity(OrderActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_reservation:

                    break;
                case R.id.button_seat:
                    intent = new Intent(getApplicationContext(), SeatActivity.class);
                    activityManager.removeActvity(OrderActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
                case R.id.button_event:
                    intent = new Intent(getApplicationContext(), EventActivity.class);
                    activityManager.removeActvity(OrderActivity.this);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    break;
            }
        }
    };

    ActionBar.TabListener tabListener = new ActionBar.TabListener() {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            mViewPager.setCurrentItem(tab.getPosition());
            current_position = tab.getPosition();

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    };


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).

            Fragment fragment = DrinkFragment.newInstance(position);
            fragmentSparseArray.put(position, fragment);

            return fragment;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //Locale l = Locale.getDefault();
            switch (position) {

                case 0:
                    return "  Coffee  ".toString();
                case 1:
                    return "  ICE COFFEE  ".toString();
                case 2:
                    return "  Frappuccino  ".toString();
                case 3:
                    return "  Beverage  ".toString();
                case 4:
                    return "  Smoothie  ".toString();
                case 5:
                    return "  Tea  ".toString();
                case 6:
                    return "  Ade  ".toString();
                case 7:
                    return "  Fruits  ".toString();
                case 8:
                    return "  Drip Coffee  ".toString();
            }
            return null;
        }
    }


    public class MenuInfoAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            return JsonMenuInfo();
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

            if (result.equals("true")) {

                Fragment fragment = fragmentSparseArray.get(0);
                FragmentListener listener = (DrinkFragment)fragment;
                listener.BindList();

            } else {
                Toast.makeText(OrderActivity.this, "메뉴를 가져오는데 실패하였습니다.ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public ArrayList<DrinkData> getDrinkDatas() {
        return this.drinkDatas;
    }

    public void setCartDatas(DrinkData drinkData) {

        cartDatas.addCart(drinkData);

        if(cartDatas.countCart() > 0) {
            menu.getItem(1).setIcon(R.drawable.icon_cart_new);
        }

        for(int i=0; i<cartDatas.countCart(); i++) {
            DrinkData temp = cartDatas.getCart(i);

        }
    }

    String isSuccess;

    private String JsonMenuInfo() {

        String result = "";
        DrinkData drinkData;
        drinkDatas.clear();
        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.MENUINFO_URL);

            if (json != null) {
                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);

                    drinkData = new DrinkData();
                    drinkData.setDrinkIdx(Integer.parseInt(c.getString("menu_idx")));
                    drinkData.setDrinkName(c.getString("menu_name"));
                    drinkData.setDrinkPrice(Integer.parseInt(c.getString("menu_price")));
                    drinkData.setDrinkFlag(Integer.parseInt(c.getString("menu_category")));
                    drinkData.setEa(1);
                    drinkData.setOrderPrice(drinkData.getDrinkPrice());

                    drinkDatas.add(drinkData);

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
        getMenuInflater().inflate(R.menu.menu_order, menu);
        this.menu = menu;

        if(cartDatas.countCart() > 0 ) {
            menu.getItem(1).setIcon(R.drawable.icon_cart_new);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_recommand) {
            FragmentManager fm = getSupportFragmentManager();
            RecommandDialog recommandDialog = new RecommandDialog();
            recommandDialog.show(fm, memberData.getMember_name());
        }

        if (id == R.id.action_cart) {

            if(cartDatas.countCart() > 0) {
                Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                activityManager.removeActvity(OrderActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);
            } else {
                cartDialog = createCartDialog();
                cartDialog.show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog createCartDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("COFFE PAGE 101");

        ab.setMessage("장바구니에 음료가 없어연ㅠㅠ");
        ab.setCancelable(false);
        ab.setIcon(getResources().getDrawable(R.drawable.app_icon));

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(cartDialog);
            }
        });

        return ab.create();
    }

    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
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
                    cartDatas.removeAllCart();
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
