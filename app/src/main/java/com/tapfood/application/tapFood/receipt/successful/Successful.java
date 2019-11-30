package com.tapfood.application.tapFood.receipt.successful;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.tapfood.application.device.BaseDeviceActivity;
import com.tapfood.application.device.printer.PrinterDevice;
import com.tapfood.application.kioskMode.PrefUtils;
import com.tapfood.application.tapFood.addFood.AddFoodActivity;
import com.tapfood.application.tapFood.splash.SplashActivity;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.tapfood.R;

public class Successful extends BaseDeviceActivity {


    private AidlDeviceService serviceManager = null;
    public PrinterDevice printerDevice;

    public TextView receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        printerDevice = new PrinterDevice(this);
        receipt = findViewById(R.id.receipt);
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Successful.this, SplashActivity.class);
                startActivity(intent);
            }
        });
        print();


    }

    public void print() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                printerDevice.print();
                AddFoodActivity.countingFoodBottom=0;
                AddFoodActivity.totalAllPriceAllFoods=0;
            }
        }, 2000);

    }

    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {


        this.serviceManager = serviceManager;

        if (null != serviceManager) {
            printerDevice.onDeviceConnected(serviceManager);
        }


    }


    //full and kiosk mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
        if (!hasFocus) {

            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (PrefUtils.blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Disable Button (backPage home active app button)

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

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
}
