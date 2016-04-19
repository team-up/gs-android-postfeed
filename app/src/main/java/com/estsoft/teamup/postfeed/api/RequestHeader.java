package com.estsoft.teamup.postfeed.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.estsoft.teamup.postfeed.Application;
import com.estsoft.teamup.postfeed.util.TokenPreferences;

/**
 * Created by click on 2016-04-12.
 */
public class RequestHeader {
    public static final String AUTHORIZATION = "Authorization";
    public static final String USERAGENT = "User-Agent";

    private static RequestHeader instance;
    private TokenPreferences mTokenPrefs;
    private PackageInfo pkgInfo;
    private Context mContext;

    private RequestHeader(){
        mContext = Application.getContext();
        mTokenPrefs = TokenPreferences.getInstance();
        try {
            pkgInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
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

    public String getAuth(){
        return mTokenPrefs.getTokenType() + " " + mTokenPrefs.getAccessToken();
    }

    public String getUserAgent(){
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
