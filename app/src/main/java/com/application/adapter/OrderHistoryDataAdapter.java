package com.application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.model.OrderHistoryData;
import com.application.model.PointData;
import com.application.page101.R;

import java.util.ArrayList;

/**
 * Created by Hong on 2015-10-30.
 */
public class OrderHistoryDataAdapter extends ArrayAdapter<OrderHistoryData> {

    private Context context;
    private ArrayList<OrderHistoryData> items;


    public OrderHistoryDataAdapter(Context context, int layoutViewResourceId, ArrayList<OrderHistoryData> items) {
        super(context, layoutViewResourceId, items);

        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_order_history, null);
        }

        OrderHistoryData orderHistoryData = items.get(position);

        String date[] = orderHistoryData.getOrder_date().split(" ");

        ((TextView)convertView.findViewById(R.id.textView_date)).setText(date[0]);
        if(Integer.parseInt(orderHistoryData.getAmount()) > 0)
            ((TextView)convertView.findViewById(R.id.textView_history)).setText(orderHistoryData.getFirst_menu() + " 외 " + orderHistoryData.getAmount() + "음료");
        else
            ((TextView)convertView.findViewById(R.id.textView_history)).setText(orderHistoryData.getFirst_menu());

        ((TextView)convertView.findViewById(R.id.textView_price)).setText(orderHistoryData.getOrder_total_price());

        return convertView;
    }

}

