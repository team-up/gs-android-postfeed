package com.estsoft.teamup.postfeed.api;

/**
 * Created by click on 2016-04-12.
 */
public class OAuth2 {
    // params
    public static final String PARAM_NAME_CLIENT_ID = "client_id";
    public static final String PARAM_NAME_CLIENT_SECRET = "client_secret";
    public static final String PARAM_NAME_USERNAME = "username";
    public static final String PARAM_NAME_PASSWORD = "password";
    public static final String PARAM_NAME_GRANT_TYPE = "grant_type";

    // grant type
    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_BEARER = "bearer";
    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    // etc
    public static final String REDIRECT_URI_PARAM_NAME = "redirect_uri";
    public static final String CODE_PARAM_NAME = "code";

    // exception
    public static final String ERROR_URI = "error_uri";
    public static final String ID_PW_MISMATCH = "id_pw_not_match";
    public static final String INVALID_REQUEST = "invalid_request";
    public static final String INVALID_CLIENT = "invalid_client";
    public static final String INVALID_GRANT = "invalid_grant";
    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
    public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
    public static final String INVALID_SCOPE = "invalid_scope";
    public static final String INSUFFICIENT_SCOPE = "insufficient_scope";
    public static final String INVALID_TOKEN = "invalid_token";
    public static final String REDIRECT_URI_MISMATCH = "redirect_uri_mismatch";
    public static final String UNSUPPORTED_RESPONSE_TYPE = "unsupported_response_type";
    public static final String ACCESS_DENIED = "access_denied";
}
