package com.hilum.otp.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public abstract class DateTimeUtils {


    public static Timestamp timestampNow() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static Date dateNow() {
        return new Date();
    }

    public static String toString(Date date) {
        return toString(date, "yyyy-MM-hh HH:mm:ss");
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Instant toInstant(LocalDateTime localDateTime) {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        return localDateTime.toInstant(offset);
    }

    public static Instant toInstant(Timestamp timestamp) {
        return timestamp.toInstant();
    }

    public static Instant toInstant(Date date) {
        return date.toInstant();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(toInstant(localDateTime));
    }

    public static Duration timeDiff(Timestamp timestamp) {
        return timeDiff(timestampNow(), timestamp);
    }

    /**
     * 比较两个时间戳的时间差
     * @param timestamp1 比较的时间戳
     * @param timestamp2 待比较的时间戳
     * @return timestamp1 与 timestamp2的时间差
     */
    public static Duration timeDiff(Timestamp timestamp1, Timestamp timestamp2) {
        return Duration.of(timestamp1.getTime() - timestamp2.getTime(), ChronoUnit.MILLIS);
    }

    public static Duration timeDiff(Date date) {
        return timeDiff(dateNow(), date);
    }


    public static Duration timeDiff(Date date1, Date date2) {
        return Duration.of(date1.getTime() - date2.getTime(), ChronoUnit.MILLIS);
    }

    public static Duration timeDiff(Instant instant1, Instant instant2) {
        return Duration.of(instant1.toEpochMilli() - instant2.toEpochMilli(), ChronoUnit.MILLIS);
    }


    public static Duration timeDiff(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return timeDiff(toInstant(localDateTime1), toInstant(localDateTime2));
    }


    public static boolean isSameYear(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear();
    }

    public static boolean isSameMonth(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear() &&
                localDateTime1.getMonth() == localDateTime2.getMonth();
    }

    public static boolean isSameDay(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear() &&
               localDateTime1.getDayOfYear() == localDateTime2.getDayOfYear() ;
    }

    public static boolean isSameHour(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear() &&
               localDateTime1.getDayOfYear() == localDateTime2.getDayOfYear() &&
               localDateTime1.getHour() == localDateTime2.getHour();
    }

    public static boolean isSameMinute(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear() &&
               localDateTime1.getDayOfYear() == localDateTime2.getDayOfYear() &&
               localDateTime1.getHour() == localDateTime2.getHour() &&
               localDateTime1.getMinute()== localDateTime2.getMinute() ;
    }

    public static boolean isSameSecond(LocalDateTime localDateTime1, LocalDateTime localDateTime2) {
        return localDateTime1.getYear() == localDateTime2.getYear() &&
                localDateTime1.getDayOfYear() == localDateTime2.getDayOfYear() &&
                localDateTime1.getHour() == localDateTime2.getHour() &&
                localDateTime1.getMinute() == localDateTime2.getMinute() &&
                localDateTime1.getSecond() == localDateTime2.getSecond();
    }




}
