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
     * 1.通过时间查找可用会议室
     *
     * @return
     */
    @GetMapping("/availableRoomByTime")
    public String availableRoomByTime() {
        return null;
    }


    /**
     * 2.通过时间+room_id查找会议室及预定详情
     *
     * @return 会议室详情 + 此会议室预定meeting记录
     *         部门下拉列表
     *         参数 时间+room_id传回去
     */
    @GetMapping("/meetingRoomInfoAdd")
    public String meetingRoomInfoAdd() {
        return null;
    }


    /**
     * 3.预定会议室
     *
     * @return
     */
    @PostMapping("/reserveMeetingRoom")
    public String reserveMeetingRoom() {
        return null;
    }



    /**
     * 4.我的预定会议室
     * @param  open_id
     *
     * @return
     */
    @GetMapping("/myMeeting")
    public String myMeeting(String open_id) {
        return null;
    }



    /**
     * 5.通过meeting_id查找会议室及预定详情
     *
     * @return 会议室详情 + 此会议室预定meeting记录
     *         部门下拉列表
     */
    @GetMapping("/meetingInfoEdit")
    public String meetingInfoEdit() {
        return null;
    }


    /**
     * 6.取消自己预定的会议室
     *
     * @return
     */
    @PostMapping("/cancelMeetingRoom")
    public String cancelMeetingRoom() {
        return null;
    }


}
