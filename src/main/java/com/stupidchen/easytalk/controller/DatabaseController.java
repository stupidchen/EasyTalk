package com.stupidchen.easytalk.controller;

import com.stupidchen.easytalk.EasyTalkApplication;
import com.stupidchen.easytalk.data.Mapper.MessageMapper;
import com.stupidchen.easytalk.data.Mapper.UserInfoMapper;
import com.stupidchen.easytalk.data.Mapper.UserMapper;
import com.stupidchen.easytalk.data.Message;
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

    public UserInfo getUserInfo(String userId) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        UserInfo thisUser = userInfoMapper.selectUserInfo(userId);

        return thisUser;
    }

    public void addUser(String userId, String username, String password) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insertUser(userId, password);
        UserInfoMapper userInfoMapper = sqlSession.getMapper(UserInfoMapper.class);
        //TOFIX Add the information of user
        userInfoMapper.insertUserInfo(userId, username, "Male");
        sqlSession.commit();
    }

    public void insertMessage(MessageMapper mapper, Message message) {
        mapper.insertMessage(message.getFromUserId(), message.getToUserId(), message.getSendTime(), message.getMessage(), message.getStatus());
    }

    public void addMessage(Message message) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
        insertMessage(messageMapper, message);
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
