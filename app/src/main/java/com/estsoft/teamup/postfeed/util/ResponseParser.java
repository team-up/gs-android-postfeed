package com.estsoft.teamup.postfeed.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ResponseParser {
    public static String toString(Response<ResponseBody> response) throws IOException{
        try {
            StringBuilder sb = new StringBuilder("");
            if (response.body() != null) {
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        }
    }
}
