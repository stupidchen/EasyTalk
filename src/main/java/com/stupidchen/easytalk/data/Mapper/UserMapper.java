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
    @Select("SELECT * FROM user WHERE userId = #{0}")
    User selectUser(String userId);

    @Select("SELECT * FROM user WHERE userId = #{0} AND password = #{1}")
    User getUser(String userId, String password);

    @Insert("INSERT INTO user VALUES (#{0}, #{1}, #{2})")
    void insertUser(String userId, String username, String password);

    @Delete("DELETE FROM user WHERE userId = #{0}")
    void deleteUser(String userId);

    @Update("UPDATE user SET username = #{1}, password = #{2} WHERE userId = #{0}")
    void updateUser(String userId, String username, String password);
}
