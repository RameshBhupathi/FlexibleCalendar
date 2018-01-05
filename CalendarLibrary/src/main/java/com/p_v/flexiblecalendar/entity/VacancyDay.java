package com.p_v.flexiblecalendar.entity;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.fliexiblecalendar.R;

import static com.p_v.flexiblecalendar.view.BaseCellView.REGISTERED_ABSENCE;

/**
 * Created by apple on 03/01/18.
 */

public class VacancyDay implements Event {
    public int vacancyDayType;

    @Override
    public int getColor() {
        if (vacancyDayType == REGISTERED_ABSENCE)
            return R.color.vac_absent_color;
        else
            return R.color.vac_registered_color;
    }

    public void setVacDayType(@BaseCellView.CellType int dayType) {
        this.vacancyDayType = dayType;
    }

    @BaseCellView.CellType
    public int getVacDayType() {
        return vacancyDayType;
    }
}
