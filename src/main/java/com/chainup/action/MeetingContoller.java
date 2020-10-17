package com.chainup.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lili
 * @date 2020/10/17 14:18
 * @see
 * @since
 */
@Slf4j
@RestController
public class MeetingContoller {

    /**
     * 我的预定会议室
     *
     * @return
     */
    @GetMapping("/myMeeting")
    public String myMeeting() {
        return null;
    }

    /**
     * 会议室信息：展示给客户端，会议室名字，会议部门等信息
     *
     * @return
     */
    @GetMapping("/meetingInfo")
    public String meetingInfo() {
        return null;
    }

    /**
     * 预定会议室
     *
     * @return
     */
    @PostMapping("/reserveMeetingRoom")
    public String reserveMeetingRoom() {
        return null;
    }

    /**
     * 取消自己预定的会议室
     *
     * @return
     */
    @PostMapping("/cancelMeetingRoom")
    public String cancelMeetingRoom() {
        return null;
    }

    /**
     * 通过时间查找可用会议室
     *
     * @return
     */
    @GetMapping("/findAvailableMeetingRoomByTime")
    public String availableMeetingRoom() {
        return null;
    }


    /**
     * 已经预定的所有会议室，大于当前时间
     *
     * @return
     */
    @GetMapping("/allReservedMeetingRoom")
    public String allReservedMeetingRoom() {
        return null;
    }


}
