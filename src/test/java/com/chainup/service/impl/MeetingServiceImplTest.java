package com.chainup.service.impl;

import com.chainup.core.params.ReserveMeetingParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lili
 * @date 2020/11/22 1:32
 * @see
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class MeetingServiceImplTest {

    @Autowired
    private MeetingServiceImpl meetingService;

    @Test
    void isMeetingTimeConflict() {
        int roomId = 2;
        String beginTime = "2020-11-22 10:10:00";
        String endTime = "2020-11-22 10:30:00";
        String openId = "oA-SP4lKKdGpqEaEs3Rnz_Bo58LM";
        //10:10-10:30,那么冲突时间是10:00-10:20 10:15-10:25 10:15-10:40 10:00-11:00有会
        meetingService.deleteUserAllMeeting(openId);
        Assertions.assertFalse(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));


        ReserveMeetingParams reserveMeetingParams = ReserveMeetingParams.builder().
                beginTime("2020-11-22 10:00:00").endTime("2020-11-22 10:20:00").openId(openId)
                .roomId(roomId).departmentId(1).departmentName("saas").userName("lili").meetingName("单元测试").roomName("").delaySwitch(false).build();
        meetingService.reserveMeetingRoom(reserveMeetingParams);
        Assertions.assertTrue(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));
        meetingService.deleteUserAllMeeting(openId);
        Assertions.assertFalse(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));

        reserveMeetingParams.setBeginTime("2020-11-22 10:15:00");
        reserveMeetingParams.setEndTime("2020-11-22 10:25:00");
        meetingService.reserveMeetingRoom(reserveMeetingParams);
        Assertions.assertTrue(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));
        meetingService.deleteUserAllMeeting(openId);
        Assertions.assertFalse(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));


        reserveMeetingParams.setBeginTime("2020-11-22 10:15:00");
        reserveMeetingParams.setEndTime("2020-11-22 10:40:00");
        meetingService.reserveMeetingRoom(reserveMeetingParams);
        Assertions.assertTrue(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));
        meetingService.deleteUserAllMeeting(openId);
        Assertions.assertFalse(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));


        reserveMeetingParams.setBeginTime("2020-11-22 10:00:00");
        reserveMeetingParams.setEndTime("2020-11-22 11:00:00");
        meetingService.reserveMeetingRoom(reserveMeetingParams);
        Assertions.assertTrue(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));
        meetingService.deleteUserAllMeeting(openId);
        Assertions.assertFalse(meetingService.isMeetingTimeConflict(roomId, beginTime, endTime, false));


    }
}