package com.stupidchen.easytalk.controller;

import com.stupidchen.easytalk.EasyTalkApplication;
import com.stupidchen.easytalk.data.Mapper.UserInfoMapper;
import com.stupidchen.easytalk.data.Mapper.UserMapper;
import com.stupidchen.easytalk.data.User;
import com.stupidchen.easytalk.data.UserInfo;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by Mike on 16/6/19.
 */

public class DatabaseController {
    private static SqlSession sqlSession;

    public boolean findUser(String userId, String password) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User thisUser = userMapper.selectUser(userId, password);

        if (thisUser != null) return true;
        return false;
    }

    public boolean checkUser(String userId) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo thisUser = userInfoMapper.selectUserInfo(userId);

        if (thisUser != null) return true;
        return false;
    }

    //TOFIX Add the information of user
    public void addUser(String userId, String username, String password) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insertUser(userId, password);
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        userInfoMapper.insertUserInfo(userId, username, "Male");
        sqlSession.commit();
    }

    public void addMessage() {

    }

    public void updateUserInfo() {

    }

    public void updateUser() {

    }

    public void addRelation() {

    }

    public void deleteRelation() {

    }
}
