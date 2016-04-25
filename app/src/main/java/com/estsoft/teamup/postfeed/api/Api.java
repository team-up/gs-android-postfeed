package com.estsoft.teamup.postfeed.api;

import com.bluelinelabs.logansquare.LoganSquare;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Api {
    private static final Api instance = new Api();
    private static final String AUTH_BASE_URL = "https://auth.tmup.com/";
    private static final String EDGE_BASE_URL = "https://edge.tmup.com/";
    private static RequestService authService = new Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(RequestService.class);

    private static RequestService edgeService = new Retrofit.Builder()
            .baseUrl(EDGE_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
            .create(RequestService.class);

    private Api() {

    }

    public static Api getInstance(){
        return instance;
    }

    public static RequestService getAuthService() {
        return authService;
    }

    public RequestService getEdgeService(){
        return edgeService;
    }

    public void getToken(String clientId, String clientSecret, String id, String password, Callback<ResponseBody> callback){
        Call<ResponseBody> call = getAuthService().getToken(clientId, clientSecret, id, password);
        call.enqueue(callback);
    }

    public void getTeams(Callback<ResponseBody> callback){
        Call<ResponseBody> call = getAuthService().getTeams(RequestHeader.getInstance().getAuthValue(), RequestHeader.getInstance().getUAValue());
        call.enqueue(callback);
    }

    public void getFeedGroups(long teamIndex, Callback<ResponseBody> callback){
        Call<ResponseBody> call = getEdgeService().getFeedGroups(RequestHeader.getInstance().getAuthValue(), RequestHeader.getInstance().getUAValue(), teamIndex);
        call.enqueue(callback);
    }

    public void postFeed(long feedgroup, int type, String content, ArrayList<Long> tagUsers, ArrayList<Long> tagFeeds, ArrayList<Long> files, int push, Callback<ResponseBody> callback){
        HashMap<String, String> body = new HashMap<>();
        try {
            body.put("content", content);
            if(tagUsers != null && !tagUsers.isEmpty()) {
                body.put("tagusers", new JSONArray(LoganSquare.serialize(tagUsers)).toString());
            }
            if(tagFeeds != null && !tagFeeds.isEmpty()) {
                body.put("tagfeeds", new JSONArray(LoganSquare.serialize(tagFeeds)).toString());
            }
            if(files != null && !files.isEmpty()) {
                body.put("files", new JSONArray(LoganSquare.serialize(files)).toString());
            }
            body.put("push", String.valueOf(push));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> call = getEdgeService().postFeed(RequestHeader.getInstance().getAuthValue(), RequestHeader.getInstance().getUAValue(), feedgroup, type, body);
        call.enqueue(callback);
    }
}
