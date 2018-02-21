package com.p_v.flexiblecalendar.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author p-v
 */
public class CalendarEvent implements Event {

    private int color;
    private int day;
    private int month;
    private int year;

    public CalendarEvent() {

    }

    public CalendarEvent(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CalendarEvent(int color) {
        this.color = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    public Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(getDateTime());
    }
}
