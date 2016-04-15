package com.estsoft.teamup.postfeed.api;

import com.bluelinelabs.logansquare.LoganSquare;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by click on 2016-04-12.
 */
public class Api {
    public static final String AUTH_BASE_URL = "https://auth.tmup.com/";
    public static final String EDGE_BASE_URL = "https://edge.tmup.com/";

    public Api() {

    }

    public static final Api instance = new Api();

    public static Api getInstance(){
        return instance;
    }

    public void getToken(String clientId, String clientSecret, String id, String password, Callback<ResponseBody> callback){
        Retrofit authRetrofit = new Retrofit.Builder()
                .baseUrl(AUTH_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RequestService service = authRetrofit.create(RequestService.class);
        Call<ResponseBody> call = service.getToken(clientId, clientSecret, id, password);
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

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        okhttp3.Request original = chain.request();
                        okhttp3.Request request = original.newBuilder()
                                .header(RequestHeader.AUTHORIZATION, RequestHeader.getInstance().getAuth())
                                .header(RequestHeader.USERAGENT, RequestHeader.getInstance().getUserAgent())
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                });

        Retrofit edgeRetrofit = new Retrofit.Builder()
                .baseUrl(EDGE_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        RequestService service = edgeRetrofit.create(RequestService.class);
        Call<ResponseBody> call = service.postFeed(feedgroup, type, body);
        call.enqueue(callback);
    }
}
