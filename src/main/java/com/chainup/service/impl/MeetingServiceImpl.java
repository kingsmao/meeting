package com.chainup.service.impl;

import com.chainup.core.dto.MeetingDto;
import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MeetingRoomReserveDto;
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
import com.google.common.base.Splitter;
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
    public List<MeetingRoomDto> availableRoomByTime(String date, String beginTime, String endTime) {
        List<Room> rooms = roomMapper.selectByExample(new RoomExample());
        List<MeetingRoomDto> results = new ArrayList<>();
        for (Room room : rooms) {
            Integer id = room.getId();
            String description = room.getDescription();
            MeetingRoomDto roomDto = MeetingRoomDto.builder()
                    .roomId(id.toString())
                    .roomName(room.getName())
                    .description(description)
                    .roomPersonCount(Splitter.on("|").splitToList(description).get(0)).build();
            MeetingExample example = new MeetingExample();
            //查找房间今天的会议室预定
            Date dateStart = DateUtil.parse(date + " 00:00:00");
            Date dateEnd = DateUtil.parse(date + " 23:59:59");
            example.createCriteria().andRoomIdEqualTo(id).
                    andBeginTimeGreaterThanOrEqualTo(dateStart).andEndTimeLessThanOrEqualTo(dateEnd);
            List<Meeting> meetings = meetingMapper.selectByExample(example);
            List<MeetingDto> meetingDtos = new ArrayList<>();
            for (Meeting meeting : meetings) {
                Integer departmentId = meeting.getDepartmentId();
                Department department = departmentMapper.selectByPrimaryKey(departmentId);
                meetingDtos.add(MeetingDto.builder()
                        .meetingId(meeting.getId().toString())
                        .beginTime(meeting.getBeginTime().getTime())
                        .endTime(meeting.getEndTime().getTime())
                        .meetingName(meeting.getName())
                        .timeRange(DateUtil.timeRange(meeting.getBeginTime(), meeting.getEndTime()))
                        .meetingSubject(department.getName() + "  " + meeting.getName())
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
            if (Objects.isNull(department) || Objects.isNull(room)) {
                continue;
            }
            MyMeetingRoomDto myMeetingRoomDto = new MyMeetingRoomDto();
            myMeetingRoomDto.setMeetingSubject(department.getName() + "  " + meeting.getName());
            myMeetingRoomDto.setNickName(user.getUserName());
            myMeetingRoomDto.setUserName(user.getNickName());
            myMeetingRoomDto.setDepartmentName(department.getName());
            myMeetingRoomDto.setDateTimeRange(DateUtil.timeDateRange(meeting.getBeginTime(), meeting.getEndTime()));
            myMeetingRoomDto.setRoomId(roomId);
            myMeetingRoomDto.setRoomName(room.getName());
            myMeetingRoomDto.setDescription(meeting.getDepartmentId().toString());
            myMeetingRoomDto.setMeetingName(meeting.getName());
            myMeetingRoomDto.setDate(DateUtil.dateWithPattern(meeting.getBeginTime(), "yyyy-MM-dd"));
            myMeetingRoomDto.setBeginTime(DateUtil.dateWithPattern(meeting.getBeginTime(), "HH:mm"));
            myMeetingRoomDto.setEndTime(DateUtil.dateWithPattern(meeting.getEndTime(), "HH:mm"));
            myMeetingRoomDto.setBeginTimeStamp(meeting.getBeginTime().getTime());
            myMeetingRoomDto.setMeetingId(meeting.getId());
            myMeetingRoomDto.setStatusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()));
            myMeetingRoomDtos.add(myMeetingRoomDto);
        }
        if (CollectionUtils.isNotEmpty(myMeetingRoomDtos)) {
            myMeetingRoomDtos.sort((x1, x2) -> (int) (x2.getBeginTimeStamp() - x1.getBeginTimeStamp()));
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
        user.setUserName(reserveMeetingParams.getUserName());
        userMapper.updateByPrimaryKey(user);
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
    public MeetingRoomReserveDto getMeetingRoomInfo(String date, String beginTime, String endTime, int roomId, String openId) {
        MeetingRoomReserveDto reserveDto = new MeetingRoomReserveDto();
        Room room = roomMapper.selectByPrimaryKey(roomId);
        if (Objects.isNull(room)) {
            return reserveDto;
        }
        User user = userMapper.findUserByOpenId(openId);
        if (Objects.isNull(user)) {
            log.warn("user not exist: openId:{}", openId);
            return reserveDto;
        }
        reserveDto.setDate(date);
        reserveDto.setBeginTime(beginTime);
        reserveDto.setEndTime(endTime);
        reserveDto.setRoomId(roomId);
        reserveDto.setRoomName(room.getName());
        reserveDto.setUserName(user.getNickName());
        MeetingExample example = new MeetingExample();
        //显示当天所有会议室
        Date dateStart = DateUtil.parse(date + " 00:00:00");
        Date dateEnd = DateUtil.parse(date + " 23:59:00");
        example.createCriteria().andRoomIdEqualTo(roomId).
                andBeginTimeGreaterThanOrEqualTo(dateStart).andEndTimeLessThanOrEqualTo(dateEnd);
        List<Meeting> meetings = meetingMapper.selectByExample(example);
        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : meetings) {
            Integer departmentId = meeting.getDepartmentId();
            Department department = departmentMapper.selectByPrimaryKey(departmentId);
            meetingDtos.add(MeetingDto.builder()
                    .meetingId(meeting.getId().toString())
                    .beginTime(meeting.getBeginTime().getTime())
                    .endTime(meeting.getEndTime().getTime())
                    .timeRange(DateUtil.timeRange(meeting.getBeginTime(), meeting.getEndTime()))
                    .meetingName(meeting.getName())
                    .meetingSubject(department.getName() + "  " + meeting.getName())
                    .departmentName(department.getName())
                    .status(meeting.getStatus().toString())
                    .statusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()))
                    .meetingName(meeting.getName())
                    .userName(meeting.getName()).build());
        }
        reserveDto.setMeetingList(meetingDtos);
        List<Department> departments = departmentMapper.selectByExample(new DepartmentExample());
        reserveDto.setDepartmentList(departments);
        return reserveDto;
    }

    @Override
    public MyMeetingRoomDto myMeetingDetail(int meetingId) {
        MyMeetingRoomDto myMeetingRoomDto = new MyMeetingRoomDto();
        Meeting meeting = meetingMapper.selectByPrimaryKey(meetingId);
        if (Objects.isNull(meeting)) {
            log.warn("meeting not exist,id:{}", meetingId);
            return myMeetingRoomDto;
        }
        myMeetingRoomDto.setStatus(meeting.getStatus().toString());
        myMeetingRoomDto.setStatusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()));
        myMeetingRoomDto.setMeetingId(meetingId);
        myMeetingRoomDto.setBeginTime(meeting.getBeginTime().toString());
        myMeetingRoomDto.setEndTime(meeting.getEndTime().toString());
        if (meeting.getBeginTime().getTime() <= System.currentTimeMillis()) {
            //只是没开始才开始删除
            myMeetingRoomDto.setCanDelete(false);
        }
        myMeetingRoomDto.setDateTimeRange(DateUtil.timeDateRange(meeting.getBeginTime(), meeting.getEndTime()));
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
        myMeetingRoomDto.setNickName(user.getNickName());
        return myMeetingRoomDto;
    }

    @Override
    public void cancelMeetingRoom(int meetingId) {
        meetingMapper.deleteByPrimaryKey(meetingId);
    }

    @Override
    public void invalidMeetingTime() {
        List<Meeting> meetings = meetingMapper.selectByExample(new MeetingExample());
        if (CollectionUtils.isNotEmpty(meetings)) {
            for (Meeting meeting : meetings) {
                long startTime = meeting.getBeginTime().getTime();
                long endTime = meeting.getEndTime().getTime();
                long now = System.currentTimeMillis();
                if (endTime <= now) {
                    meeting.setStatus(MeetingStatus.FINISHED.byteStatus());
                }
                if (startTime <= now && endTime >= now) {
                    meeting.setStatus(MeetingStatus.RUNNING.byteStatus());
                }
                meetingMapper.updateByPrimaryKey(meeting);
            }
        }
    }
}
