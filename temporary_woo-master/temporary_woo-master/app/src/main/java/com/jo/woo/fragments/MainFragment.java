package com.jo.woo.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.woo.Constants;
import com.jo.woo.MainActivity;
import com.jo.woo.R;

/**
 * Created by jo on 2016-02-11.
 */
public class MainFragment extends Fragment {
    private View wholeView = null;

    private String mName = null;
    private Integer mMonth = null;
    private String mAge = null;
    private String mGender = null;
    public MainFragment (){}
    public MainFragment (String Name, Integer Month, String Age, String Gender){
        this.mName = Name;
        this.mMonth = Month;
        this.mAge = Age;
        this.mGender = Gender;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle data){
        wholeView = inflater.inflate(R.layout.fragment_main, null);
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
        ((Button)wholeView.findViewById(R.id.btnMain_register)).setOnClickListener(click);
        ((Button)wholeView.findViewById(R.id.btnMain_menu)).setOnClickListener(click);
        if(mName == null)
            ((TextView)wholeView.findViewById(R.id.textViewMain)).setText("등록해 주세요.");
        else
            ((TextView)wholeView.findViewById(R.id.textViewMain)).setText(mName + " ("+ mGender +"/"+ mAge+")");
    }


    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnMain_register) {
                if(mName == null)
                    ((MainActivity) getActivity()).moveFragment(Constants.REGISTER);
                
                else
                {
                    new AlertDialog.Builder(getActivity())
                        .setMessage("등록시 기존의 데이터가 삭제됩니다.")
                        .setNegativeButton("취소",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dlg, int sumthin){

                            }
                        })
                        .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dig, int sumthin){
                                ((MainActivity) getActivity()).moveFragment(Constants.REGISTER);
                            }
                        })
                        .show();
                //Back button clicked
                //((MainActivity) getActivity()).moveFragment(Constants.REGISTER);
                }
            }
            if(v.getId() == R.id.btnMain_menu){
                if(mName != null)
                    ((MainActivity)getActivity()).moveFragment(Constants.MENU);
                else
                    Toast.makeText(getActivity(), "등록해 주세요.", Toast.LENGTH_LONG).show();

            }
        }
    };
}
