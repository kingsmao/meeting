package com.chainup;

import com.chainup.entity.Meeting;
import com.chainup.entity.MeetingExample;
import com.chainup.service.MeetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author QX
 * @date 2020/10/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingServiceTest {
    @Autowired
    private MeetingService meetingService;

    @Test
    public void t1(){
        List<Meeting> all = meetingService.findAll(new MeetingExample());
    }
}
