package com.chainup.dao;

import java.util.List;

import com.chainup.entity.Meeting;
import com.chainup.entity.MeetingExample;
import com.chainup.service.SqlMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author qinxuan
*/
public interface MeetingMapper extends SqlMapper {
    int countByExample(MeetingExample example);

    int deleteByExample(MeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Meeting record);

    int insertSelective(Meeting record);

    List<Meeting> selectByExample(MeetingExample example);

    Meeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByExample(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByPrimaryKeySelective(Meeting record);

    int updateByPrimaryKey(Meeting record);
}