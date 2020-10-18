package com.chainup.service;

import com.chainup.core.dto.MeetingRoomDto;
import com.chainup.entity.Meeting;
import com.chainup.entity.MeetingExample;

import java.util.List;

/**
 * @author QX
 * @date 2020/10/13
 */
public interface MeetingService {
    List<Meeting> findAll(MeetingExample example);

    /**
     * 根据时间查找可用的会议室
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 会议室列表
     */
    List<MeetingRoomDto> availableRoomByTime(String beginTime, String endTime);
}
