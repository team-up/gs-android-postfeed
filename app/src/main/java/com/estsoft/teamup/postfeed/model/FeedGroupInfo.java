package com.estsoft.teamup.postfeed.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class FeedGroupInfo {
    @JsonField(name = "feedgroup")
    long feedgroup;
    @JsonField(name = "team")
    long team;
    @JsonField(name = "groupfile")
    long groupfile;
    @JsonField(name = "groupname")
    String groupname = "";
    @JsonField(name = "usercount")
    int usercount;
    @JsonField(name = "membertype")
    int membertype;
    @JsonField(name = "watchfeed")
    int watchfeed;
    @JsonField(name = "write")
    int write;
    @JsonField(name = "remove")
    int remove;
    @JsonField(name = "replywrite")
    int replywrite;
    @JsonField(name = "replyremove")
    int replyremove;
    @JsonField(name = "alerttype")
    int alerttype;
    @JsonField(name = "alertfeed")
    int alertfeed;
    @JsonField(name = "alertreply")
    int alertreply;
    @JsonField(name = "star")
    int star;
    @JsonField(name = "writable")
    int writable;

    public long getFeedgroup() {
        return feedgroup;
    }

    public long getTeam() {
        return team;
    }

    public long getGroupfile() {
        return groupfile;
    }

    public String getGroupname() {
        return groupname;
    }

    public int getUsercount() {
        return usercount;
    }

    public int getMembertype() {
        return membertype;
    }

    public int getWatchfeed() {
        return watchfeed;
    }

    public int getWrite() {
        return write;
    }

    public int getRemove() {
        return remove;
    }

    public int getReplywrite() {
        return replywrite;
    }

    public int getReplyremove() {
        return replyremove;
    }

    public int getAlerttype() {
        return alerttype;
    }

    public int getAlertfeed() {
        return alertfeed;
    }

    public int getAlertreply() {
        return alertreply;
    }

    public int getStar() {
        return star;
    }

    public int getWritable() {
        return writable;
    }
}
