package com.tapfood.application.tapFood.splash;

import dagger.Module;
import dagger.Provides;


@Module
public class SplashModule {


    @Provides
    public SplashMVP.Presenter provideSplashPresenter(SplashMVP.Model model ){
        return new SplashPresenter(model);
    }
    @Provides
    public SplashMVP.Model provideSplashModel(SplashRepository repository){
        return new SplashModel(repository);

    }

    @Provides
    public SplashRepository provideSplashRepository(){
        return new MemoryRepository();
    }
}
