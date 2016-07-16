package com.stupidchen.easytalk.security;

import com.stupidchen.easytalk.EasyTalkApplication;
import com.stupidchen.easytalk.controller.DatabaseController;
import com.stupidchen.easytalk.data.Mapper.UserMapper;
import com.stupidchen.easytalk.data.User;
import com.stupidchen.easytalk.util.Global;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
public class Auth implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Auth.class);

    private Session session;

    private DatabaseController db = new DatabaseController();

    private Global global = Global.getInstance();

    public Auth() {

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
        String sendMsg = "";
        if (message.charAt(0) == 'L') {
            int sep = message.indexOf('&');
            String userId = message.substring(2, sep);
            String password = message.substring(sep + 1, message.length());
            boolean ifPass = db.findUser(userId, password);
            if (ifPass) {
                logger.info(userId + " login success.");
                String token = TokenProcessor.getInstance().generateToken(userId + password, false);
                logger.info(token);
                sendMsg = "L:S&" + token;
                global.addUserId(token, userId);
            } else {
                logger.info("Login failed.");
                sendMsg = "L:F&Email or password is wrong!";
            }
        }
        if (message.charAt(0) == 'R') {
            String[] temp = message.substring(2, message.length()).split("&");
            String username = temp[0];
            String userId = temp[1];
            String password = temp[2];
            String passwordConfirm = temp[3];
            if (!password.equals(passwordConfirm)) {
                logger.info("Register failed.");
                sendMsg = "R:F&Two passwords are not same!";
            } else {
                if (db.checkUser(userId)) {
                    logger.info("Register failed.");
                    sendMsg = "R:F&This email has been registered!";
                } else {
                    db.addUser(userId, username, password);
                    logger.info(userId + " registered.");
                    sendMsg = "R:S";
                }
            }

        }

        if (message.charAt(0) == 'T') {
            logger.info("Login use token");
            String token = message.substring(2);

            String userId = global.getUserIdByToken(token);
            if (userId == null) {
                logger.info("Token login failed");
                sendMsg = "T:F&Token is invalid";
            }
            else {
                logger.info("Token login success: " + userId);
                sendMsg = "T:P&" + userId;
            }
        }
        if (message.charAt(0) == 'O') {
            String token = message.substring(2);
            logger.info("Trying to logout. Token: " + token);
            global.removeUserIdByToken(token);

            onClose();
        }


        if (!sendMsg.isEmpty()) {
            try {
                logger.info("Send: " + sendMsg);
                session.getBasicRemote().sendText(sendMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    @OnClose
    public void onClose() {
        try {
            session.close();
        } catch (IOException e) {
            logger.warn(e.getStackTrace().toString());
        }
        logger.info("Auth connection has been closed.");
    }

    @Override
    public void run() {

    }
}
