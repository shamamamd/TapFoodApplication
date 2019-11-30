package com.tapfood.application;

import android.annotation.SuppressLint;
import android.app.Application;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

import com.tapfood.application.kioskMode.KioskService;
import com.tapfood.application.kioskMode.OnScreenOffReceiver;
import com.tapfood.application.tapFood.ApplicationComponent;
import com.tapfood.application.tapFood.ApplicationModule;
import com.tapfood.application.tapFood.DaggerApplicationComponent;
import com.tapfood.application.tapFood.splash.SplashModule;

import dagger.internal.DaggerCollections;


public class TapFoodApplication extends Application {

    private ApplicationComponent  component;

    private TapFoodApplication instance;
    private PowerManager.WakeLock wakeLock;
    private OnScreenOffReceiver onScreenOffReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerKioskModeScreenOffReceiver();
        startKioskService();

        component= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .splashModule(new SplashModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }


    private void registerKioskModeScreenOffReceiver() {
        // register screen off receiver
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        onScreenOffReceiver = new OnScreenOffReceiver();
        registerReceiver(onScreenOffReceiver, filter);
    }
    @SuppressLint("InvalidWakeLockTag")
    public PowerManager.WakeLock getWakeLock() {
        if (wakeLock == null) {
            // lazy loading: first call, create wakeLock via PowerManager.
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "wakeup");
        }
        return wakeLock;
    }
    private void startKioskService() { // ... and this method
        startService(new Intent(this, KioskService.class));
    }

}
