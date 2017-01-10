package com.jo.woo.fragments;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jo.woo.R;
import com.jo.woo.listitem.BluetoothItem;

/**
 * Created by godjaku on 2016. 4. 4..
 */
public class BluetoothFragment extends Fragment {
    private View wholeView= null;
    private ListView mList= null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        wholeView= inflater.inflate(R.layout.fragment_bluetooth, null);
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

    }

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("DEVICELIST", "Bluetooth device found\n");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Create a new device item
//                DeviceItem newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                BluetoothItem newItem= new BluetoothItem(device.getAddress(), device.getUuids(), device.getName());

                // Add it to our adapter
//                mAdapter.add(newDevice);
//                mAdapter.notifyDataSetChanged();
            }
        }
    };
}