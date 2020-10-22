package com.chainup.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

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

    @Test
    void timeRange() {
        String s = DateUtil.timeRange(new Date(), new Date());
        System.out.println(s);
    }

    @Test
    void getMin() {
    }

    @Test
    void getYMD() {
    }

    @Test
    void getYMD2() {
    }

    @Test
    void date2Str() {
    }

    @Test
    void str2Date() {
    }

    @Test
    void str2TDate() {
    }

    @Test
    void getDatePlus() {
    }

    @Test
    void getPlus() {
    }

    @Test
    void countRat() {
    }

    @Test
    void timeDateRange() {
        String s = DateUtil.timeDateRange(new Date(), new Date());
        System.out.println(s);
    }

    @Test
    void get12() {
    }

    @Test
    void getEarnTime() {
    }

    @Test
    void getRay() {
    }
}