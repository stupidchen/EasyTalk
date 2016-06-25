package com.stupidchen.easytalk.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stupidchen.easytalk.data.Message;
import com.stupidchen.easytalk.data.User;
import com.stupidchen.easytalk.data.UserInfo;
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
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

/**
 * Created by Mike on 16/6/19.
 */

@ServerEndpoint("/chat")
@Component
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    private Session session;

    private DatabaseController db = new DatabaseController();

    private String userId;

    private String token;

    private UserInfo userInfo;

    private Gson gson;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("New client trying to connect.");
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        this.session = session;
        try {
            session.getBasicRemote().sendText("C:S");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info("Received: " + message);
        String sendMsg = "";
        if (message.indexOf("UI") == 0) {
            token = message.split(":")[1];
            if (token.equals("null")){
                onClose();
                return;
            }
            userId = Global.getInstance().getUserIdByToken(token);
            if (userId == null){
                onClose();
                return;
            }
            userInfo = db.getUserInfo(userId);

            Global.getInstance().boardcast("AU:" + gson.toJson(userInfo, UserInfo.class));
            Global.getInstance().userLogin(token, session);

            session.getBasicRemote().sendText("UI:" + gson.toJson(userInfo, UserInfo.class));
        }

        if (message.indexOf("OI") == 0) {
            String[] onlineUserId = Global.getInstance().getOnlineUserId();
            UserInfo[] onlineUsers = new UserInfo[onlineUserId.length - 1];
            int i = 0;
            for (String userId: onlineUserId
                 ) {
                if (!userId.equals(this.userId)) {
                    onlineUsers[i++] = db.getUserInfo(userId);
                }
            }
            session.getBasicRemote().sendText("OI:" + gson.toJson(onlineUsers, onlineUsers.getClass()));
        }

        if (message.indexOf("MI") == 0) {
            Message[] messages;

        }

        //MS:{toUserId, time, message}
        if (message.indexOf("MS") == 0) {
            message = message.substring(message.indexOf(':') + 1);

            Message messageSend = gson.fromJson(message, Message.class);
            messageSend.setFromUserId(userId);
            messageSend.setStatus(0);
            String toUserId = messageSend.getToUserId();
            if (Global.getInstance().ifUserOnline(toUserId)) {
                Global.getInstance().getSessionByUserId(toUserId).getBasicRemote().sendText("MR:" + gson.toJson(messageSend));
            }
            db.addMessage(messageSend);
        }

        if (message.indexOf("LO") == 0) {
            onClose();
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
        try {
            if (!token.equals("null")) {
                Global.getInstance().userLogout(token, session);
                Global.getInstance().boardcast("RU:" + gson.toJson(userId, userId.getClass()));
            }
            session.close();
        } catch (IOException e) {
            logger.warn(e.getStackTrace().toString());
        }
        logger.info("Chat connection has been closed.");
    }
}
