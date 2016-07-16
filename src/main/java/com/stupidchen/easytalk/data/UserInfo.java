package com.stupidchen.easytalk.data;

/**
 * Created by Mike on 16/6/19.
 */
public class UserInfo {
    private String userId;

    private String username;

    private String gender;

    public UserInfo(String userId, String username, String gender) {
        this.userId = userId;
        this.username = username;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return userId + ':' + username + '&' + gender;
    }
}
