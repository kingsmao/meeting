package com.chainup.service.impl;

import com.chainup.core.dto.MeetingDto;
import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.enums.MeetingStatus;
import com.chainup.dao.DepartmentMapper;
import com.chainup.dao.MeetingMapper;
import com.chainup.dao.RoomMapper;
import com.chainup.dao.UserMapper;
import com.chainup.entity.*;
import com.chainup.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QX
 * @date 2020/10/13
 */
@Service
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
    public List<Meeting> findAll(MeetingExample example) {
        return meetingMapper.selectByExample(example);
    }

    @Override
    public List<MeetingRoomDto> availableRoomByTime(String beginTime, String endTime) {
        List<Room> rooms = roomMapper.selectByExample(new RoomExample());
        List<MeetingRoomDto> results = new ArrayList<>();
        for (Room room : rooms) {
            Integer id = room.getId();
            MeetingRoomDto roomDto = MeetingRoomDto.builder().roomId(id.toString()).roomName(room.getName()).description(room.getDescription()).build();
            MeetingExample example = new MeetingExample();
            //查找房间今天的会议室预定
            example.createCriteria().andRoomIdEqualTo(id);
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
            roomDto.setMeetingList(meetingDtos);
            results.add(roomDto);
        }
        return results;
    }
}
