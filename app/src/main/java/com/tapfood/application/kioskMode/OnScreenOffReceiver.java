package com.tapfood.application.kioskMode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.tapfood.application.TapFoodApplication;

public class OnScreenOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
            TapFoodApplication tpf = (TapFoodApplication) context.getApplicationContext();
            // is Kiosk Mode active?
            if(PrefUtils.isKioskModeActive(tpf)) {
                wakeUpDevice(tpf);
            }
        }
    }

    private void wakeUpDevice(TapFoodApplication context) {
        PowerManager.WakeLock wakeLock = context.getWakeLock(); // get WakeLock reference
        if (wakeLock.isHeld()) {
            wakeLock.release(); // release old wake lock
        }

        wakeLock.acquire();

        wakeLock.release();
    }


}