package com.chainup.action;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.chainup.config.WebConstants.API_STATUS_DEVELOPING;

/**
 * @author lili
 * @date 2020/10/17 14:18
 * @see
 * @since
 */
@Slf4j
@RestController
@Api(value = "MeetingContoller", description = "会议室API 0/6")
public class MeetingContoller {
    /**
     * 1.通过时间查找可用会议室
     *
     * @return
     */
    @ApiOperation(value = "通过时间查找可用会议室" + API_STATUS_DEVELOPING, httpMethod = "GET")
    @GetMapping("/availableRoomByTime")
    public String availableRoomByTime() {
        return null;
    }


    /**
     * 2.通过时间+room_id查找会议室及预定详情
     *
     * @return 会议室详情 + 此会议室预定meeting记录
     * 部门下拉列表
     * 参数 时间+room_id传回去
     */
    @ApiOperation(value = "会议室预定前的详情" + API_STATUS_DEVELOPING, httpMethod = "GET")
    @GetMapping("/meetingRoomInfo")
    public String meetingRoomInfo() {
        return null;
    }


    /**
     * 3.预定会议室
     *
     * @return
     */
    @PostMapping("/reserveMeetingRoom")
    @ApiOperation(value = "预定会议室" + API_STATUS_DEVELOPING, httpMethod = "POST")
    public String reserveMeetingRoom() {
        return null;
    }


    /**
     * 4.我的预定会议室
     *
     * @param open_id
     * @return
     */
    @GetMapping("/myMeetingList")
    @ApiOperation(value = "我预定的会议室列表" + API_STATUS_DEVELOPING, httpMethod = "GET")
    public String myMeetingList(String open_id) {
        return null;
    }


    /**
     * 5.通过meeting_id查找会议室及预定详情
     *
     * @return 会议室详情 + 此会议室预定meeting记录
     * 部门下拉列表
     */
    @GetMapping("/myMeetingDetail")
    @ApiOperation(value = "我预定的会议室详情" + API_STATUS_DEVELOPING, httpMethod = "GET")
    public String myMeetingDetail() {
        return null;
    }


    /**
     * 6.取消自己预定的会议室
     *
     * @return
     */
    @PostMapping("/cancelMeetingRoom")
    @ApiOperation(value = "取消我的预定" + API_STATUS_DEVELOPING, httpMethod = "POST")
    public String cancelMeetingRoom() {
        return null;
    }


}
