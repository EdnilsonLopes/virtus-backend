package com.virtus.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {

    private static final SimpleDateFormat FORMAT_DATE_EN = new SimpleDateFormat("yyyyMMdd");

    public static LocalDate parseDate(String date) {
        if (date == null) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime parseDateTime(String date) {
        if (date == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalTime parseTime(String time) {
        if (time == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static Calendar parseDateJson(String date) {
        if (date == null) {
            return null;
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(formatter.parse(date));
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDateEn(Calendar date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DATE_EN.format(date);
    }

    public static String formatDatePtBr(LocalDate date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }


    public static String formatDateTimePtBr(LocalDateTime date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }


}
