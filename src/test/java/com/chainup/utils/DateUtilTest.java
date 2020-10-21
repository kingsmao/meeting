package com.chainup.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lili
 * @date 2020/10/21 0:36
 * @see
 * @since
 */
class DateUtilTest {

    @Test
    void test1() {
        DateUtil.test1();
    }

    @Test
    void test2() {
    }

    @Test
    void parse() {
        Date parse = DateUtil.parse("2020-10-20 14:21:00");
        long time = parse.getTime();
        System.out.println(parse);
        System.out.println(time);
    }
}