package com.jo.woo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.jo.woo.R;
import com.jo.woo.listitem.BluetoothItem;

import java.util.ArrayList;

/**
 * Created by godjaku on 2016. 4. 7..
 */
public class BluetoothAdapter extends ArrayAdapter<BluetoothItem> {
    public Context mContext= null;
    public ArrayList<BluetoothItem> mContent= null;

    public BluetoothAdapter(Context context, int resId, ArrayList<BluetoothItem> content){
        super(context, resId, content);
        mContext= context;
        mContent= content;
    }

    @Override
    public View getView(int position, View v, ViewGroup container){
        if(v == null) v= LayoutInflater.from(mContext).inflate(R.layout.item_bluetooth, null);

        return v;
    }
    @Override
    public int getCount(){ return mContent.size(); }
    @Override
    public BluetoothItem getItem(int position){ return mContent.get(position); }
}
