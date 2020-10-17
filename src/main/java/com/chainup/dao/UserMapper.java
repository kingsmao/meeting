package com.chainup.dao;

import com.chainup.entity.User;
import com.chainup.entity.UserExample;
import com.chainup.service.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiLi
 */
public interface UserMapper extends SqlMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);


    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}