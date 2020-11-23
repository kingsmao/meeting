package com.chainup.service;

import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MeetingRoomReserveDto;
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

/**
 * @author lili
 * @date 2020/10/21 23:33
 * @see
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class MeetingServiceTest {

    private static final String CHEN_ZHI_QI = "oFmyS4jjyiihYDmTWql5hEdTIjqA";
    private static final String LI_LI = "oFmyS4vDVqBmLg-31AeJJm8AuzCY";
    private static final String TEST_USER = "oA-SP4lOq6GOeJp_5OoqyhSjzGBI";

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
        List<MyMeetingRoomDto> myMeetingList = meetingService.getMyMeetingList(TEST_USER);
        System.out.println(myMeetingList);
    }

    @Test
    void reserveMeetingRoom() {
        ReserveMeetingParams params = new ReserveMeetingParams();
        params.setMeetingName("Chainp年度复盘");
        params.setBeginTime("2020-10-22 17:00:00");
        params.setEndTime("2020-10-22 19:00:00");
        params.setOpenId(CHEN_ZHI_QI);
        params.setDepartmentId(3);
        params.setRoomId(2);
        meetingService.reserveMeetingRoom(params);
    }

    @Test
    void availableRoomByTime() {
        List<MeetingRoomDto> meetingRoomDtos = meetingService.availableRoomByTime("2020-10-20",
                "11:00:00", "17:00:00",1);
        System.out.println(meetingRoomDtos);
    }

    @Test
    void getMeetingRoomInfo() {
        MeetingRoomReserveDto reserveDto = meetingService.getMeetingRoomInfo("2020-10-20",
                "11:00:00", "17:00:00", 1, "oFmyS4vDVqBmLg-31AeJJm8AuzCY");
        System.out.println(reserveDto);
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