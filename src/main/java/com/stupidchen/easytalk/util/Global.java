package com.stupidchen.easytalk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mike on 16/6/8.
 * TODO
 */
public class Global {
    private static Logger logger = LoggerFactory.getLogger(Global.class);

    private static ConcurrentHashMap<String, Session> wsSessionPool = new ConcurrentHashMap<String, Session>();

    private static ConcurrentHashMap<String, String> tokenPool = new ConcurrentHashMap<String, String>();

    private static Global instance = new Global();

    public static Global getInstance() {
        return instance;
    }

    private void Global() {
    }

    public Session getWsSessionByToken(String token) {
        return wsSessionPool.get(token);
    }

    public String getUserIdByToken(String token) {
        return tokenPool.get(token);
    }

    public void removeUserIdByToken(String token) {
        tokenPool.remove(token);
    }

    public void addWsSession(String token, Session session) {
        wsSessionPool.put(token, session);
    }

    public void addUserId(String token, String userId) {
        logger.info("Add token and user: " + token + '&' + userId);
        tokenPool.put(token, userId);
    }
}
