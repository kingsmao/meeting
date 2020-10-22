package com.chainup.service;

import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.core.dto.MeetingRoomReserveDto;
import com.chainup.core.dto.MyMeetingRoomDto;
import com.chainup.core.params.ReserveMeetingParams;

import java.util.List;

/**
 * @author QX
 * @date 2020/10/13
 */
public interface MeetingService {
    /**
     * 根据时间查找可用的会议室
     *
     *
     * @param date 开始日期
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 会议室列表
     */
    List<MeetingRoomDto> availableRoomByTime(String date, String beginTime, String endTime);

    /**
     * 通过openid获取我预定的会议室
     *
     * @param openId openId
     * @return
     */
    List<MyMeetingRoomDto> getMyMeetingList(String openId);

    /**
     * 预定会议室
     *
     * @param reserveMeetingParams 预定会议室参数
     */
    void reserveMeetingRoom(ReserveMeetingParams reserveMeetingParams);

    /**
     * 预定页面会议室详情
     *
     * @param date      日期
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param roomId    房间id
     * @param openId
     * @return
     */
    MeetingRoomReserveDto getMeetingRoomInfo(String date, String beginTime, String endTime, int roomId, String openId);

    /**
     * 获取我的预定会议室详情
     *
     * @param meetingId
     * @return
     */
    MyMeetingRoomDto myMeetingDetail(int meetingId);

    /**
     * 取消我预定的会议室
     *
     * @param meetingId 会议室id
     */
    void cancelMeetingRoom(int meetingId);

    /**
     * 定时任务更新会议室状态
     */
    void invalidMeetingTime();
}
