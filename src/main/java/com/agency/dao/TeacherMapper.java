package com.agency.dao;

import com.agency.pojo.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ch33orange
 */
public interface TeacherMapper {
    @Delete({
        "delete from agency_teacher",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into agency_teacher (id, icon, ",
        "username, password, ",
        "gender, email, phone, ",
        "degree, title, bio, ",
        "question, answer, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=INTEGER}, #{icon,jdbcType=VARCHAR}, ",
        "#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{gender,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
        "#{degree,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, #{bio,jdbcType=VARCHAR}, ",
        "#{question,jdbcType=VARCHAR}, #{answer,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Teacher record);

    int insertSelective(Teacher record);

    @Select({
        "select",
        "id, icon, username, password, gender, email, phone, degree, title, bio, question, ",
        "answer, create_time, update_time",
        "from agency_teacher",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.agency.dao.TeacherMapper.BaseResultMap")
    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    @Update({
        "update agency_teacher",
        "set icon = #{icon,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=INTEGER},",
          "email = #{email,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "degree = #{degree,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "bio = #{bio,jdbcType=VARCHAR},",
          "question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Teacher record);
}