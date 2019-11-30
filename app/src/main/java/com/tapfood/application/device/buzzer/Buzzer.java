package com.tapfood.application.device.buzzer;

import android.content.Context;
import android.os.RemoteException;

import com.tapfood.application.device.BaseDeviceActivity;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.buzzer.AidlBuzzer;

public class Buzzer extends BaseDeviceActivity implements BuzzerType{

    private AidlBuzzer iBeeper;

    public Context context;
    public Buzzer(Context context){
        this.context=context;
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            iBeeper = AidlBuzzer.Stub.asInterface(serviceManager.getBuzzer());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startBuzzer() {
        try {
            iBeeper.beep(0,1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stopBuzzer() {
        try {
            iBeeper.stopBeep();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
