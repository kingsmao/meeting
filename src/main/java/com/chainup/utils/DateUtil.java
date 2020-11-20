package com.chainup.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Paceage:com.kingmao.dog.utils
 * Description:
 * Date:2019/2/21
 * Author: KingMao
 **/
@Component
public class DateUtil {

    /**
     * 求两个date类型之间的分钟差值
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static int getMin(Date starTime, Date endTime) {
        return (int) (endTime.getTime() - starTime.getTime()) / (1000 * 60);
    }

    /**
     * 返回时间，不包含是秒 2019-02-26 10:36
     *
     * @param date
     * @return
     */
    public static Date getYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = sdf.format(new Date());
        Date date1 = null;
        try {
            date1 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 返回时间，2019-02-26 10:36
     *
     * @param date
     * @return
     */
    public static Date getYMD2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        Date date1 = null;
        try {
            date1 = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 返回字符串类型日期，
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 返回小时和分钟，24h制，例如 12：30
     * @param date
     * @return
     */
    public static String date2StrHourMin(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String str = sdf.format(date);
        return str;
    }

    public static String dateWithPattern(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        return str;
    }

    /**
     * str -->date
     *
     * @param str
     * @return
     */
    public static Date str2Date(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * str -->date
     *
     * @param str
     * @return
     */
    public static Date str2TDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Date getDatePlus(Date date) {
        //计算两天后的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(date);

        c.add(Calendar.DATE, 2);

        return c.getTime();
    }

    /**
     * 时间+分钟，返回结果不包含秒
     *
     * @param date
     * @param mins
     * @return
     */
    public static Date getPlus(Date date, Integer mins) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = null;
        try {
            date1 = sdf.parse(sdf.format((date.getTime() + mins * 60 * 1000)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1;
    }

    /**
     * 计算可服务时间
     * <p>
     * 上午earnTime为满的，下午需要有earnTime
     *
     * @param serviceStartTime
     * @param serviceEndTime
     * @param earnTime
     * @param consumeTime
     * @return
     */
    /*public static Date countRat(Date serviceStartTime,Date serviceEndTime,Integer earnTime, Integer consumeTime, String dtype){
        double b = getMin(serviceStartTime,serviceEndTime) * (new BigDecimal((float)consumeTime/earnTime).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        return getPlus(serviceStartTime, (int) b);
    }*/
    public static Date countRat(Date serviceStartTime, Date serviceEndTime, Integer earnTime, Integer consumeTime, String dtype) {
        Integer earnTimeByType = getEarnTime(serviceStartTime, serviceEndTime, earnTime, dtype);
        if (dtype.equals("am")) {
            if (consumeTime == 0) {
                return serviceStartTime;
            } else {
                double a = getMin(serviceStartTime, get12(serviceStartTime)) * (getRay(consumeTime, earnTimeByType)); // 计算后的排队耗时
                return getPlus(serviceStartTime, (int) a); //计算后的排队时间
            }

        } else {
            if (consumeTime == 0) {
                return get12(serviceStartTime);
            } else {
                double b = getMin(get12(serviceStartTime), serviceEndTime) * (getRay(consumeTime, earnTimeByType));  // 计算后的排队耗时
                return getPlus(get12(serviceStartTime), (int) b); //计算后的排队时间
            }
        }
    }


    /**
     * @param dateString 2019-02-26 09:30
     * @return
     */
    public static Date parse(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String timeRange(Date begin, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateBegin = sdf.format(begin);
        String dateEnd = sdf.format(endDate);
        return dateBegin + " --- " + dateEnd;
    }


    public static String timeDateRange(Date begin, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateBegin = sdf.format(begin);
        String dateEnd = sdf.format(endDate);
        return dateBegin + " --- " + dateEnd;
    }


    public static Date test1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String a = "2019-02-26 09:30:00";
        Date date1 = null;
        try {
            date1 = sdf.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("开始时间：" + date1);
        return date1;
    }

    public static Date test2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String a = "2019-02-26 20:00:00";
        Date date1 = null;
        try {
            date1 = sdf.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("结束时间：" + date1);
        return date1;
    }

    /**
     * 时间+分钟，返回结果不包含秒
     *
     * @param date
     * @return
     */
    public static Date get12(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str12 = " 12:00:00";
        String s1 = sdf.format(date).substring(0, 10);
        Date date1 = null;
        try {
            date1 = sdf.parse(s1 + str12);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1;
    }

    /**
     * @param serviceStarTime
     * @param serviceEndTime
     * @param earnTime
     * @param etype
     * @return
     */
    public static Integer getEarnTime(Date serviceStarTime, Date serviceEndTime, Integer earnTime, String etype) {
        Integer a = getMin(serviceStarTime, get12(serviceStarTime));
        Integer b = getMin(serviceStarTime, serviceEndTime);
        double ray = getRay(a, b);
        if (etype.equals("am")) {
            return (int) ray * earnTime;
        } else {
            return (int) (1.0 - ray * earnTime);
        }
    }


    /**
     * 输入分子分母，得出比例
     *
     * @param a 分子
     * @param b 分母
     * @return
     */
    public static double getRay(Integer a, Integer b) {
        return new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
