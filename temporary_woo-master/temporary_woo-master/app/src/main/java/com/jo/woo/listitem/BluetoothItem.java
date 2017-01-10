package com.jo.woo.listitem;

import android.os.ParcelUuid;

/**
 * Created by godjaku on 2016. 4. 7..
 */
public class BluetoothItem {
    private String mMACAddr= null;
    private ParcelUuid[] mUUID= null;
    private String mDeviceName= null;
    private boolean isConnected= false;

    public BluetoothItem(String mac, ParcelUuid[] uuid, String name){
        mMACAddr= mac;
        mUUID= uuid;
        mDeviceName= name;
    }

    public String getMACAddr(){ return mMACAddr; }
    public ParcelUuid[] getUUID(){ return mUUID; }
    public String getName() { return mDeviceName; }
}
