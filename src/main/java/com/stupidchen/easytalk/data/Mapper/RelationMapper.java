package com.stupidchen.easytalk.data.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.ResultMap;

/**
 * Created by Mike on 16/6/19.
 */
public interface RelationMapper {
    @Select("SELECT * FROM Relation WHERE thisUserId = #{0} AND relation = #{1}")
    ResultMap selectRelation(String userId, int relation);

    @Insert("INSERT INTO Relation VALUES (#{0}, #{1}, #{2})")
    void insertRelation(String thisUserId, String thatUserId, int relation);

    @Update("UPDATE Relation SET relation = #{2} WHERE thisUserId = #{0} AND thatUserId = #{1}")
    void updateRelation(String thisUserId, String thatUserId, int relation);
}
