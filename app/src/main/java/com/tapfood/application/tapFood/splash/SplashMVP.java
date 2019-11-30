package com.tapfood.application.tapFood.splash;

public interface SplashMVP {


    interface View {

        void showProgress();
        void nextActivity();

    }

    interface Presenter {

        void setView(SplashMVP.View view);
        void splashClick();
    }
    interface Model {

    }


}
