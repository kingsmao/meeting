package com.chainup.service.impl;

import com.chainup.core.dto.MeetingDto;
import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MyMeetingRoomDto;
import com.chainup.core.enums.MeetingStatus;
import com.chainup.core.params.ReserveMeetingParams;
import com.chainup.dao.DepartmentMapper;
import com.chainup.dao.MeetingMapper;
import com.chainup.dao.RoomMapper;
import com.chainup.dao.UserMapper;
import com.chainup.entity.*;
import com.chainup.service.MeetingService;
import com.chainup.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author QX
 * @date 2020/10/13
 */
@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MeetingMapper meetingMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public List<MeetingRoomDto> availableRoomByTime(String beginTime, String endTime) {
        List<Room> rooms = roomMapper.selectByExample(new RoomExample());
        List<MeetingRoomDto> results = new ArrayList<>();
        for (Room room : rooms) {
            Integer id = room.getId();
            MeetingRoomDto roomDto = MeetingRoomDto.builder().roomId(id.toString()).roomName(room.getName()).description(room.getDescription()).build();
            MeetingExample example = new MeetingExample();
            //查找房间今天的会议室预定
            Date dateStart = DateUtil.parse(beginTime);
            Date dateEnd = DateUtil.parse(endTime);
            example.createCriteria().andRoomIdEqualTo(id).
                    andBeginTimeGreaterThanOrEqualTo(dateStart).andEndTimeLessThanOrEqualTo(dateEnd);
            List<Meeting> meetings = meetingMapper.selectByExample(example);
//            if (CollectionUtils.isNotEmpty(meetings)) {
//                //该房间被预定了，直接跳过
//                continue;
//            }
            List<MeetingDto> meetingDtos = new ArrayList<>();
            for (Meeting meeting : meetings) {
                Integer departmentId = meeting.getDepartmentId();
                Department department = departmentMapper.selectByPrimaryKey(departmentId);
                meetingDtos.add(MeetingDto.builder()
                        .meetingId(meeting.getId().toString())
                        .beginTime(meeting.getBeginTime().toString())
                        .endTime(meeting.getEndTime().toString())
                        .meetingName(meeting.getName())
                        .departmentName(department.getName())
                        .status(meeting.getStatus().toString())
                        .statusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()))
                        .meetingName(meeting.getName())
                        .userName(meeting.getName()).build());
            }
            roomDto.setMeetingList(meetingDtos);
            results.add(roomDto);
        }
        return results;
    }

    @Override
    public List<MyMeetingRoomDto> getMyMeetingList(String openId) {
        User user = userMapper.findUserByOpenId(openId);
        if (Objects.isNull(user)) {
            return Collections.emptyList();
        }
        Integer userId = user.getId();
        List<Meeting> meetings = meetingMapper.findByUserId(userId);
        if (CollectionUtils.isEmpty(meetings)) {
            return Collections.emptyList();
        }
        List<MyMeetingRoomDto> myMeetingRoomDtos = new ArrayList<>();
        for (Meeting meeting : meetings) {
            Integer roomId = meeting.getRoomId();
            Integer departmentId = meeting.getDepartmentId();
            Room room = roomMapper.selectByPrimaryKey(roomId);
            Department department = departmentMapper.selectByPrimaryKey(departmentId);
            MyMeetingRoomDto myMeetingRoomDto = new MyMeetingRoomDto();
            myMeetingRoomDto.setDepartmentName(department.getName());
            myMeetingRoomDto.setRoomId(roomId);
            myMeetingRoomDto.setRoomName(room.getName());
            myMeetingRoomDto.setDescription(meeting.getDepartmentId().toString());
            myMeetingRoomDto.setMeetingName(meeting.getName());
            myMeetingRoomDto.setBeginTime(meeting.getBeginTime().toString());
            myMeetingRoomDto.setEndTime(meeting.getEndTime().toString());
            myMeetingRoomDto.setMeetingId(meeting.getId());
            myMeetingRoomDto.setStatusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()));
            myMeetingRoomDtos.add(myMeetingRoomDto);
        }
        return myMeetingRoomDtos;
    }

    @Override
    public void reserveMeetingRoom(ReserveMeetingParams reserveMeetingParams) {
        Meeting meeting = new Meeting();
        String openId = reserveMeetingParams.getOpenId();
        User user = userMapper.findUserByOpenId(openId);
        if (Objects.isNull(user)) {
            log.warn("user not exist: openId:{}, reserveMeetingParams:{}", openId, reserveMeetingParams);
            return;
        }
        meeting.setName(reserveMeetingParams.getMeetingName());
        meeting.setUserId(user.getId());
        meeting.setBeginTime(DateUtil.parse(reserveMeetingParams.getBeginTime()));
        meeting.setEndTime(DateUtil.parse(reserveMeetingParams.getEndTime()));
        meeting.setRoomId(reserveMeetingParams.getRoomId());
        meeting.setCtime(new Date());
        meeting.setMtime(new Date());
        meeting.setDepartmentId(reserveMeetingParams.getDepartmentId());
        meeting.setStatus(MeetingStatus.NOT_START.byteStatus());
        meetingMapper.insertSelective(meeting);
    }

    @Override
    public MyMeetingRoomDto getMeetingRoomInfo(String beginTime, String endTime, int roomId, String openId) {
        MyMeetingRoomDto myMeetingRoomDto = new MyMeetingRoomDto();
        Room room = roomMapper.selectByPrimaryKey(roomId);
        if (Objects.isNull(room)) {
            return myMeetingRoomDto;
        }
        User user = userMapper.findUserByOpenId(openId);
        if (Objects.isNull(user)) {
            log.warn("user not exist: openId:{}", openId);
            return myMeetingRoomDto;
        }
        myMeetingRoomDto.setBeginTime(beginTime);
        myMeetingRoomDto.setEndTime(endTime);
        myMeetingRoomDto.setRoomId(roomId);
        myMeetingRoomDto.setRoomName(room.getName());
        myMeetingRoomDto.setUserName(user.getNickName());
        MeetingExample example = new MeetingExample();
        //查找房间今天的会议室预定 todo beginTime endTime
        Date dateStart = DateUtil.parse(beginTime);
        Date dateEnd = DateUtil.parse(endTime);
        example.createCriteria().andRoomIdEqualTo(roomId).
                andBeginTimeGreaterThanOrEqualTo(dateStart).andEndTimeLessThanOrEqualTo(dateEnd);
        List<Meeting> meetings = meetingMapper.selectByExample(example);
        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : meetings) {
            Integer departmentId = meeting.getDepartmentId();
            Department department = departmentMapper.selectByPrimaryKey(departmentId);
            meetingDtos.add(MeetingDto.builder()
                    .meetingId(meeting.getId().toString())
                    .beginTime(meeting.getBeginTime().toString())
                    .endTime(meeting.getEndTime().toString())
                    .meetingName(meeting.getName())
                    .departmentName(department.getName())
                    .status(meeting.getStatus().toString())
                    .statusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()))
                    .meetingName(meeting.getName())
                    .userName(meeting.getName()).build());
        }
        myMeetingRoomDto.setMeetingList(meetingDtos);
        List<Department> departments = departmentMapper.selectByExample(new DepartmentExample());
        myMeetingRoomDto.setDepartmentList(departments);
        return myMeetingRoomDto;
    }

    @Override
    public MyMeetingRoomDto myMeetingDetail(int meetingId) {
        MyMeetingRoomDto myMeetingRoomDto = new MyMeetingRoomDto();
        Meeting meeting = meetingMapper.selectByPrimaryKey(meetingId);
        if (Objects.isNull(meeting)) {
            log.warn("meeting not exist,id:{}", meetingId);
            return myMeetingRoomDto;
        }
        myMeetingRoomDto.setBeginTime(meeting.getBeginTime().toString());
        myMeetingRoomDto.setEndTime(meeting.getEndTime().toString());
        myMeetingRoomDto.setMeetingName(meeting.getName());
        Integer roomId = meeting.getRoomId();
        Room room = roomMapper.selectByPrimaryKey(roomId);
        myMeetingRoomDto.setRoomId(roomId);
        myMeetingRoomDto.setRoomName(room.getName());
        Integer departmentId = meeting.getDepartmentId();
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        myMeetingRoomDto.setDepartmentName(department.getName());
        Integer userId = meeting.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        myMeetingRoomDto.setUserName(user.getUserName());
        return myMeetingRoomDto;
    }

    @Override
    public void cancelMeetingRoom(int meetingId) {
        meetingMapper.deleteByPrimaryKey(meetingId);
    }
}
