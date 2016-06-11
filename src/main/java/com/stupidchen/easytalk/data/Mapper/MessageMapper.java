package com.stupidchen.easytalk.data.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultMap;

import java.sql.Time;

/**
 * Created by Mike on 16/6/8.
 */
public interface MessageMapper {
    @Select("SELECT * FROM message WHERE fromUserId = #{0}")
    ResultMap getMessageBySender(String userId);

    @Select("SELECT * FROM message WHERE toUserId = #{0}")
    ResultMap getMessageByReceiver(String userId);

    @Insert("INSERT INTO message VALUES (#{0}, #{1}, #{2}, #{3}, #{4}, #{5})")
    void insertNewMessage(String messageId, String fromUserId, String toUserId, Time sendTime, String message, int status);
}
