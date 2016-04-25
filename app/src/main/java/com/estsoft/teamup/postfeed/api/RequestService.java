package com.estsoft.teamup.postfeed.api;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestService {
    @FormUrlEncoded
    @POST("oauth2/token?grant_type=password")
    Call<ResponseBody> getToken(@Field(OAuth2.PARAM_NAME_CLIENT_ID) String client_id,
                                @Field(OAuth2.PARAM_NAME_CLIENT_SECRET) String client_secret,
                                @Field(OAuth2.PARAM_NAME_USERNAME) String username,
                                @Field(OAuth2.PARAM_NAME_PASSWORD) String password);

    @GET("v1/teams")
    Call<ResponseBody> getTeams(@Header(RequestHeader.AUTHORIZATION) String authorization,
                                @Header(RequestHeader.USERAGENT) String userAgent);

    @GET("v1/feedgroups/{teamIndex}")
    Call<ResponseBody> getFeedGroups(@Header(RequestHeader.AUTHORIZATION) String authorization,
                                     @Header(RequestHeader.USERAGENT) String userAgent,
                                     @Path(value = "teamIndex", encoded = false) long teamIndex);


    @POST("v1/feed/{feedGroup}/{type}")
    Call<ResponseBody> postFeed(@Header(RequestHeader.AUTHORIZATION) String authorization,
                                @Header(RequestHeader.USERAGENT) String userAgent,
                                @Path(value = "feedGroup", encoded = false) long feedGroup,
                                @Path(value = "type", encoded = false) int type,
                                @Body HashMap<String, String> body);
}
