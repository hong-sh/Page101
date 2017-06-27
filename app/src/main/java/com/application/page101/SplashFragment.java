package com.application.page101;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.application.adapter.DrinkDataAdapter;
import com.application.model.DrinkData;
import com.application.model.FragmentListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-10-12.
 */
public class SplashFragment extends Fragment{

    int position;

    public SplashFragment() {

    }

    public static SplashFragment newInstance(int position) {

        SplashFragment splashFragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        splashFragment.setArguments(args);
        return splashFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        position = getArguments().getInt("position");
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView_splash);

        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.splash_page1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.splash_page2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.splash_page3);
                break;
            case 3:
                imageView.setImageResource(R.drawable.splash_page4);
                break;
            case 4:
                imageView.setImageResource(R.drawable.splash_page5);
                break;

        }

        return rootView;
    }


}
