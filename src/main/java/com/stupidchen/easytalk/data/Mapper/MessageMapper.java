package com.stupidchen.easytalk.data.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultMap;

import java.sql.Time;

/**
 * Created by Mike on 16/6/8.
 */
public interface MessageMapper {
    @Select("SELECT * FROM Message WHERE fromUserId = #{0}")
    ResultMap selectMessageBySender(String userId);

    @Select("SELECT * FROM Message WHERE toUserId = #{0}")
    ResultMap selectMessageByReceiver(String userId);

    @Select("SELECT * FROM Message WHERE fromUserId = #{0} AND toUserId = #{1}")
    ResultMap selectMessage(String fromUserId, String toUserId);

    @Insert("INSERT INTO Message (fromUserId, toUserId, sendTime, message, status) VALUES (#{0}, #{1}, #{2}, #{3}, #{4})")
    void insertMessage(String fromUserId, String toUserId, String sendTime, String message, int status);
}
