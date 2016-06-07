package com.stupidchen.easytalk.data.Mapper;

import com.stupidchen.easytalk.data.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Mike on 16/6/7.
 */
public interface UserMapper {
    @Select("SELECT * FROM user WHERE userId = #{0}")
    User selectUser(String userId);

    @Insert("INSERT INTO user VALUES (#{0}, #{1}, #{2})")
    void insertUser(String userId, String username, String password);

    @Delete("DELETE FROM user WHERE userId = #{0}")
    void deleteUser(String userId);
}
