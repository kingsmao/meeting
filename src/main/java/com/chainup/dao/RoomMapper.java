package com.chainup.dao;

import com.chainup.entity.Room;
import com.chainup.entity.RoomExample;
import java.util.List;

import com.chainup.service.SqlMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author LiLi
*/
public interface RoomMapper extends SqlMapper {
    int countByExample(RoomExample example);

    int deleteByExample(RoomExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Room record);

    int insertSelective(Room record);


    List<Room> selectByExample(RoomExample example);

    Room selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Room record, @Param("example") RoomExample example);

    int updateByExample(@Param("record") Room record, @Param("example") RoomExample example);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
}