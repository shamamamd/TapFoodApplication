package com.tapfood.application.tapFood.splash;

public class SplashModel implements SplashMVP.Model {

    private SplashRepository repository;

    public SplashModel(SplashRepository repository) {
        this.repository = repository;
    }
}
