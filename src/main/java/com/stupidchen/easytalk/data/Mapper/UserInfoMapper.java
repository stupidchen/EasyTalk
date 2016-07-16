package com.stupidchen.easytalk.data.Mapper;

import com.stupidchen.easytalk.data.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Mike on 16/6/19.
 */

public interface UserInfoMapper {
    @Select("SELECT * FROM UserInfo WHERE userId = #{0}")
    UserInfo selectUserInfo(String userId);

    @Insert("INSERT INTO UserInfo VALUES (#{0}, #{1}, #{2})")
    void insertUserInfo(String userId, String username, String gender);

    @Delete("DELETE FROM UserInfo WHERE userId = #{0}")
    void deleteUserInfo(String userId);

    @Update("UPDATE UserInfo SET username = #{1}, gender = #{2} WHERE userId = #{0}")
    void updateUserInfo(String userId, String username, String gender);
}
