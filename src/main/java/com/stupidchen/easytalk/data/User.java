package com.stupidchen.easytalk.data;

/**
 * Created by Mike on 16/6/6.
 */
public class User {
    private String userId;

    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userId + ":" + password;
    }
}
