package com.tapfood.application.tapFood;


import com.tapfood.application.tapFood.splash.SplashActivity;
import com.tapfood.application.tapFood.splash.SplashModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, SplashModule.class})
public interface ApplicationComponent {

    void inject(SplashActivity target );

}
