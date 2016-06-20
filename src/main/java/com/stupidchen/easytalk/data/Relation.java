package com.stupidchen.easytalk.data;

/**
 * Created by Mike on 16/6/19.
 */
public class Relation {
    private String thisUserId;

    private String thatUserId;

    private int relation;

    public Relation(String thisUserId, String thatUserId, int relation) {
        this.thisUserId = thisUserId;
        this.thatUserId = thatUserId;
        this.relation = relation;
    }

    public String getThisUserId() {
        return thisUserId;
    }

    public void setThisUserId(String thisUserId) {
        this.thisUserId = thisUserId;
    }

    public String getThatUserId() {
        return thatUserId;
    }

    public void setThatUserId(String thatUserId) {
        this.thatUserId = thatUserId;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return thisUserId + '&' + thatUserId + String.valueOf(relation);
    }
}
