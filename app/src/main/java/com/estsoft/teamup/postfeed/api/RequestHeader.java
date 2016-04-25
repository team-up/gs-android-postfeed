package com.estsoft.teamup.postfeed.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.estsoft.teamup.postfeed.Application;
import com.estsoft.teamup.postfeed.util.TokenPreferences;

public class RequestHeader {
    public static final String AUTHORIZATION = "Authorization";
    public static final String USERAGENT = "User-Agent";
    private static RequestHeader instance;
    private TokenPreferences tokenPrefs;
    private PackageInfo pkgInfo;
    private Context context;

    private RequestHeader(){
        context = Application.getContext();
        tokenPrefs = TokenPreferences.getInstance();
        try {
            pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static RequestHeader getInstance(){
        if(instance == null){
            instance = new RequestHeader();
        }

        return instance;
    }

    public String getAuthValue(){
        return tokenPrefs.getTokenType() + " " + tokenPrefs.getAccessToken();
    }

    public String getUAValue(){
        String httpAgent = System.getProperty("http.agent");
        String dalvik = httpAgent.substring(0, httpAgent.indexOf("(")).trim();
        httpAgent = httpAgent.substring(httpAgent.indexOf("("));
        if(pkgInfo == null){
            return "TeamUP/ " + httpAgent + " " + dalvik;
        } else {
            return "TeamUP/" + pkgInfo.versionName + " " + httpAgent + " " + dalvik;
        }
    }
}
