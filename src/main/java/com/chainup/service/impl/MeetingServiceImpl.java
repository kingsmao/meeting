package com.chainup.service.impl;

import com.chainup.core.config.ExceptionCode;
import com.chainup.core.dto.MeetingDto;
import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MeetingRoomReserveDto;
import com.chainup.core.dto.MyMeetingRoomDto;
import com.chainup.core.enums.MeetingStatus;
import com.chainup.core.enums.Workplace;
import com.chainup.core.params.ReserveMeetingParams;
import com.chainup.dao.DepartmentMapper;
import com.chainup.dao.MeetingMapper;
import com.chainup.dao.RoomMapper;
import com.chainup.dao.UserMapper;
import com.chainup.entity.*;
import com.chainup.service.MeetingService;
import com.chainup.utils.CoreUrl;
import com.chainup.utils.DateUtil;
import com.chainup.wechat.CoreApi;
import com.google.common.annotations.VisibleForTesting;
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
                    .workSpace(Workplace.descriptionByType(room.getWorkplace()))
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
                if (Objects.isNull(department)) {
                    continue;
                }
                Integer userId = meeting.getUserId();
                User user = userMapper.selectByPrimaryKey(userId);
                String userName = "";
                if (Objects.nonNull(user)) {
                    userName = user.getUserName();
                }
                meetingDtos.add(MeetingDto.builder()
                        .meetingId(meeting.getId().toString())
                        .beginTime(meeting.getBeginTime().getTime())
                        .endTime(meeting.getEndTime().getTime())
                        .meetingName(meeting.getName())
                        .timeRange(DateUtil.timeRange(meeting.getBeginTime(), meeting.getEndTime()))
                        .meetingSubject(department.getName() + " " + meeting.getName() + " " + userName)
                        .departmentName(department.getName())
                        .status(meeting.getStatus().toString())
                        .statusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()))
                        .meetingName(meeting.getName())
                        .userName(meeting.getName()).build());
            }
            roomDto.setMeetingList(meetingDtos);
            results.add(roomDto);
        }
        if (CollectionUtils.isNotEmpty(results)) {
            results.sort(Comparator.comparingInt(dto -> dto.getMeetingList().size()));
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
            myMeetingRoomDto.setWorkSpace(Workplace.descriptionByType(room.getWorkplace()));
            myMeetingRoomDto.setDescription(meeting.getDepartmentId().toString());
            myMeetingRoomDto.setMeetingName(meeting.getName());
            myMeetingRoomDto.setDate(DateUtil.dateWithPattern(meeting.getBeginTime(), "yyyy-MM-dd"));
            myMeetingRoomDto.setBeginTime(DateUtil.dateWithPattern(meeting.getBeginTime(), "HH:mm"));
            myMeetingRoomDto.setEndTime(DateUtil.dateWithPattern(meeting.getEndTime(), "HH:mm"));
            myMeetingRoomDto.setBeginTimeStamp(meeting.getBeginTime().getTime());
            myMeetingRoomDto.setMeetingId(meeting.getId());
            myMeetingRoomDto.setStatus(meeting.getStatus().toString());
            myMeetingRoomDto.setStatusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()));
            myMeetingRoomDtos.add(myMeetingRoomDto);
        }
        if (CollectionUtils.isNotEmpty(myMeetingRoomDtos)) {
            //按照开始时间排序，最新的在前面
            myMeetingRoomDtos.sort((x1, x2) -> (int) (x2.getBeginTimeStamp() - x1.getBeginTimeStamp()));
        }
        return myMeetingRoomDtos;
    }

    @Override
    public int reserveMeetingRoom(ReserveMeetingParams reserveMeetingParams) {
        log.info(reserveMeetingParams.toString());
        if (isMeetingTimeConflict(reserveMeetingParams.getRoomId(),
                reserveMeetingParams.getBeginTime(),
                reserveMeetingParams.getEndTime(),
                reserveMeetingParams.getDelaySwitch())) {
            return ExceptionCode.MEETING_CONFLICT.getCode();
        }

        //todo 顺延四周开关
        Meeting meeting = new Meeting();
        String openId = reserveMeetingParams.getOpenId();
        User user = userMapper.findUserByOpenId(openId);
        if (Objects.isNull(user)) {
            log.warn("user not exist: openId:{}, reserveMeetingParams:{}", openId, reserveMeetingParams);
            return ExceptionCode.USER_NOT_EXIST.getCode();
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
        return ExceptionCode.SUCCESS.getCode();
    }


    /**
     * 预定会议室时间是否冲突：有四种情况算是冲突
     * 如果预定时间是10:10-10:30,那么冲突时间是10:00-10:20 10:15-10:25 10:15-10:40 10:00-11:00有会
     *
     * @param roomId      房间id
     * @param beginTime   预定开始时间
     * @param endTime     预定结束时间
     * @param delaySwitch 顺延开关
     * @return true 预定会议时间有冲突 fasle 时间没有冲突
     */
    @VisibleForTesting
    boolean isMeetingTimeConflict(int roomId, String beginTime, String endTime, boolean delaySwitch) {
        Date beginDate = DateUtil.parse(beginTime);
        Date endDate = DateUtil.parse(endTime);
        //10:00-10:20
        MeetingExample example1 = new MeetingExample();
        example1.createCriteria().andRoomIdEqualTo(roomId).andBeginTimeLessThan(beginDate).andEndTimeGreaterThan(beginDate);
        boolean hasConflictCase1 = Objects.nonNull(meetingMapper.selectOne(example1));
        //10:15-10:25
        MeetingExample example2 = new MeetingExample();
        example2.createCriteria().andRoomIdEqualTo(roomId).andBeginTimeGreaterThan(beginDate).andEndTimeLessThan(endDate);
        boolean hasConflictCase2 = Objects.nonNull(meetingMapper.selectOne(example2));
        //10:15-10:40
        MeetingExample example3 = new MeetingExample();
        example3.createCriteria().andRoomIdEqualTo(roomId).andBeginTimeLessThan(endDate).andEndTimeGreaterThan(endDate);
        boolean hasConflictCase3 = Objects.nonNull(meetingMapper.selectOne(example3));
        //10:00-11:00
        MeetingExample example4 = new MeetingExample();
        example4.createCriteria().andRoomIdEqualTo(roomId).andBeginTimeLessThan(beginDate).andEndTimeGreaterThan(endDate);
        boolean hasConflictCase4 = Objects.nonNull(meetingMapper.selectOne(example4));
        return hasConflictCase1 || hasConflictCase2 || hasConflictCase3 || hasConflictCase4;
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
        reserveDto.setRoomPersonCount(Splitter.on("|").splitToList(room.getDescription()).get(0));
        reserveDto.setWorkSpace(Workplace.descriptionByType(room.getWorkplace()));
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
                    .meetingSubject(department.getName() + " " + meeting.getName() + " " + user.getUserName())
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
        myMeetingRoomDto.setDate(DateUtil.dateWithPattern(meeting.getBeginTime(), "yyyy-MM-dd"));
        myMeetingRoomDto.setBeginTime(DateUtil.dateWithPattern(meeting.getBeginTime(), "HH:mm"));
        myMeetingRoomDto.setEndTime(DateUtil.dateWithPattern(meeting.getEndTime(), "HH:mm"));
        myMeetingRoomDto.setStatus(meeting.getStatus().toString());
        myMeetingRoomDto.setStatusMsg(MeetingStatus.descriptionByStatus(meeting.getStatus()));
        myMeetingRoomDto.setMeetingId(meetingId);
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
        MeetingExample example = new MeetingExample();
        // 结束的就不扫描了
        example.createCriteria().andStatusNotEqualTo(MeetingStatus.FINISHED.byteStatus());
        List<Meeting> meetings = meetingMapper.selectByExample(example);
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

    @Override
    public void remindMeeting() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("+++++++++++++开始拉取订阅通知+++++++++++++++++++");
        MeetingExample example = new MeetingExample();
        // 结束的就不扫描了
        example.createCriteria().andStatusEqualTo(MeetingStatus.NOT_START.byteStatus());
        List<Meeting> meetings = meetingMapper.selectByExample(example);
        meetings.forEach(meeting -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(meeting.getBeginTime());
            cal.add(Calendar.MINUTE, 4);
            long remindLate = cal.getTime().getTime();
            cal.add(Calendar.MINUTE, 2);
            long remindEarly = cal.getTime().getTime();
            long now = System.currentTimeMillis();

            if (now >= remindEarly && now < remindLate) {
                User user = userMapper.selectByPrimaryKey(meeting.getUserId());
                Room room = roomMapper.selectByPrimaryKey(meeting.getRoomId());
                Department department = departmentMapper.selectByPrimaryKey(meeting.getDepartmentId());
                MeetingDto meetingDto = MeetingDto.builder().openId(user.getOpenId())
                        .meetingSubject(meeting.getName())
                        .roomName(room.getName())
                        .timeRange("今天 " + DateUtil.date2StrHourMin(meeting.getBeginTime()) + " - " + DateUtil.date2StrHourMin(meeting.getEndTime()))
                        .departmentName(department.getDescription())
                        .userName(user.getUserName()).build();
                CoreApi.sendTemplateMessage(makeUpsubscribeMessage(meetingDto));
            }
        });
    }

    public String makeUpsubscribeMessage(MeetingDto meetingDto) {
        return "{\n" +
                "\t\"touser\": " + meetingDto.getOpenId() + ",\n" +
                "\t\"template_id\": " + CoreUrl.getSubscribeMessageTemplateId() + ",\n" +
                "\t\"miniprogram_state\": \"developer\",\n" +
                "\t\"lang\": \"zh_CN\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"thing1\": {\n" +
                "\t\t\t\"value\": " + meetingDto.getMeetingSubject() + "\n" +
                "\t\t},\n" +
                "\t\t\"thing2\": {\n" +
                "\t\t\t\"value\": " + meetingDto.getRoomName() + "\n" +
                "\t\t},\n" +
                "\t\t\"character_string3\": {\n" +
                "\t\t\t\"value\": " + meetingDto.getTimeRange() + "\n" +
                "\t\t},\n" +
                "\t\t\"thing5\": {\n" +
                "\t\t\t\"value\": " + meetingDto.getDepartmentName() + "\n" +
                "\t\t},\n" +
                "\t\t\"thing4\": {\n" +
                "\t\t\t\"value\": " + meetingDto.getUserName() + "\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    void deleteUserAllMeeting(String openId) {
        User user = userMapper.findUserByOpenId(openId);
        Integer id = user.getId();
        MeetingExample example = new MeetingExample();
        example.createCriteria().andUserIdEqualTo(id);
        meetingMapper.deleteByExample(example);
    }
}
