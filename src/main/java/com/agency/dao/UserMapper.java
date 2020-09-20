package com.agency.dao;

import com.agency.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ch33orange
 */
public interface UserMapper {
    @Delete({
        "delete from agency_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into agency_user (id, icon, ",
        "username, password, ",
        "gender, email, phone, ",
        "question, answer, ",
        "role, create_time, ",
        "update_time, bio)",
        "values (#{id,jdbcType=INTEGER}, #{icon,jdbcType=VARCHAR}, ",
        "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{gender,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
        "#{question,jdbcType=VARCHAR}, #{answer,jdbcType=VARCHAR}, ",
        "#{role,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{bio,jdbcType=LONGVARCHAR})"
    })
    int insert(User record);

    int insertSelective(User record);

    @Select({
        "select",
        "id, icon, username, password, gender, email, phone, question, answer, role, ",
        "create_time, update_time, bio",
        "from agency_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.agency.dao.UserMapper.ResultMapWithBLOBs")
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    @Update({
        "update agency_user",
        "set icon = #{icon,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=INTEGER},",
          "email = #{email,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "role = #{role,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "bio = #{bio,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(User record);

    @Update({
        "update agency_user",
        "set icon = #{icon,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=INTEGER},",
          "email = #{email,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "role = #{role,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);
}