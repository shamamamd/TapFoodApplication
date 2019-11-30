package com.tapfood.application.tapFood.splash;


import android.app.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;


import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;

import android.view.View;

import android.view.WindowManager;
import android.widget.RelativeLayout;


import com.tapfood.application.TapFoodApplication;
import com.tapfood.application.device.card.SwipeCard;
import com.tapfood.application.kioskMode.PrefUtils;
import com.tapfood.application.kioskMode.PreventStatusBar;
import com.tapfood.application.tapFood.restaurantList.RestaurantListActivity;
import com.topwise.tapfood.R;

import javax.inject.Inject;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends Activity implements SplashMVP.View {

    @Inject
    SplashMVP.Presenter presenter;

    private final static int REQUEST_CODE = -1010101;
    private RelativeLayout splash;
    private GifImageView gifImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dismissKeyGuard();

        ((TapFoodApplication) getApplication()).getComponent().inject(this);

        splash = findViewById(R.id.splash);
        gifImageView = findViewById(R.id.prog);

        splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.splashClick();
            }
        });

        checkDrawOverlayPermission();
        statusBar();

    }


    @Override
    public void showProgress() {

        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void nextActivity() {

        startActivity(new Intent(getApplicationContext(), SwipeCard.class));
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }


    //Full screen and kiosk mode

    public void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
            }
        }
    }

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
                        // content doesn't resize when the system bars dismissKeyGuard and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void dismissKeyGuard() {


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    private void statusBar() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PreventStatusBar.preventStatusBarExpansion(getApplicationContext());
            }
        }).start();


    }

}


