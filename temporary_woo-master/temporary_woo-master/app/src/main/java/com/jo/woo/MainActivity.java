package com.jo.woo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.jo.woo.fragments.BluetoothFragment;
import com.jo.woo.fragments.DevelopFragment;
import com.jo.woo.fragments.ExpectFragment;
import com.jo.woo.fragments.GraphFragment;
import com.jo.woo.fragments.GrowthinfoFragment;
import com.jo.woo.fragments.MainFragment;
import com.jo.woo.fragments.MeasureFragment;
import com.jo.woo.fragments.MenuFragment;
import com.jo.woo.fragments.RegisterFragment;
import com.jo.woo.fragments.ValueFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

public class MainActivity extends FragmentActivity {
    private String mName = null;
    private Integer mMonth = null;
    private String mAge = null;
    private String mGender = null;

    private SharedPreferences pref = null;

    private FragmentTransaction mFrgTransaction = null;

    private MainFragment mMain = null;
    private RegisterFragment mRegister = null;
    private MenuFragment mMenu = null;
    private MeasureFragment mMeasure = null;
    private ValueFragment mValue = null;
    private GrowthinfoFragment mGrowthinfo = null;
    private DevelopFragment mDevelop = null;
    private ExpectFragment mExpect = null;
    private GraphFragment mGraph = null;

    private BluetoothFragment mBluetooth= null;

    // for bluetooth
    private BluetoothAdapter mBluetoothAdapter= null;
    private BluetoothDevice selectedDevice= null;
    private BluetoothSocket mSocket= null;
    private BufferedReader mReader= null;

    private Thread bluetoothThread= null;
    private boolean threadStop= false;
    private InputStream mmInputStream;
    private byte[] readBuffer;
    private int readBufferPosition;

    private int testCount= 0;

    final String mBluetoothName= "20:15:07:07:04:77";
    private StringBuilder recDataString = new StringBuilder();
    private int mMeasureCount= 0;

    private double mMeasuredHeight= -100.0f, mMeasuredWeight= -100.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if(!mBluetoothAdapter.isEnabled()){
            Intent enableBluetooth= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 1);
        }
        setBluetoothData();

        bluetoothIn= new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {                                     //if message is what we want
                    Log.d("BLUETOOTH HANDLER", "obtain message :::: 0");
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);

                        if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        Log.d("GENDER", "gender :: "+mGender);
    }

    public ParcelUuid[] servicesFromDevice(BluetoothDevice device) {
        try {
            Class cl = Class.forName("android.bluetooth.BluetoothDevice");
            Class[] par = {};
            Method method = cl.getMethod("getUuids", par);
            Object[] args = {};
            ParcelUuid[] retval = (ParcelUuid[]) method.invoke(device, args);
            return retval;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        loadSharedPreference();
        makeView();
        moveFragment(Constants.MAIN);
    }
    @Override
    public void onPause(){
        super.onPause();
        saveSharedPreference();
    }

    private void setBluetoothData() {
        // Getting the Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null)  Toast.makeText(this, "Bluetooth NOT supported. Aborting.", Toast.LENGTH_LONG).show();
        mBluetoothAdapter.startDiscovery();

        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            Log.e("BLUETOOTH", "\nFound device: " + device.getName() + " Add: "
                    + device.getAddress());
            if(device.getAddress().equals(mBluetoothName)) {
                Log.e("BLUETOOTH", "\nSelected device: " + device.getName() + " Add: " + device.getAddress());
                selectedDevice = device;
                break;
            }
        }
    }

    private void makeView()
    {
        mRegister = new RegisterFragment();
        mMeasure = new MeasureFragment();
        mGraph = new GraphFragment();
        mValue = new ValueFragment();
        mBluetooth = new BluetoothFragment();
    }

    public void moveFragment(int type){
        //int �� type ������ �޾ƿͼ�
        mFrgTransaction = getSupportFragmentManager().beginTransaction();
        //transaction �����ͼ� mFrgTransaction�� �־���
        switch (type){
            case Constants.MAIN:
                if(mMain == null)
                    mMain = new MainFragment(mName, mMonth, mAge, mGender);
                mFrgTransaction.replace(R.id.container, mMain);
                break;
            case Constants.REGISTER:
                mFrgTransaction.replace(R.id.container, mRegister);
                //container��� ������ mainFragment�� ����ž� ��� FrgTransaction���� �˷���
                break;
            case Constants.MENU:
                if(mMenu == null)
                    mMenu = new MenuFragment(mName, mMonth, mAge, mGender);
                mFrgTransaction.replace(R.id.container, mMenu);
                break;
            case Constants.MEASURE:
                mFrgTransaction.replace(R.id.container, mMeasure);
                break;
            case Constants.VALUE:
                mFrgTransaction.replace(R.id.container, mValue);
                break;
            case Constants.GROWTHINFO:
                if(mGrowthinfo == null)
                    mGrowthinfo = new GrowthinfoFragment(mName, mMonth, mAge, mGender);
                mFrgTransaction.replace(R.id.container, mGrowthinfo);
                break;
            case Constants.DEVELOP:
                if(mDevelop == null)
                    mDevelop = new DevelopFragment(mName, mMonth, mAge, mGender);
                mFrgTransaction.replace(R.id.container, mDevelop);
                break;
            case Constants.EXPECT:
                if(mExpect == null)
                    mExpect = new ExpectFragment(mGender);
                mFrgTransaction.replace(R.id.container, mExpect);
                break;
            case Constants.GRAPH:
                mFrgTransaction.replace(R.id.container, mGraph);
                break;
            case Constants.BLUETOOTH:
                mFrgTransaction.replace(R.id.container, mBluetooth);
                break;
            default:
        }
        mFrgTransaction.commit();
        //commit�� ������ ������ ���Ǽ� ����ڿ��� ��Ÿ���� ��
    }

    public void setName(String Name) { mName = Name; }
    public void setMonth(Integer Month) { mMonth = Month; }
    public void setAge(Integer month) {
        int age;
        if(month < 12)
            mAge = "생후" + month +"개월";
        else{
            age = month/12;
            mAge = "만 " + age + "세";
        }
    }
    public void setGender(Boolean Gender) {
        if(Gender)
            mGender = "남아";
        else
            mGender = "여아";
    }

    public String getName(){ return mName; }
    public Integer getMonth() { return mMonth; }
    public String getAge() {return mAge;}
    public String getGender() { return mGender; }

    private void saveSharedPreference(){
        if(pref == null) pref= getSharedPreferences("myapplication", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit= pref.edit();
        if(mName != null) edit.putString("name", mName);
        if(mMonth != null) edit.putInt("month", mMonth);
        if(mAge != null) edit.putString("age", mAge);
        if(mGender != null) edit.putString("gender", mGender);
        edit.commit();
    }
    private void saveMeasuredData(){
        if(pref == null) pref= getSharedPreferences("myapplication", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit= pref.edit();
        edit.putFloat("height", (float)mMeasuredHeight);
        edit.putFloat("weight", (float)mMeasuredWeight);
        edit.commit();
    }
    // load data on local
    private void loadSharedPreference(){
        pref= getSharedPreferences("myapplication", Context.MODE_PRIVATE);
        mName= pref.getString("name", null);
        mMonth = pref.getInt("month", 0);
        mAge = pref.getString("age", null);
        mGender = pref.getString("gender", null);
    }

    public void connectBluetooth(){
        pairDevice(selectedDevice);
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Log.d("pairDevice()", "Start Pairing...");
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.d("pairDevice()", "Pairing finished.");
            Toast.makeText(this, "pairing finished", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("pairDevice()", e.getMessage());
        }
    }

    public void getDataFromBluetooth(){
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        try{
            mSocket= selectedDevice.createInsecureRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            Log.e("BLUETOOTH", "socket open success");
            mmInputStream= mSocket.getInputStream();
            mReader= new BufferedReader(new InputStreamReader(mmInputStream));
            Log.e("BLUETOOTH", "get inputstream success");
        }catch(IOException e){
            Log.e("BLUETOOTH", "socket open error");
        }
        if(bluetoothThread == null){
            bluetoothThread= new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("BLUETOOTH THREAD", "thread start && before starting while");
                    while(!Thread.currentThread().isInterrupted() && !threadStop) {
                        try {
                            byte[] packetBytes = new byte[1024];
                            if(readBuffer == null) readBuffer= new byte[1024];
                            mmInputStream.read(packetBytes);
                            System.arraycopy(packetBytes, 0, readBuffer, 0, readBuffer.length);
                            final String data = new String(readBuffer, "US-ASCII");
                            Log.d("BLUETOOTH", "RECEIVED DATA :: " + data);
                            StringTokenizer token= new StringTokenizer(data, ";");
                            String temp= token.nextToken();
                            mMeasuredHeight= Double.parseDouble(temp)/100.0;
                            temp= token.nextToken();
                            mMeasuredWeight= Math.abs(Double.parseDouble(temp));
                            moveFragment(Constants.VALUE);
                            saveMeasuredData();
                            mSocket.close();
                        }catch (IOException e)  { threadStop = true; }
                        catch (Exception e){}
                    }
                }
            });
        }
        bluetoothThread.start();
    }
    public double getMeasuredWeight(){ return mMeasuredWeight; }
    public double getMeasuredHeight(){ return mMeasuredHeight; }

    private ConnectedThread mConnectedThread;
    private Handler bluetoothIn= null;

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(0, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

}

