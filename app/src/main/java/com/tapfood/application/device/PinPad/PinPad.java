package com.tapfood.application.device.PinPad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;


import com.tapfood.application.constant.HexUtil;
import com.tapfood.application.device.BaseDeviceActivity;
import com.tapfood.application.device.buzzer.Buzzer;
import com.tapfood.application.device.card.SwipeCard;
import com.tapfood.application.constant.Data;
import com.tapfood.application.device.printer.PrinterDevice;
import com.tapfood.application.device.systemInfo.SystemInfo;
import com.tapfood.application.iso85383.Constant;
import com.tapfood.application.iso85383.ClientISO;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.pinpad.GetPinListener;
import com.topwise.cloudpos.data.PinpadConstant;
import com.topwise.tapfood.R;

import org.jpos.iso.ISOUtil;

public class PinPad extends BaseDeviceActivity implements PinPadType {

    public static final String TAG="Message PinPad";

    private AidlDeviceService serviceManager = null;
    private AidlPinpad pinpad = null;
    private int pinPadType = PinpadConstant.PinpadId.BUILTIN;
    private int pinPadMode = 1;
    private boolean supportExtPinPad = false;

    int mWorkKey = 0x01;
    int mMainKey = 0x01;

    Typeface typeface;

    Data data;
    public Context context;
    public SystemInfo systemInfo;
    public PrinterDevice printerDevice;
    public Buzzer buzzer;
    public PinPad(Context context){
        this.context=context;
        data=new Data();
        systemInfo=new SystemInfo(this.context);
        printerDevice =new PrinterDevice(this.context);
        buzzer=new Buzzer(this.context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/iransans_mobile_bold.ttf");
    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        this.serviceManager = serviceManager;

        if (null != serviceManager) {
            try {
                if (!supportExtPinPad && pinPadType == PinpadConstant.PinpadId.EXTERNAL) {
                    pinPadType = PinpadConstant.PinpadId.BUILTIN;
                }
                systemInfo.onDeviceConnected(serviceManager);
                printerDevice.onDeviceConnected(serviceManager);
                buzzer.onDeviceConnected(serviceManager);

                pinpad = AidlPinpad.Stub.asInterface(serviceManager
                        .getPinPad(pinPadType));
                pinpad.setPinKeyboardMode(pinPadMode);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public void getMac(){


        try {
            String key1 = "3131313131313131";
            boolean flag1 = pinpad.loadMainkey(mMainKey, HexUtil.hexStringToByte(key1), null);

            String key = "29b0632622d37e5a";
            boolean flag = pinpad.loadWorkKey(PinpadConstant.WKeyType.WKEY_TYPE_MAK, mMainKey, mWorkKey, HexUtil.hexStringToByte(key),null);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int retCode = -1;
        Bundle bundle = new Bundle();
        bundle.putInt("wkeyid", mWorkKey);
        bundle.putByteArray("data", HexUtil.hexStringToByte("826b0a00008100180b1af7cc001702ff061180000607190017004593b59c960832333d302e37303b27d3d112"));
        bundle.putByteArray("random", null);
        bundle.putInt("type", 0x01);
        byte[] mac = new byte[8];
        try {
            retCode = pinpad.getMac(bundle, mac);
            if (retCode != 0x00) {
                Log.i("Error   : ",retCode+"");
            } else {
                Log.i("Mac :", HexUtil.bcd2str(mac));
                Log.i("MAC: ",Constant.convertDecimalToHexDecimal(HexUtil.bcd2str(mac)));
                Log.i("MAC",new String(mac));

            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }





    @Override
    public void getPin() {

        if (!supportExtPinPad && pinPadType == PinpadConstant.PinpadId.EXTERNAL) {
            return ;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("wkeyid", mWorkKey);
        bundle.putInt("keytype", 0x00);
        bundle.putString("pan", "0000000000000000");
        bundle.putByteArray("random",null);
        bundle.putInt("inputtimes", 1);
        bundle.putInt("minlength", 4);
        bundle.putInt("maxlength", 4);
        bundle.putBoolean("pin_refresh", false);
        bundle.putBoolean("is_lkl", false);
        bundle.putInt("timeout", 60);
        KeyBoard keyBoard = new KeyBoard(context,pinpad, bundle,new PinPad.MyGetPinListener());
        keyBoard.show();

    }

    private class MyGetPinListener extends GetPinListener.Stub {
        @Override
        public void onStopGetPin() throws RemoteException {
            pinHandler.obtainMessage(0x00, context.getResources().getString(R.string.pin_cancel)).sendToTarget();
        }

        @Override
        public void onInputKey(int arg0, String arg1) throws RemoteException {
            pinHandler.obtainMessage(0x00, com.tapfood.application.constant.Constant.getStar(arg0)).sendToTarget();
            /*buzzer.startBuzzer();
            buzzer.stopBuzzer();
*/
        }

        @Override
        public void onError(int arg0) throws RemoteException {
            pinHandler.obtainMessage(0x00, context.getResources().getString(R.string.pin_input_error) + arg0).sendToTarget();
        }

        @Override
        public void onConfirmInput(byte[] arg0) throws RemoteException {
            pinHandler.obtainMessage(0x00, context.getResources().getString(R.string.pin_input_success) + (null == arg0 ?
                    context.getResources().getString(R.string.pin_input_null) : context.getResources().getString(R.string.pin_input_result)
                    + HexUtil.bcd2str(arg0))).sendToTarget();
                    data.setPin(HexUtil.bcd2str(arg0));
//                    buzzer.startBuzzer();
//                    buzzer.stopBuzzer();
                               goSwitch();

        }

        @Override
        public void onCancelKeyPress() throws RemoteException {
            pinHandler.obtainMessage(0x00, context.getResources().getString(R.string.pin_cancel)).sendToTarget();
            finish();

        }
    }


    @SuppressLint("HandlerLeak")
    private Handler pinHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                Log.i(TAG,msg.obj.toString());
            }
        }
    };

    public void goSwitch(){
        Intent intent=new Intent(context, ClientISO.class);
        intent.putExtra("cardData",new Data(SwipeCard.cardNumber, SwipeCard.secondTrackData,data.getPin(), Constant.defCalculatorKeyMAC()
                ,systemInfo.getSerialNumberTerminal() ));
        context.startActivity(intent);

        //printerDevice.print();

    }


}


