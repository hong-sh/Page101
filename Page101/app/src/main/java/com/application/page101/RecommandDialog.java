package com.application.page101;


import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.application.util.JsonParse;
import com.application.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Hong on 2015-11-01.
 */
public class RecommandDialog extends DialogFragment {

    View layout_select, layout_result;
    ProgressBar progressBar;
    CheckBox checkBox_men, checkBox_women, checkBox_bad, checkBox_good, checkBox_sad, checkBox_soso, checkBox_sleep;
    TextView textView_name, textView_drinkname, textView_price;

    private int gender_flag = -1, feel_flag = -1;

    public RecommandDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_recommand, container);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        layout_select = (View) view.findViewById(R.id.layout_select);
        layout_result = (View) view.findViewById(R.id.layout_result);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        checkBox_men = (CheckBox) view.findViewById(R.id.checkBox_men);
        checkBox_women = (CheckBox) view.findViewById(R.id.checkBox_women);

        checkBox_men.setOnCheckedChangeListener(genderCheckChangeListener);
        checkBox_women.setOnCheckedChangeListener(genderCheckChangeListener);

        checkBox_bad = (CheckBox) view.findViewById(R.id.checkBox_bad);
        checkBox_good = (CheckBox) view.findViewById(R.id.checkBox_good);
        checkBox_sad = (CheckBox) view.findViewById(R.id.checkBox_sad);
        checkBox_soso = (CheckBox) view.findViewById(R.id.checkBox_soso);
        checkBox_sleep = (CheckBox) view.findViewById(R.id.checkBox_sleep);

        checkBox_bad.setOnCheckedChangeListener(feelCheckChangeListener);
        checkBox_good.setOnCheckedChangeListener(feelCheckChangeListener);
        checkBox_sad.setOnCheckedChangeListener(feelCheckChangeListener);
        checkBox_soso.setOnCheckedChangeListener(feelCheckChangeListener);
        checkBox_sleep.setOnCheckedChangeListener(feelCheckChangeListener);

        textView_name = (TextView)view.findViewById(R.id.textView_name);
        textView_drinkname = (TextView)view.findViewById(R.id.textView_drinkname);
        textView_price = (TextView)view.findViewById(R.id.textView_drinkprice);


        ((ImageView) view.findViewById(R.id.imageView_cancel)).setOnClickListener(buttonClickListener);
        ((Button) view.findViewById(R.id.button_ok)).setOnClickListener(buttonClickListener);
        ((Button) view.findViewById(R.id.button_re)).setOnClickListener(buttonClickListener);

        return view;
    }

    CompoundButton.OnCheckedChangeListener genderCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {
                case R.id.checkBox_men:
                    if(isChecked) {
                        checkBox_women.setChecked(false);
                        gender_flag =0;
                    } else {
                        gender_flag =-1;
                    }
                    break;
                case R.id.checkBox_women:
                    if(isChecked) {
                        checkBox_men.setChecked(false);
                        gender_flag =1;
                    } else {
                        gender_flag =-1;
                    }
                    break;
            }
        }
    };

    CompoundButton.OnCheckedChangeListener feelCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId())
            {
                case R.id.checkBox_bad:
                    if(isChecked) {
                        checkBox_good.setChecked(false);
                        checkBox_sad.setChecked(false);
                        checkBox_soso.setChecked(false);
                        checkBox_sleep.setChecked(false);
                        feel_flag = 0;
                    } else {
                        feel_flag =-1;
                    }
                    break;
                case R.id.checkBox_good:
                    if(isChecked) {
                        checkBox_bad.setChecked(false);
                        checkBox_sad.setChecked(false);
                        checkBox_soso.setChecked(false);
                        checkBox_sleep.setChecked(false);
                        feel_flag = 1;
                    } else {
                        feel_flag =-1;
                    }
                    break;
                case R.id.checkBox_sad:
                    if(isChecked) {
                        checkBox_bad.setChecked(false);
                        checkBox_good.setChecked(false);
                        checkBox_soso.setChecked(false);
                        checkBox_sleep.setChecked(false);
                        feel_flag = 2;
                    } else {
                        feel_flag =-1;
                    }
                    break;
                case R.id.checkBox_soso:
                    if(isChecked) {
                        checkBox_bad.setChecked(false);
                        checkBox_good.setChecked(false);
                        checkBox_sad.setChecked(false);
                        checkBox_sleep.setChecked(false);
                        feel_flag = 3;
                    } else {
                        feel_flag =-1;
                    }
                    break;
                case R.id.checkBox_sleep:
                    if(isChecked) {
                        checkBox_bad.setChecked(false);
                        checkBox_good.setChecked(false);
                        checkBox_sad.setChecked(false);
                        checkBox_soso.setChecked(false);
                        feel_flag = 4;
                    } else {
                        feel_flag =-1;
                    }
                    break;
            }
        }
    };

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.imageView_cancel:
                    dismiss();
                    break;
                case R.id.button_ok:
                    if(gender_flag ==-1 ){
                        Toast.makeText(getContext(), "성별을 선택해 주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                    }else if(feel_flag == -1) {
                        Toast.makeText(getContext(), "현재 감정을 선택해 주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                    } else {
                        new RecommenAsyncTask().execute();
                    }
                    break;
                case R.id.button_re:
                    if(gender_flag ==-1 ){
                        Toast.makeText(getContext(), "성별을 선택해 주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                    }else if(feel_flag == -1) {
                        Toast.makeText(getContext(), "현재 감정을 선택해 주세연 ㅠㅠ", Toast.LENGTH_SHORT).show();
                    } else {
                        new RecommenAsyncTask().execute();
                    }
                    break;
            }
        }
    };



    public class RecommenAsyncTask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {

            if(JsonRecommandCount().equals("true")) {

            }
                return JsonRecommandMenu(Random(recommend_count));
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {

                textView_name.setText(getTag() +"님 의");
                textView_drinkname.setText(menu_name);
                textView_price.setText(menu_price +" 원");


                layout_select.setVisibility(View.GONE);
                layout_result.setVisibility(View.VISIBLE);

            } else {
                Toast.makeText(getContext(), "음료 추천 실패ㅠㅠ", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    int recommend_count;

    private String JsonRecommandCount() {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.RECOMMEND_COUNT_URL);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    recommend_count = Integer.parseInt(c.getString("menu_count"));

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

    private String Random(int count) {

        Random random = new Random();
        int idx = random.nextInt(count-1);

        return String.valueOf(idx);

    }

    String menu_name ="", menu_price="", menu_category="";

    private String JsonRecommandMenu(String menu_idx) {

        String result = "";

        try {
            JsonParse jsonParse = new JsonParse();
            JSONObject json;

            json = jsonParse.getJSONFromUrl(URLDefine.RECOMMEND_INFO_URL  +"menu_idx=" +menu_idx);


            if (json != null) {

                JSONArray contacts = json.getJSONArray("result");

                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    menu_name = c.getString("menu_name");
                    menu_price = c.getString("menu_price");
                    menu_category = c.getString("menu_category");

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
}
