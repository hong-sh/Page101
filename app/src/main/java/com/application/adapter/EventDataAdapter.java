package com.application.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.application.model.EventData;
import com.application.page101.R;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Hong on 2015-10-30.
 */
public class EventDataAdapter extends ArrayAdapter<EventData> {

    private Context context;
    private ArrayList<EventData> items;


    public EventDataAdapter(Context context, int layoutViewResourceId, ArrayList<EventData> items) {
        super(context, layoutViewResourceId, items);

        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_event, null);
        }

        EventData eventData = items.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = new Date();
        Date endDate = new Date();
        Date currentDate1 = new Date();
        Date currentDate2 = new Date();

        try {

            startDate = dateFormat.parse(eventData.getEvent_start_date());
            endDate = dateFormat.parse(eventData.getEvent_end_date());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) convertView.findViewById(R.id.textView_event_name)).setText(eventData.getEvent_title());
        ((TextView) convertView.findViewById(R.id.textView_event_start)).setText(eventData.getEvent_start_date());
        ((TextView) convertView.findViewById(R.id.textView_event_end)).setText(eventData.getEvent_end_date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate1);
        calendar.add(Calendar.DATE, -1);
        currentDate1 = calendar.getTime();

        if (currentDate1.after(endDate)) {
            ((Button) convertView.findViewById(R.id.button_state)).setText("완료");
            ((Button) convertView.findViewById(R.id.button_state)).setBackgroundResource(R.drawable.button_round_gray);
            return convertView;
        }

        if (currentDate2.before(startDate)) {
            ((Button) convertView.findViewById(R.id.button_state)).setText("예정");
            ((Button) convertView.findViewById(R.id.button_state)).setBackgroundResource(R.drawable.button_round_darkgray);
            return convertView;
        }

        ((Button) convertView.findViewById(R.id.button_state)).setText("진행중");
        ((Button) convertView.findViewById(R.id.button_state)).setBackgroundResource(R.drawable.button_round_orange);


        return convertView;
    }

}
