package com.tapfood.application.device.led;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;

import com.tapfood.application.device.BaseDeviceActivity;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.data.LedCode;

public class LED extends BaseDeviceActivity implements ColorLED {

    public static final String TAG="Message LED";
    public AidlLed iLed;
    private AidlDeviceService serviceManager = null;
    Context context;

    public LED(Context context){
        this.context=context;
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        this.serviceManager = serviceManager;
        if (serviceManager!=null){

            try {
                Bundle bundle = new Bundle();
                bundle.putInt("LED_ID",1);
                iLed = AidlLed.Stub.asInterface(serviceManager.getLed());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void redOn() {
        try {
            iLed.setLed(LedCode.OPER_LED_RED,true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void redOff() {
        try {
            iLed.setLed(LedCode.OPER_LED_RED,false);
           // Log.i(TAG,context.getResources().getString(R.string.led_red_off));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void yellowOn() {
        try {
            iLed.setLed(LedCode.OPER_LED_YELLOW,true);
    //  Log.i(TAG,context.getResources().getString(R.string.led_yellow_on));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void yellowOff() {
        try {
            iLed.setLed(LedCode.OPER_LED_YELLOW,false);
        //    Log.i(TAG,context.getResources().getString(R.string.led_yellow_off));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void greenOn() {
        try {
            iLed.setLed(LedCode.OPER_LED_GREEN,true);
//            Log.i(TAG,context.getResources().getString(R.string.led_green_on));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void greenOff() {
        try {
            iLed.setLed(LedCode.OPER_LED_GREEN,false);
       //    Log.i(TAG,context.getResources().getString(R.string.led_green_off));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void blueOn() {
        try {
            iLed.setLed(LedCode.OPER_LED_BLUE,true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void blueOff() {
        try {
            iLed.setLed(LedCode.OPER_LED_BLUE,false);
           // Log.i(TAG,context.getResources().getString(R.string.led_blue_off));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void allOn() {
        try {
            iLed.setLed(LedCode.OPER_LED_ALL,true);
           // Log.i(TAG,context.getResources().getString(R.string.led_all_on));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void allOff() {
        try {
            iLed.setLed(LedCode.OPER_LED_ALL,false);
            //Log.i(TAG,context.getResources().getString(R.string.led_all_off));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
