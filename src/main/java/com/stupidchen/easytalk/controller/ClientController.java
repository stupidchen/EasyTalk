package com.stupidchen.easytalk.controller;

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
 * Created by Mike on 16/6/19.
 */

@ServerEndpoint("/chat")
@Component
public class ClientController {
    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    private Session session;

    private SqlSession sqlSession;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("New client trying to connect.");
        this.session = session;
        try {
            session.getBasicRemote().sendText("4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("Received: " + message);
        String sendMsg = "";
        if (message.charAt(0) == '1') {

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
            session.close();
        } catch (IOException e) {
            logger.warn(e.getStackTrace().toString());
        }
        logger.info("Auth connection has been closed.");
    }
}
