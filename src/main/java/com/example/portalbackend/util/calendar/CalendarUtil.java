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

    public static Integer getMonthDifference(Calendar from, Calendar to){
        if (from == null || to == null) return null;
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toMonth = to.get(Calendar.MONTH);
        return (toYear - fromYear) * 12 + (toMonth - fromMonth) + 1;
    }
    
    public static Calendar getCalendarWithoutDays(Long calendar){
        if (calendar == null) return null;
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(calendar);
        newCalendar.set(Calendar.DAY_OF_MONTH, 1);
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        return newCalendar;
    }

    public static String getMonthAndYearSpanishTranslation(Calendar calendar){
        if (calendar == null) return null;
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        return months[calendar.get(Calendar.MONTH)] + " del " + calendar.get(Calendar.YEAR);
    }
}
