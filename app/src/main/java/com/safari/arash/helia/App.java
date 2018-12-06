package com.safari.arash.helia;

import android.app.Application;
import android.content.Context;

import com.safari.arash.helia.database.MyObjectBox;
import com.safari.arash.helia.utils.SharedPreferenceUtils;

import io.objectbox.BoxStore;

public class App extends Application {
    int moduleSize=10;
    private BoxStore boxStore;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceUtils sharedPreferenceUtils =
                SharedPreferenceUtils.getInstance(this);
        sharedPreferenceUtils.putModulesSize(moduleSize);
        sharedPreferenceUtils.putModulesStatus(0,true);
        for (int i = 1; i < moduleSize; i++) {
            sharedPreferenceUtils.putModulesStatus(i,false);
        }
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }
    public BoxStore getBoxStore() {
        return boxStore;
    }
}
