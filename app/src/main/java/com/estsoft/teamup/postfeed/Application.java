package com.estsoft.teamup.postfeed;

import android.content.Context;

/**
 * Created by click on 2016-04-12.
 */
public class Application extends android.app.Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
    }

    public static Application getInstance(){
        return instance;
    }

    public static Context getContext(){
        return getInstance().getApplicationContext();
    }
}
