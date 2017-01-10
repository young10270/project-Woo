package com.jo.woo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jo.woo.Constants;
import com.jo.woo.MainActivity;
import com.jo.woo.R;

/**
 * Created by jo on 2016-01-28.
 */
public class ValueFragment extends Fragment {
    private View wholeView = null;
    private double mHeight, mWeight, mBMI;
    private int mMonth;
    private String mGender= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle data){
        wholeView = inflater.inflate(R.layout.fragment_value, null);
        mHeight= ((MainActivity)getActivity()).getMeasuredHeight();
        mWeight= ((MainActivity)getActivity()).getMeasuredWeight();
        mMonth= ((MainActivity)getActivity()).getMonth();
        mBMI= mWeight/(mHeight*mHeight);
        mGender= ((MainActivity)getActivity()).getGender();
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
        ((TextView)wholeView.findViewById(R.id.textFragmentValueHeight)).setText(mHeight*100+"cm");
        ((TextView)wholeView.findViewById(R.id.textFragmentValueWeight)).setText(mWeight+"kg");
        ((TextView)wholeView.findViewById(R.id.textFragmentValueBMI)).setText(String.format("%.2f", mBMI));
        ((TextView)wholeView.findViewById(R.id.textFragmentValueBasis)).setText(calculateValue(mBMI, mMonth));
        ((Button)wholeView.findViewById(R.id.btnValue_back)).setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            if(v.getId() == R.id.btnValue_back)
                //Back button clicked
                ((MainActivity)getActivity()).moveFragment(Constants.MENU);
        }
    };

    // for calculating
    public String calculateValue(double bmi, int month){
        String value = "";

        if(mGender.equals("남아")) {
            if(month<24)
                value = "BMI 지수의 참고치 정보는 2세 이상부터 제공";
            else if(month>=24 && month<36){
                if(bmi<14.33)
                    value="저체중";
                else if(bmi>=19.15)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=36 && month<48){
                if(bmi<14.15)
                    value="저체중";
                else if(bmi>=18.40)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=48 && month<60){
                if(bmi<14.01)
                    value="저체중";
                else if(bmi>=18.34)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=60 && month<72){
                if(bmi<13.87)
                    value="저체중";
                else if(bmi>=19.15)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=72 && month<84){
                if(bmi<13.93)
                    value="저체중";
                else if(bmi>=20.93)
                    value="비만";
                else if(bmi<20.93 || bmi>=18.86)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=84 && month<96){
                if(bmi<14.06)
                    value="저체중";
                else if(bmi>=22.13)
                    value="비만";
                else if(bmi<22.13 || bmi>=19.80)
                    value="과체중";
                else
                    value="정상";
            }

            else if(month>=96 && month<108){
                if(bmi<14.27)
                    value="저체중";
                else if(bmi>=23.34)
                    value="비만";
                else if(bmi<23.34 || bmi>=20.76)
                    value="과체중";
                else
                    value="정상";
            }

            else if(month>=108 && month<=120){
                if(bmi<14.57)
                    value="저체중";
                else if(bmi>=24.48)
                    value="비만";
                else if(bmi<24.48 || bmi>=21.71)
                    value="과체중";
                else
                    value="정상";
            }
        }

        else {
            if(month<24)
                value = "BMI 지수의 참고치 정보는 2세 이상부터 제공";
            else if(month>=24 && month<36){
                if(bmi<14.12)
                    value="저체중";
                else if(bmi>=18.94)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=36 && month<48){
                if(bmi<13.92)
                    value="저체중";
                else if(bmi>=18.19)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=48 && month<60){
                if(bmi<13.76)
                    value="저체중";
                else if(bmi>=18.03)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=60 && month<72){
                if(bmi<13.64)
                    value="저체중";
                else if(bmi>=18.33)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=72 && month<84){
                if(bmi<13.59)
                    value="저체중";
                else if(bmi>=18.96)
                    value="비만";
                else if(bmi<18.96 || bmi>=17.48)
                    value="과체중";
                else
                    value="정상";
            }
            else if(month>=84 && month<96){
                if(bmi<13.63)
                    value="저체중";
                else if(bmi>=20.05)
                    value="비만";
                else if(bmi<20.05 || bmi>=18.27)
                    value="과체중";
                else
                    value="정상";
            }

            else if(month>=96 && month<108){
                if(bmi<13.77)
                    value="저체중";
                else if(bmi>=21.05)
                    value="비만";
                else if(bmi<21.05|| bmi>=19.05)
                    value="과체중";
                else
                    value="정상";
            }

            else if(month>=108 && month<=120){
                if(bmi<14.01)
                    value="저체중";
                else if(bmi>=22.09)
                    value="비만";
                else if(bmi<22.09 || bmi>=19.88)
                    value="과체중";
                else
                    value="정상";
            }
        }

        return value;
    }
}
