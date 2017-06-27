package com.application.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.model.DrinkData;
import com.application.model.FragmentListener;
import com.application.page101.DrinkFragment;
import com.application.page101.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-10-13.
 */
public class DrinkDataAdapter extends ArrayAdapter<DrinkData> {

    private Context context;
    private ArrayList<DrinkData> items;

    private TextView textView_name, textView_price, textView_ea;
    private CheckBox checkBox_shot;
    private Button button_minus_ea, button_plus_ea, button_cart;

    DrinkFragment fragment;

    public DrinkDataAdapter(Context context, int layoutViewResourceId, ArrayList<DrinkData> items, DrinkFragment fragment) {
        super(context, layoutViewResourceId, items);

        this.context = context;
        this.items = items;
        this.fragment = fragment;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_drink, null);
        }

        textView_name = (TextView)convertView.findViewById(R.id.textView_name);
        textView_price = (TextView)convertView.findViewById(R.id.textView_price);
        textView_ea = (TextView)convertView.findViewById(R.id.textView_ea);
        checkBox_shot = (CheckBox)convertView.findViewById(R.id.checkbox_shot);
        button_minus_ea = (Button)convertView.findViewById(R.id.button_minus_ea);
        button_plus_ea = (Button)convertView.findViewById(R.id.button_plus_ea);
        button_cart = (Button)convertView.findViewById(R.id.button_cart);

        DrinkData drinkData = items.get(position);

        if(drinkData.getDrinkFlag() == 0 || drinkData.getDrinkFlag() == 1) {
            ((LinearLayout)convertView.findViewById(R.id.layout_shot)).setVisibility(View.VISIBLE);
        } else {
            ((LinearLayout)convertView.findViewById(R.id.layout_shot)).setVisibility(View.GONE);
        }

        textView_name.setTag(position);
        textView_price.setTag(position);
        textView_ea.setTag(position);
        checkBox_shot.setTag(position);
        button_minus_ea.setTag(position);
        button_plus_ea.setTag(position);
        button_cart.setTag(position);

        textView_name.setText(drinkData.getDrinkName());
        textView_price.setText(String.valueOf(drinkData.getOrderPrice()));
        textView_ea.setText(String.valueOf(drinkData.getEa()));

        checkBox_shot.setOnCheckedChangeListener(checkedChangeListener);
        checkBox_shot.setChecked(drinkData.isShot());

        button_minus_ea.setOnClickListener(buttonClick_ea);
        button_plus_ea.setOnClickListener(buttonClick_ea);
        button_cart.setOnClickListener(buttonClick_cart);

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            DrinkData item = items.get((int) buttonView.getTag());
            item.setShot(isChecked);

            int updatePrice =0;

            if (buttonView.getId() == R.id.checkbox_shot) {
                if (isChecked) {
                    updatePrice = item.getEa() * 500;

                } else {

                    updatePrice = -1 * (item.getEa() * 500);

                }

            }
            item.setOrderPrice(item.getOrderPrice() + updatePrice);
            notifyDataSetChanged();

        }
    };

    View.OnClickListener buttonClick_ea = new View.OnClickListener() {
        @Override

        public void onClick(View v) {

            DrinkData item = items.get((int)v.getTag());

            switch (v.getId()) {
                case R.id.button_minus_ea:
                    if (item.getEa() > 1)
                        item.setEa(item.getEa() - 1);
                    break;

                case R.id.button_plus_ea:
                    item.setEa(item.getEa() + 1);
                    break;
            }

            if(item.isShot() == true) {
                item.setOrderPrice((item.getDrinkPrice() * item.getEa()) + (item.getEa() * 500));
            }else {
                item.setOrderPrice(item.getDrinkPrice() * item.getEa());
            }
            notifyDataSetChanged();
        }
    };

    View.OnClickListener buttonClick_cart = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentListener listener = (DrinkFragment)fragment;
            listener.onCart((int) v.getTag());

            DrinkData item = items.get((int)v.getTag());
            item.setOrderPrice(item.getDrinkPrice());
            item.setShot(false);
            item.setEa(1);
            notifyDataSetChanged();

            Toast.makeText(context, "음료가 장바구니에 담겼습니다.\n오른쪽 상단 아이콘을 확인하세연~",
                    Toast.LENGTH_SHORT).show();

        }
    };

}
