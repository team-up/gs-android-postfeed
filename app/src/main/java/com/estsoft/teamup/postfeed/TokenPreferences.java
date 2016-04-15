package com.estsoft.teamup.postfeed;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by click on 2016-04-15.
 */
public class TokenPreferences {
    private static TokenPreferences instance = new TokenPreferences();
    private static final String PREFERENCE_NAME = "token";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRE_IN = "expire_in";
    private static final String TOKEN_TYPE = "token_type";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private TokenPreferences(){
        prefs = Application.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public static TokenPreferences getInstance(){
        return instance;
    }

    public void setAccessToken(String accessToken){
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public String getAccessToken(){
        return prefs.getString(ACCESS_TOKEN, "");
    }

    public void setRefreshToken(String refreshToken){
        editor.putString(REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

    public String getRefreshToken(){
        return prefs.getString(REFRESH_TOKEN, "");
    }

    public void setExpireIn(long expireIn){
        editor.putLong(EXPIRE_IN, expireIn);
        editor.apply();
    }

    public long getExpireIn(){
        return prefs.getLong(EXPIRE_IN, 0l);
    }

    public void setTokenType(String tokenType){
        editor.putString(TOKEN_TYPE, tokenType);
        editor.apply();
    }

    public String getTokenType(){
        return prefs.getString(TOKEN_TYPE, "");
    }
}
