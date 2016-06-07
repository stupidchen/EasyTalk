package com.stupidchen.easytalk.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by Mike on 16/6/6.
 */

@ServerEndpoint("/websocketTest")
@Component
public class WebSocketTest implements GeneralTest {
    private static Logger logger = LoggerFactory.getLogger(SelfCheck.class);

    private static String receive = "";

    private Session session;

    public WebSocketTest() {
        SelfCheck.register(this);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        receive += message;
        session.getBasicRemote().sendText(message);
    }

    @OnError
    public void onError(Throwable t) {

    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {

    }

    @Override
    public boolean execute() {
        logger.info("Into websocket test.. Received: " + receive);
        return true;
    }
}
