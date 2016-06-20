package com.stupidchen.easytalk.data.Mapper;

import com.stupidchen.easytalk.data.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Mike on 16/6/7.
 */
public interface UserMapper {
    @Select("SELECT * FROM User WHERE userId = #{0} AND password = #{1}")
    User selectUser(String userId, String password);

    @Insert("INSERT INTO User VALUES (#{0}, #{1})")
    void insertUser(String userId, String password);

    @Delete("DELETE FROM User WHERE userId = #{0}")
    void deleteUser(String userId);

    @Update("UPDATE User SET password = #{1} WHERE userId = #{0}")
    void updateUser(String userId, String password);
}
