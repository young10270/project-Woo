package com.jo.woo.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jo.woo.Constants;
import com.jo.woo.MainActivity;
import com.jo.woo.R;

/**
 * Created by jo on 2016-01-28.
 */
public class GrowthinfoFragment extends Fragment {
    private String mName = null;
    private Integer mMonth = null;
    private String mAge = null;
    private String mGender = null;
    public GrowthinfoFragment (){}
    public GrowthinfoFragment (String Name, Integer Month, String Age, String Gender){
        this.mName = Name;
        this.mMonth = Month;
        this.mAge = Age;
        this.mGender = Gender;
    }

    private View wholeView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle data){
        wholeView = inflater.inflate(R.layout.fragment_growthinfo, null);
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
        ((Button)wholeView.findViewById(R.id.btnGrowthinfo_graph)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.btnGrowthinfo_develop)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.btnGrowthinfo_expect)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.button_fragment_forth_back)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnGrowthinfo_graph)
                //graph button clicked
                ((MainActivity)getActivity()).moveFragment(Constants.GRAPH);
            else if(v.getId() == R.id.btnGrowthinfo_develop) {
                if(mMonth <= 72)
                    ((MainActivity) getActivity()).moveFragment(Constants.DEVELOP);
                else
                {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("만 6세 이하의 아이 정보만 제공합니다.")
                            .setNegativeButton("확인",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dlg, int sumthin){ }
                            })
                            .show();
                }

            }
            else if(v.getId() == R.id.btnGrowthinfo_expect)
                //expect button clicked
                ((MainActivity)getActivity()).moveFragment(Constants.EXPECT);
            else if(v.getId() == R.id.button_fragment_forth_back)
                //back button clicked
                ((MainActivity)getActivity()).moveFragment(Constants.MENU);

        }
    };
}
