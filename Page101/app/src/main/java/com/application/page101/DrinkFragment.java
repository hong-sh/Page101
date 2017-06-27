package com.application.page101;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.application.adapter.DrinkDataAdapter;
import com.application.model.DrinkData;
import com.application.model.FragmentListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-10-12.
 */
public class DrinkFragment extends ListFragment implements FragmentListener {

    private int drink_flag;
    ArrayList<DrinkData> drinkDatas = new ArrayList<DrinkData>();

    ArrayAdapter<DrinkData> adapter;
    ProgressBar progressBar;

    public DrinkFragment() {

    }

    public static DrinkFragment newInstance(int drink_flag) {

        DrinkFragment drinkFragment = new DrinkFragment();
        Bundle args = new Bundle();
        args.putInt("drink_flag", drink_flag);
        drinkFragment.setArguments(args);
        return drinkFragment;

    }

    public void BindList() {

        drinkDatas.clear();

        OrderActivity orderActivity = (OrderActivity)getActivity();
        ArrayList<DrinkData> temp = orderActivity.getDrinkDatas();


        for (int i = 0; i < temp.size(); i++) {

            if (drink_flag == temp.get(i).getDrinkFlag()) {
                drinkDatas.add(temp.get(i));
            }

        }

        getListView().invalidateViews();
    }

    public void onCart(int position) {

        OrderActivity orderActivity = (OrderActivity)getActivity();
        orderActivity.setCartDatas(drinkDatas.get(position));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drink, container, false);

        drink_flag = getArguments().getInt("drink_flag");

        adapter = new DrinkDataAdapter(getActivity(), R.layout.listview_drink, drinkDatas, this);
        setListAdapter(adapter);

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        return rootView;
    }


}
