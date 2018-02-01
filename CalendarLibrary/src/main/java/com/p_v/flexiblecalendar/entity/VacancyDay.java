package com.p_v.flexiblecalendar.entity;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.fliexiblecalendar.R;

import java.util.Date;

import static com.p_v.flexiblecalendar.view.BaseCellView.REGISTERED_ABSENCE;
import static com.p_v.flexiblecalendar.view.BaseCellView.REGISTERED_CARE;
import static com.p_v.flexiblecalendar.view.BaseCellView.VACANCY_AVAILABLE;
import static com.p_v.flexiblecalendar.view.BaseCellView.VACANCY_NOT_AVAILABLE;

/**
 * Created by apple on 03/01/18.
 */

public class VacancyDay implements Event {
    public int vacancyDayType;
    public int state;
    private int year;
    private int month;
    private int dayOfMonth;
    private Date date;

    public VacancyDay() {

    }

    public VacancyDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = day;

    }

    @Override
    public int getColor() {
        if (vacancyDayType == REGISTERED_ABSENCE)
            return R.color.vac_absent_color;
        else if (vacancyDayType == REGISTERED_CARE)
            return R.color.vac_registered_color;
        else if (vacancyDayType == VACANCY_AVAILABLE)
            return R.color.pink;
        else if (vacancyDayType == VACANCY_NOT_AVAILABLE)
            return R.color.light_blue_100;
        return 0;
    }

    public void setVacDayType(@BaseCellView.CellType int dayType) {
        this.vacancyDayType = dayType;
    }

    @BaseCellView.CellType
    public int getVacDayType() {
        return vacancyDayType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VacancyDay))
            return false;
        VacancyDay vacancyDay = (VacancyDay) obj;

        return (vacancyDay.dayOfMonth == dayOfMonth) &&
                (vacancyDay.month == month) &&
                (vacancyDay.year == year);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = 7 * hash + String.valueOf(dayOfMonth).hashCode();
        hash = 7 * hash + String.valueOf(month).hashCode();
        hash = 7 * hash + String.valueOf(year).hashCode();
        return hash;
    }
}
