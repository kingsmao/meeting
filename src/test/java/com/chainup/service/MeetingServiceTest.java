package com.chainup.service;

import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MyMeetingRoomDto;
import com.chainup.core.params.ReserveMeetingParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lili
 * @date 2020/10/21 23:33
 * @see
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class MeetingServiceTest {

    @Autowired
    private MeetingService meetingService;

    private static final String LiLi_OPEN_ID = "oFmyS4vDVqBmLg-31AeJJm8AuzCY";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMyMeetingList() {
        List<MyMeetingRoomDto> myMeetingList = meetingService.getMyMeetingList(LiLi_OPEN_ID);
        System.out.println(myMeetingList);
    }

    @Test
    void reserveMeetingRoom() {
        ReserveMeetingParams params = new ReserveMeetingParams();
        params.setMeetingName("alibaba 年度复盘");
        params.setBeginTime("2020-10-20 14:00:00");
        params.setEndTime("2020-10-20 15:00:00");
        params.setOpenId("oFmyS4vDVqBmLg-31AeJJm8AuzCY");
        params.setDepartmentId(1);
        params.setRoomId(1);
        meetingService.reserveMeetingRoom(params);
    }

    @Test
    void availableRoomByTime() {
        List<MeetingRoomDto> meetingRoomDtos = meetingService.availableRoomByTime("2020-10-20 11:00:00", "2020-10-20 17:00:00");
        System.out.println(meetingRoomDtos);
    }

    @Test
    void getMeetingRoomInfo() {
        MyMeetingRoomDto meetingRoomInfo = meetingService.getMeetingRoomInfo("2020-10-20 11:00:00", "2020-10-20 17:00:00", 1, "oFmyS4vDVqBmLg-31AeJJm8AuzCY");
        System.out.println(meetingRoomInfo);
    }


    @Test
    void myMeetingDetail() {
        MyMeetingRoomDto myMeetingRoomDto = meetingService.myMeetingDetail(1);
        System.out.println(myMeetingRoomDto);
    }

    @Test
    void cancelMeetingRoom() {
        meetingService.cancelMeetingRoom(2);
    }
}