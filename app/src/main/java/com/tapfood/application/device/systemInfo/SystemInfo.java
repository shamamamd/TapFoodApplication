package com.tapfood.application.device.systemInfo;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.tapfood.application.device.BaseDeviceActivity;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.system.AidlSystem;

public class SystemInfo extends BaseDeviceActivity implements SystemType {

    private static final String TAG = "Message SystemInfo";
    private AidlSystem systemInf = null;

    Context context;
    String terminalSn = null;

    public SystemInfo(Context context){
        this.context=context;
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            systemInf = AidlSystem.Stub.asInterface(serviceManager
                    .getSystemService());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getSerialNumberTerminal() {

        try
        {
             terminalSn= systemInf.getSerialNo();

            } catch (RemoteException e) {
            e.printStackTrace();
            Log.i(TAG,"system_read_sn_error");
        }
        return terminalSn;
    }


}
