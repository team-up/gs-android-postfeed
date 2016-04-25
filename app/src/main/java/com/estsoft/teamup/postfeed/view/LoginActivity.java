package com.estsoft.teamup.postfeed.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bluelinelabs.logansquare.LoganSquare;
import com.estsoft.teamup.postfeed.R;
import com.estsoft.teamup.postfeed.api.Api;
import com.estsoft.teamup.postfeed.model.TokenInfo;
import com.estsoft.teamup.postfeed.util.ResponseParser;
import com.estsoft.teamup.postfeed.util.TeamUP;
import com.estsoft.teamup.postfeed.util.TokenPreferences;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText insertIdEdt;
    private EditText insertPwdEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        insertIdEdt = (EditText)findViewById(R.id.insert_id_edt);
        insertPwdEdt = (EditText)findViewById(R.id.insert_password_edt);
    }

    public void generateToken(View view){
        String userId = insertIdEdt.getText().toString();
        String userPwd = insertPwdEdt.getText().toString();
        Api.getInstance().getToken(TeamUP.CLIENT_ID, TeamUP.CLIENT_SECRET, userId, userPwd, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = ResponseParser.toString(response);
                    Log.d("TeamUP", "generateToken response : " + responseString);
                    if(!responseString.isEmpty()){
                        TokenInfo tokenInfo = LoganSquare.parse(responseString, TokenInfo.class);
                        TokenPreferences.getInstance().setAccessToken(tokenInfo.getAccessToken());
                        TokenPreferences.getInstance().setRefreshToken(tokenInfo.getRefreshToken());
                        TokenPreferences.getInstance().setExpireIn(tokenInfo.getExpire());
                        TokenPreferences.getInstance().setTokenType(tokenInfo.getTokenType());

                        gotoTeamListActivity();
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "generateToken response parsing Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TeamUP", "generateToken request error");
                t.printStackTrace();
            }
        });
    }

    private void gotoTeamListActivity(){
        String userId = insertIdEdt.getText().toString();
        String userPwd = insertPwdEdt.getText().toString();
        if(userId.isEmpty() || userPwd.isEmpty()) return;

        Intent intent = new Intent(getApplicationContext(), TeamListActivity.class);
        startActivity(intent);
        finish();
    }
}
