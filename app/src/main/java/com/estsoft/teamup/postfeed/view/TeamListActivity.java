package com.estsoft.teamup.postfeed.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.estsoft.teamup.postfeed.R;
import com.estsoft.teamup.postfeed.api.Api;
import com.estsoft.teamup.postfeed.model.TeamInfo;
import com.estsoft.teamup.postfeed.util.ResponseParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView teamListView;
    private ArrayList<TeamInfo> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_list);
        getSupportActionBar().setTitle(R.string.team_list);
        teamListView = (ListView)findViewById(R.id.team_listview);
        teamListView.setOnItemClickListener(this);
        getTeams();
    }

    public void getTeams(){
        Api.getInstance().getTeams(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseString = ResponseParser.toString(response);
                    Log.d("TeamUP", "getTeams response : " + responseString);
                    if (!responseString.isEmpty()) {
                        teams = new ArrayList<>(LoganSquare.parseList(responseString, TeamInfo.class));
                        if (teams != null && !teams.isEmpty()) {
                            teamListView.setAdapter(new TeamListAdapter(teams));
                        }
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "getTeams response parsing error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TeamUP", "getTeams request error");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), GroupListActivity.class);
        intent.putExtra("TeamIndex", teams.get(position).getIndex());
        startActivity(intent);
    }

    class TeamListAdapter extends BaseAdapter {

        private ArrayList<TeamInfo> teams;

        public TeamListAdapter(ArrayList<TeamInfo> teams) {
            this.teams = teams;
        }

        @Override
        public int getCount() {
            return teams.size();
        }

        @Override
        public TeamInfo getItem(int position) {
            return teams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView teamName;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.team_item, null, false);
                teamName = (TextView)convertView.findViewById(R.id.team_name);
                convertView.setTag(teamName);
            } else {
                teamName = (TextView)convertView.getTag();
            }

            // 팀 이름
            TeamInfo teamInfo = getItem(position);
            teamName.setText(teamInfo.getName());
            return convertView;
        }
    }
}
