package com.application.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.model.DrinkData;
import com.application.page101.CartActivity;
import com.application.page101.R;

import java.util.ArrayList;

/**
 * Created by Hong on 2015-10-26.
 */
public class CartDataAdapter extends ArrayAdapter<DrinkData> {

    private Context context;
    private ArrayList<DrinkData> items;

    private TextView textView_drinkname, textView_price, textView_save_money, textView_ea;
    private ImageView imageView_delete;
    private EditText editText;


    public CartDataAdapter(Context context, int layoutViewResourceId, ArrayList<DrinkData> items) {
        super(context, layoutViewResourceId, items);

        this.context = context;

        this.items = items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_order, null);
        }

        textView_drinkname = (TextView) convertView.findViewById(R.id.textView_drinkname);
        textView_price = (TextView) convertView.findViewById(R.id.textView_price);
        textView_save_money = (TextView) convertView.findViewById(R.id.textView_save_money);
        textView_ea = (TextView) convertView.findViewById(R.id.textView_ea);
        imageView_delete = (ImageView) convertView.findViewById(R.id.imageView_delete);
        imageView_delete.setOnClickListener(deleteClickListener);
        editText = (EditText) convertView.findViewById(R.id.editText_option);

        DrinkData drinkData = items.get(position);

        if ((drinkData.getDrinkFlag() == 0 || drinkData.getDrinkFlag() == 1) == true && drinkData.isShot() == true) {

            ((LinearLayout) convertView.findViewById(R.id.layout_shot)).setVisibility(View.VISIBLE);

        } else {

            ((LinearLayout) convertView.findViewById(R.id.layout_shot)).setVisibility(View.GONE);

        }

        textView_drinkname.setTag(position);
        textView_price.setTag(position);
        textView_save_money.setTag(position);
        textView_ea.setTag(position);
        imageView_delete.setTag(position);
        editText.setTag(position);

        editText.setOnFocusChangeListener(new MyFocusChangeListener(editText));
        editText.setText(drinkData.getDrinkOption());

        textView_drinkname.setText(drinkData.getDrinkName());
        textView_price.setText(String.valueOf(drinkData.getOrderPrice()));
        textView_save_money.setText(String.valueOf(drinkData.getOrderPrice() / 10));

        return convertView;
    }

    public class MyFocusChangeListener implements View.OnFocusChangeListener {

        private EditText editText;

        public MyFocusChangeListener(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            items.get((int)v.getTag()).setDrinkOption(editText.getText().toString());
        }
    }


    View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (context instanceof CartActivity) {
                ((CartActivity) context).deleteCart((int) v.getTag());
            }
            notifyDataSetChanged();
        }
    };
}
