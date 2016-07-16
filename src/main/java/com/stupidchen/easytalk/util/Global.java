package com.stupidchen.easytalk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mike on 16/6/8.
 * TODO
 */
public class Global {
    private static Logger logger = LoggerFactory.getLogger(Global.class);

    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();

    private static ConcurrentHashMap<String, String> tokenPool = new ConcurrentHashMap<String, String>();

    private static ConcurrentHashMap<String, String> userIdPool = new ConcurrentHashMap<String, String>();

    private static Set<String> onlinePool = Collections.synchronizedSet(new HashSet<String>());

    private static Global instance = new Global();

    public static Global getInstance() {
        return instance;
    }

    private void Global() {
    }

    public void userLogin(String token, Session session) {
        String userId = tokenPool.get(token);
        addSession(token, session);
        addOnlineUser(userId);
    }

    public void userLogout(String token, Session session) {
        if (token == null) return;
        String userId = tokenPool.get(token);
        if (userId == null) return;
        removeUserIdByToken(token);
        removeOnlineUser(userId);
        removeSession(token);
    }

    public void userOffline(String token, Session session) {
        String userId = tokenPool.get(token);
        removeOnlineUser(userId);
        removeSession(token);
    }

    public void boardcast(String data) {
        for (String userId: onlinePool
             ) {
            try {
                logger.info("Boardcast to " + userId);
                String token = userIdPool.get(userId);
                logger.info("Boardcast to " + token);
                sessionPool.get(token).getBasicRemote().sendText(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Session getSessionByToken(String token) {
        return sessionPool.get(token);
    }

    public Session getSessionByUserId(String userId) {
        String token = userIdPool.get(userId);

        if (token != null) return sessionPool.get(token);
        return null;
    }

    public void removeSession(String token) {
        sessionPool.remove(token);
    }

    public String getUserIdByToken(String token) {
        return tokenPool.get(token);
    }

    public String getTokenByUserId(String userId) {
        return userIdPool.get(userId);
    }

    public void removeUserIdByToken(String token) {
        String userId = tokenPool.get(token);
        tokenPool.remove(token);
        userIdPool.remove(userId);
    }

    public void addSession(String token, Session session) {
        sessionPool.put(token, session);
    }

    public void addUserId(String token, String userId) {
        logger.info("Add token and user: " + token + '&' + userId);
        tokenPool.put(token, userId);
        userIdPool.put(userId, token);
    }

    public String[] getAllUserId() {
        String[] ret = new String[tokenPool.values().size()];
        return tokenPool.values().toArray(ret);
    }

    public String[] getOnlineUserId() {
        String[] ret = new String[onlinePool.size()];
        return onlinePool.toArray(ret);
    }

    public void addOnlineUser(String userId) {
        onlinePool.add(userId);
    }

    public void removeOnlineUser(String userId) {
        onlinePool.remove(userId);
    }

    public boolean ifUserOnline(String userId) {
        if (onlinePool.contains(userId)) return true;
        return false;
    }

}
