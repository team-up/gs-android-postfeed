package com.estsoft.teamup.postfeed.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by click on 2016-04-12.
 */
public interface RequestService {
    @FormUrlEncoded
    @POST("oauth2/token?grant_type=password")
    Call<ResponseBody> getToken(@Field(OAuth2.PARAM_NAME_CLIENT_ID) String client_id,
                                @Field(OAuth2.PARAM_NAME_CLIENT_SECRET) String client_secret,
                                @Field(OAuth2.PARAM_NAME_USERNAME) String username,
                                @Field(OAuth2.PARAM_NAME_PASSWORD) String password);

    @POST("v1/feed/{feedGroup}/{type}")
    Call<ResponseBody> postFeed(@Path(value = "feedGroup", encoded = false) long feedGroup,
                                @Path(value = "type", encoded = false) int type,
                                @Body HashMap<String, String> body);
}
