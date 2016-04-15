package com.estsoft.teamup.postfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.estsoft.teamup.postfeed.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    private TextView generateTokenStatus;
    private EditText feedContentEditText;
    private Button postFeedButton;
    private TextView postFeedStatus;
    private boolean isTokenGenerated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateTokenStatus = (TextView)findViewById(R.id.generate_token_status);
        feedContentEditText = (EditText)findViewById(R.id.feed_content_edittext);
        feedContentEditText.addTextChangedListener(this);
        postFeedButton = (Button)findViewById(R.id.post_feed_button);
        postFeedStatus = (TextView)findViewById(R.id.post_feed_status);
    }

    public void generateToken(View view){
        Api.getInstance().getToken(TeamUP.CLIENT_ID, TeamUP.CLIENT_SECRET, TeamUP.ID, TeamUP.PASSWORD, new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = ResponseParser.toString(response);
                    if(!responseString.isEmpty()){
                        TokenInfo tokenInfo = LoganSquare.parse(responseString, TokenInfo.class);
                        TokenPreferences.getInstance().setAccessToken(tokenInfo.getAccessToken());
                        TokenPreferences.getInstance().setRefreshToken(tokenInfo.getRefreshToken());
                        TokenPreferences.getInstance().setExpireIn(tokenInfo.getExpire());
                        TokenPreferences.getInstance().setTokenType(tokenInfo.getTokenType());

                        // refresh UI
                        isTokenGenerated = true;
                        generateTokenStatus.setText("token generated!");
                        if(!feedContentEditText.getText().toString().isEmpty()) {
                            postFeedButton.setEnabled(true);
                        }
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "getToken response parsing Error");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void postFeed(View view){
        String message = feedContentEditText.getText().toString();
        Api.getInstance().postFeed(TeamUP.FEEDGROUP_SEQUENCE, TeamUP.TYPE_NORMAL, message, null, null, null, TeamUP.ALERT_OFF, new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = ResponseParser.toString(response);
                    if(!responseString.isEmpty()){
                        long feed = new JSONObject(responseString).getLong("feed"); // posted feed number

                        // refresh UI
                        postFeedStatus.setText("new feed posted!");
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "postFeed response parsing error");
                } catch (JSONException e) {
                    Log.d("TeamUP", "postFeed feed number parsing error");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(isTokenGenerated) {
            if (s.toString().isEmpty()) {
                postFeedButton.setEnabled(false);
            } else {
                postFeedButton.setEnabled(true);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
