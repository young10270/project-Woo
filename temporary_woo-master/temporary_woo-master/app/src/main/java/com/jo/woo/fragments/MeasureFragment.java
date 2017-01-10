package com.jo.woo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jo.woo.Constants;
import com.jo.woo.MainActivity;
import com.jo.woo.R;

/**
 * Created by jo on 2016-01-28.
 */
public class MeasureFragment extends Fragment {
    private View wholeView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle data){
        wholeView = inflater.inflate(R.layout.fragment_measure, null);
        return wholeView;
    }

    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        ((Button)wholeView.findViewById(R.id.btnMeasure_measure)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.btnMeasure_back)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.bluetoothButton)).setOnClickListener(click);
//        ((Button)wholeView.findViewById(R.id.moveToValue)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnMeasure_measure){
                ((MainActivity)getActivity()).getDataFromBluetooth();
            }
            else if(v.getId() == R.id.btnMeasure_back)
                ((MainActivity)getActivity()).moveFragment(Constants.MENU);
            else if(v.getId() == R.id.bluetoothButton){
                ((MainActivity)getActivity()).connectBluetooth();
            }
        }
    };
}
