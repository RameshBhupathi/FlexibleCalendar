package com.p_v.flexiblecalendarexample;

import com.p_v.flexiblecalendar.entity.Event;

/**
 * @author p-v
 */
public class CustomEvent implements Event {

    private int color;
    private int day;
    private int month;
    private int year;

    public CustomEvent(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CustomEvent(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

}
