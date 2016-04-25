package com.estsoft.teamup.postfeed.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.estsoft.teamup.postfeed.R;
import com.estsoft.teamup.postfeed.api.Api;
import com.estsoft.teamup.postfeed.util.ResponseParser;
import com.estsoft.teamup.postfeed.util.TeamUP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFeedActivity extends AppCompatActivity{

    private EditText feedContentEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_feed);
        getSupportActionBar().setTitle(R.string.post_feed);
        feedContentEdt = (EditText)findViewById(R.id.feed_content_edittext);
    }

    public void postFeed(View view){
        long groupIndex = getIntent().getLongExtra("GroupIndex", 0l);
        String message = feedContentEdt.getText().toString();
        if(message.isEmpty()) return;
        Api.getInstance().postFeed(groupIndex, TeamUP.TYPE_NORMAL, message, null, null, null, TeamUP.ALERT_OFF, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = ResponseParser.toString(response);
                    Log.d("TeamUP", "postFeed response : " + responseString);
                    if(!responseString.isEmpty()){
                        long feed = new JSONObject(responseString).getLong("feed"); // posted feed number
                        Toast.makeText(getApplicationContext(), R.string.complete_post_feed, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "postFeed response parsing error");
                } catch (JSONException e) {
                    Log.d("TeamUP", "postFeed feed number parsing error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TeamUP", "postFeed request error");
            }
        });
    }
}
