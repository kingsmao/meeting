package com.chainup.dao;

import com.chainup.entity.Meeting;
import com.chainup.entity.MeetingExample;
import com.chainup.service.SqlMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiLi
 */
public interface MeetingMapper extends SqlMapper {
    int countByExample(MeetingExample example);

    int deleteByExample(MeetingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Meeting record);

    int insertSelective(Meeting record);


    List<Meeting> selectByExample(MeetingExample example);

    Meeting selectOne(MeetingExample example);

    Meeting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByExample(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByPrimaryKeySelective(Meeting record);

    int updateByPrimaryKey(Meeting record);

    List<Meeting> findByUserId(@Param("userId") Integer userId);


    Meeting afterBeginTimeBeforeEndTime(int roomId, String beginTime);


    Meeting betweenBeginTimeAndEndTime(int roomId, String beginTime, String endTime);

    Meeting beforeBeginTimeAfterEndTime(int roomId, String endTime);

    Meeting containsBeginTimeAndEndTime(int roomId, String beginTime, String endTime);
}