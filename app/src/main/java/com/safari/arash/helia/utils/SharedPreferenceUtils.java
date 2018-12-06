package com.safari.arash.helia.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class SharedPreferenceUtils {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferenceUtils ourInstance;

    public static SharedPreferenceUtils getInstance(Context context) {
        if(ourInstance==null){
            ourInstance = new SharedPreferenceUtils(context);
        }
        return ourInstance;
    }

    private SharedPreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("senjadPrefrences",Context.MODE_PRIVATE);
    }
    public String getToken(){
        return mSharedPreferences.getString("Token","");
    }
    public void setToken(String token){
        mSharedPreferences.edit().putString("Token",token).apply();
    }
    public int getModulesSize(){
        return mSharedPreferences.getInt("status_size",0);
    }
    public void putModulesSize(int n){
        mSharedPreferences.edit().putInt("status_size",n).apply();
    }
    public boolean getModulesStatus(int i){
        return mSharedPreferences.getBoolean("status"+i,false);
    }
    public void putModulesStatus(int i,boolean status){
        mSharedPreferences.edit().putBoolean("status"+i,status).apply();
    }
    public int getPrecentageModule(int i){
        return mSharedPreferences.getInt("precQuiz"+i,0);
    }
    public void putPrecentageModule(int i,int prec){
        mSharedPreferences.edit().putInt("precQuiz"+i,prec).apply();
    }

}
