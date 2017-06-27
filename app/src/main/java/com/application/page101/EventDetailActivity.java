package com.application.page101;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.util.ActivityManager;
import com.application.util.PageApplication;
import com.application.util.URLDefine;

import java.io.IOException;
import java.io.InputStream;

public class EventDetailActivity extends AppCompatActivity {

    private PageApplication app;
    private ActivityManager activityManager;

    private ImageView imageVIew;
    private ProgressBar progressBar;

    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

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
        activityManager = app.instanceActivityManager();
        activityManager.addActivity(EventDetailActivity.this);

        imageVIew = (ImageView)findViewById(R.id.imageView_event);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Intent intent = getIntent();
        img = intent.getStringExtra("img");

        new ImageAsyncTask().execute();

    }

    private void initActionBar() {
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.action_event)));
            actionBar.setTitle("");
        }
    }

    public class ImageAsyncTask extends AsyncTask<Void, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmap = null;
            try {
                InputStream is = new java.net.URL(URLDefine.IMAGE_URL + img).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                Log.e("ImageLoaderTask", "Cannot load image from ");
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.GONE);
            if(result != null)
                imageVIew.setImageBitmap(result);
            else
                Toast.makeText(EventDetailActivity.this, "이미지 로드 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
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
        if (id == android.R.id.home) {
            Intent intent = new Intent(EventDetailActivity.this, EventActivity.class);
            activityManager.removeActvity(EventDetailActivity.this);
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
                Intent intent = new Intent(EventDetailActivity.this, EventActivity.class);
                activityManager.removeActvity(EventDetailActivity.this);
                overridePendingTransition(0,0);
                startActivity(intent);
        }
        return false;
    }
}
