package com.example.portalbackend.util.calendar;

import java.util.Calendar;

public class CalendarUtil {

    public static Calendar getCalendar(String date){
        if (date == null || date.isEmpty()) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        return calendar;
    }

    public static Calendar getCalendar(Long date){
        if (date == null ) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }
}
