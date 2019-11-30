package com.tapfood.application.tapFood.splash;

import android.os.Handler;
import android.support.annotation.Nullable;

public class SplashPresenter implements SplashMVP.Presenter {

    @Nullable
    private SplashMVP.View view;
    private SplashMVP.Model model;

    public SplashPresenter(SplashMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(SplashMVP.View view) {

        this.view = view;


    }

    @Override
    public void splashClick() {

        if (view != null) {
            view.showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    view.nextActivity();

                }
            }, 2000);


        }

    }
}
