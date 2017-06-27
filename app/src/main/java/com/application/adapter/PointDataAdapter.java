package com.application.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.application.model.EventData;
import com.application.model.PointData;
import com.application.page101.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hong on 2015-10-30.
 */
public class PointDataAdapter extends ArrayAdapter<PointData> {

    private Context context;
    private ArrayList<PointData> items;


    public PointDataAdapter(Context context, int layoutViewResourceId, ArrayList<PointData> items) {
        super(context, layoutViewResourceId, items);

        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_point, null);
        }

        PointData pointData = items.get(position);

        String date[] = pointData.getOrder_date().split(" ");

        ((TextView)convertView.findViewById(R.id.textView_date)).setText(date[0]);

        if(Integer.parseInt(pointData.getAmount()) > 0)
            ((TextView)convertView.findViewById(R.id.textView_history)).setText(pointData.getFirst_menu() + " 외 " + pointData.getAmount() + "음료");
        else
            ((TextView)convertView.findViewById(R.id.textView_history)).setText(pointData.getFirst_menu());

        if(Integer.parseInt(pointData.getRecode_point()) > 0)
            ((TextView)convertView.findViewById(R.id.textView_symbol)).setVisibility(View.VISIBLE);

        ((TextView)convertView.findViewById(R.id.textView_point)).setText(pointData.getRecode_point());

        return convertView;
    }

}

