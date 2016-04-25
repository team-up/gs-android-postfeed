package com.estsoft.teamup.postfeed.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

@JsonObject
public class TeamInfo {
    @JsonField(name = "index")
    long index;
    @JsonField(name = "name")
    String name;
    @JsonField(name = "status")
    String status;
    @JsonField(name = "time")
    long time;
//    @JsonField(name = "department")
//    ArrayList<DepartmentInfo> department;
//    @JsonField(name = "users")
//    ArrayList<UserInfo> users;
    @JsonField(name = "user_role")
    ArrayList<String> user_role = new ArrayList<>();

    public long getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public long getTime() {
        return time;
    }

    public ArrayList<String> getUser_role() {
        return user_role;
    }
}
