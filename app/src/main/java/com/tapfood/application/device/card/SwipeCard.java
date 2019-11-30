package com.tapfood.application.device.card;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import com.tapfood.application.device.BaseDeviceActivity;
import com.tapfood.application.device.led.LED;
import com.tapfood.application.device.PinPad.PinPad;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.emv.AidlCheckCardListener;
import com.topwise.cloudpos.aidl.emv.AidlPboc;
import com.topwise.cloudpos.aidl.magcard.TrackData;
import com.topwise.tapfood.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SwipeCard extends BaseDeviceActivity implements CardType{

    public static final String TAG="Message SwipeCard";
    public AidlPboc pboc = null;
    public AidlDeviceService serviceManager = null;

    public  static  Typeface typeface;
    public LED led;
    public PinPad pinPad;
    public TextView txtSwipe;

    public static String cardNumber,secondTrackData;

    public void initialize(){
        led=new LED(this);
        pinPad=new PinPad(this);
        txtSwipe=findViewById(R.id.txtSwipe);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/iransans_mobile_bold.ttf");
        txtSwipe.setTypeface(typeface);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_card_swipe);
        super.onCreate(savedInstanceState);
        initialize();
        callSwipeCard();


    }
    public void callSwipeCard(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeCard();
            }
        }, 1000);

    }
    @Override
    public void swipeCard(){

        if (null != pboc) {

            Log.i(TAG,"insert_or_swipe_card");
            led.blueOn();
            try {
                pboc.checkCard(true, true, true, 60000, new AidlCheckCardListener.Stub() {
                    @Override
                    public void onTimeout() throws RemoteException {
                        Log.i(TAG,"search_card_timeout");

                        led.redOn();
                        led.blueOff();
                        led.redOff();
                        swipeCard();
                    }

                    @Override
                    public void onSwipeCardFail() throws RemoteException {
                        Log.i(TAG,"swipe_failed");
                        pboc.cancelCheckCard();

                        led.redOn();
                        led.blueOff();
                        led.redOff();
                        swipeCard();
                    }

                    @Override
                    public void onFindRFCard() throws RemoteException {

                    }

                    @Override
                    public void onFindMagCard(TrackData trackData) throws RemoteException {
                        led.greenOn();
                        led.blueOff();

                        cardNumber=trackData.getCardno();
                        secondTrackData=trackData.getSecondTrackData();
                        showPinPad();
                        led.greenOff();

                    }

                    @Override
                    public void onFindICCard() throws RemoteException {

                    }

                    @Override
                    public void onError(int arg0) throws RemoteException {
                        Log.i(TAG,"search_card_failed" + arg0);
                        led.redOn();
                        led.redOff();
                        swipeCard();


                    }

                    @Override
                    public void onCanceled() throws RemoteException {
                        Log.i(TAG,"cancel_search_card");
                        led.redOn();
                        swipeCard();

                    }
                });
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
    public void showPinPad() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                SwipeCard.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            pinPad.getMac();
                               pinPad.getPin();

                        }
                        catch (Exception e){

                        }
                    }
                });
            }
        }.start();


    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        this.serviceManager = serviceManager;

        //Device Connected Check SwipeCard
        try {

            this.pboc = AidlPboc.Stub.asInterface(serviceManager.getEMVL2());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        pinPad.onDeviceConnected(serviceManager);
        led.onDeviceConnected(serviceManager);

    }
    //kiosk mode
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }

    }
    public static final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP));

    @Override
    public void onBackPressed() {

    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return true;
    }
}



