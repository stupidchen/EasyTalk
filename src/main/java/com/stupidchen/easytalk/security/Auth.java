package com.stupidchen.easytalk.security;

import com.stupidchen.easytalk.EasyTalkApplication;
import com.stupidchen.easytalk.data.Mapper.UserMapper;
import com.stupidchen.easytalk.data.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by Mike on 16/6/8.
 */

@ServerEndpoint("/auth")
@Component
public class Auth {
    private static Logger logger = LoggerFactory.getLogger(Auth.class);

    private static Session session;

    private SqlSession sqlSession;

    private boolean findUser(String userId, String password) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User thisUser = userMapper.getUser(userId, password);

        if (thisUser != null) return true;
        return false;
    }

    private boolean checkUser(String userId) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User thisUser = userMapper.selectUser(userId);

        if (thisUser != null) return true;
        return false;
    }

    private void addUser(String userId, String username, String password) {
        if (sqlSession == null) sqlSession = EasyTalkApplication.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        userMapper.insertUser(userId, username, password);
        sqlSession.commit();
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("New auth connection.");
        this.session = session;
        try {
            session.getBasicRemote().sendText("C:S");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("Received: " + message);
        String sendMsg = new String();
        if (message.indexOf('L') != -1) {
            int sep = message.indexOf('&');
            String userId = message.substring(2, sep);
            String password = message.substring(sep + 1, message.length());
            boolean ifPass = findUser(userId, password);
            if (ifPass) {
                logger.info(userId + " login success.");
                String token = TokenProcessor.getInstance().generateToken(userId + password, false);
                logger.info(token);
                sendMsg = "L:S&" + token;
            } else {
                logger.info("Login failed.");
                sendMsg = "L:F&Email or password is wrong!";
            }
        }
        if (message.indexOf('R') != -1) {
            String[] temp = message.substring(2, message.length()).split("&");
            String username = temp[0];
            String userId = temp[1];
            String password = temp[2];
            String passwordConfirm = temp[3];
            if (!password.equals(passwordConfirm)) {
                logger.info("Register failed.");
                sendMsg = "R:F&Two passwords are not same!";
            } else {
                if (checkUser(userId)) {
                    logger.info("Register failed.");
                    sendMsg = "R:F&This email has been registered!";
                } else {
                    addUser(userId, username, password);
                    logger.info(userId + " registered.");
                    sendMsg = "R:S";
                }
            }
        }

        if (!sendMsg.isEmpty()) {
            try {
                session.getBasicRemote().sendText(sendMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    @OnClose
    public void onClose() {
        sqlSession.close();
        logger.info("Auth connection has been closed.");
    }
}
