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
import com.estsoft.teamup.postfeed.model.FeedGroupInfo;
import com.estsoft.teamup.postfeed.util.ResponseParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView groupListView;
    private ArrayList<FeedGroupInfo> feedGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list);
        getSupportActionBar().setTitle(R.string.group_list);
        groupListView = (ListView)findViewById(R.id.feedgroup_listview);
        groupListView.setOnItemClickListener(this);
        getFeedGroups();
    }

    public void getFeedGroups(){
        long teamIndex = getIntent().getLongExtra("TeamIndex", 0l);
        Api.getInstance().getFeedGroups(teamIndex, new Callback < ResponseBody > () {
            @Override
            public void onResponse (Call<ResponseBody> call, Response<ResponseBody> response){
                try {
                    String responseString = ResponseParser.toString(response);
                    Log.d("TeamUP", "getFeedGroups response : " + responseString);
                    if (!responseString.isEmpty()) {
                        feedGroups = new ArrayList<>(LoganSquare.parseList(new JSONObject(responseString).getString("feedgroups"), FeedGroupInfo.class));
                        if (feedGroups != null && !feedGroups.isEmpty()) {
                            groupListView.setAdapter(new GroupListAdapter(feedGroups));
                        }
                    }
                } catch (IOException e) {
                    Log.d("TeamUP", "getFeedGroups response parsing error");
                } catch (JSONException e) {
                    Log.d("TeamUP", "getFeedGroups response parsing error");
                }
            }

            @Override
            public void onFailure (Call < ResponseBody > call, Throwable t){
                Log.d("TeamUP", "getFeedGroups request error");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), PostFeedActivity.class);
        intent.putExtra("GroupIndex", feedGroups.get(position).getFeedgroup());
        startActivity(intent);
    }

    class GroupListAdapter extends BaseAdapter {

        private ArrayList<FeedGroupInfo> groups;

        public GroupListAdapter(ArrayList<FeedGroupInfo> groups) {
            this.groups = groups;
        }

        @Override
        public int getCount() {
            return groups.size();
        }

        @Override
        public FeedGroupInfo getItem(int position) {
            return groups.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView groupName;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.group_item, null, false);
                groupName = (TextView)convertView.findViewById(R.id.group_name);
                convertView.setTag(groupName);
            } else {
                groupName = (TextView)convertView.getTag();
            }

            // 피드그룹 이름
            FeedGroupInfo teamInfo = getItem(position);
            groupName.setText(teamInfo.getGroupname());
            return convertView;
        }
    }
}
