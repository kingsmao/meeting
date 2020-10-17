package com.chainup.dao;

import com.chainup.entity.Department;
import com.chainup.entity.DepartmentExample;
import java.util.List;

import com.chainup.service.SqlMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author LiLi
*/
public interface DepartmentMapper extends SqlMapper {
    int countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);


    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}