package com.estsoft.teamup.postfeed.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class TokenInfo {
    @JsonField(name = "access_token")
    String access_token = "";

    @JsonField(name = "expires_in")
    long expires_in = 0L;

    @JsonField(name = "token_type")
    String token_type = "";

    @JsonField(name = "refresh_token")
    String refresh_token = "";

    @JsonField(name = "exception")
    String exception = "";

    @JsonField(name = "error")
    String error = "";

    @JsonField(name = "error_description")
    String error_description = "";

    public static final String FIELD_ACCESS_TOKEN = "access_token";
    public static final String FIELD_EXPIRE = "expires_in";
    public static final String FIELD_TOKEN_TYPE = "token_type";
    public static final String FIELD_REFRESH_TOKEN = "refresh_token";
    public static final String FIELD_EXCEPTION = "exception";
    public static final String FIELD_ERROR = "error";
    public static final String FIELD_ERROR_DESCRIPTION = "error_description";

    public String getAccessToken() {
        return access_token;
    }

    public long getExpire(){
        return expires_in;
    }

    public String getTokenType(){
        return token_type;
    }

    public String getRefreshToken(){
        return refresh_token;
    }

    public String getException() {
        return exception;
    }

    public String getError(){
        return error;
    }

    public String getError_description() {
        return error_description;
    }

    public String toString() {
        return FIELD_ACCESS_TOKEN + " : " + getAccessToken() + "\n" +
                FIELD_EXPIRE + " : " + getExpire() + "\n" +
                FIELD_TOKEN_TYPE + " : " + getTokenType() + "\n" +
                FIELD_REFRESH_TOKEN + " : " + getRefreshToken() + "\n" +
                FIELD_EXCEPTION + " : " + getException() + "\n" +
                FIELD_ERROR + " : " + getError() + "\n" +
                FIELD_ERROR_DESCRIPTION + " : " + getError_description();
    }
}