package com.chainup.service;

import com.chainup.entity.Meeting;
import com.chainup.entity.MeetingExample;

import java.util.List;

/**
 * @author QX
 * @date 2020/10/13
 */
public interface MeetingService {
    List<Meeting> findAll(MeetingExample example);
}
