package com.jo.woo.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jo.woo.Constants;
import com.jo.woo.MainActivity;
import com.jo.woo.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jo on 2016-01-28.
 */
public class RegisterFragment extends Fragment {
    DatePicker datePicker;
    private int s_year, s_month, s_day; //선택된 날짜
    private int c_year, c_month, c_day; //오늘 날짜
    private Calendar calendar;

    private View wholeView=null;
    private boolean gender = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle date){
        wholeView = inflater.inflate(R.layout.fragment_register, null);
        //bring root view
        //bind to wholeView
        return wholeView;
        //must return wholeView for showing xml , null-> nullPointerException
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
        //make listener and convey to makeView
        ((Button) wholeView.findViewById(R.id.btnRegister_login)).setOnClickListener(click);
        //Register button click listener
        ((RadioButton)wholeView.findViewById(R.id.radio_Male)).setOnCheckedChangeListener(check);
        ((RadioButton)wholeView.findViewById(R.id.radio_Female)).setOnCheckedChangeListener(check);

        //String name = ((MainActivity)getActivity()).getName();
        //Integer month = ((MainActivity)getActivity()).getMonth();
        //String gender = ((MainActivity)getActivity()).getGender();

        datePicker = (DatePicker)wholeView.findViewById(R.id.datepicker);

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        s_year = year;
                        s_month = monthOfYear + 1;
                        s_day = dayOfMonth;
                    }
                });
    }

    CheckBox.OnCheckedChangeListener check = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.getId()==R.id.radio_Male)
                if(isChecked)
                    gender = true;
            //남아가 체크 됐을때

            if(buttonView.getId()==R.id.radio_Female)
                if(isChecked)
                    gender = false;
            //여아가 체크 됐을때

        }
    };

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            //when View is clicked in onClickListener
            int id = v.getId();
            int result;
            //현재 날짜
            calendar = Calendar.getInstance();
            c_year = calendar.get(Calendar.YEAR);
            c_month =  calendar.get(Calendar.MONTH) +1;
            c_day = calendar.get(Calendar.DAY_OF_MONTH);
            //get id from clicked one (as integer)
            if(id == R.id.btnRegister_login){

                result = ((c_year - s_year) * 12) + (c_month-s_month);
                String name = ((EditText) wholeView.findViewById(R.id.editRegister_name)).getText().toString();
                //((MainActivity) getActivity()).setName(name);
                //((MainActivity) getActivity()).setMonth((result));
                //((MainActivity) getActivity()).setGender(gender);
                if(name.equals(""))
                    Toast.makeText(getActivity(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                else if(result == 24194 || result <= 0)
                    Toast.makeText(getActivity(), "생년월일을 정확하게 입력하세요.", Toast.LENGTH_LONG).show();
                else {
                    ((MainActivity) getActivity()).setName(name);
                    ((MainActivity) getActivity()).setMonth((result));
                    ((MainActivity) getActivity()).setAge((result));
                    ((MainActivity) getActivity()).setGender(gender);
                    ((MainActivity) getActivity()).moveFragment(Constants.MENU);
                }

            }
            //Second page
        }

    };
}

