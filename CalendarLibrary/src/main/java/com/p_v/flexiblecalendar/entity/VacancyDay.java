package com.p_v.flexiblecalendar.entity;

import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.fliexiblecalendar.R;

import static com.p_v.flexiblecalendar.view.BaseCellView.REGISTERED_ABSENCE;
import static com.p_v.flexiblecalendar.view.BaseCellView.REGISTERED_CARE;
import static com.p_v.flexiblecalendar.view.BaseCellView.VACANCY_AVAILABLE;
import static com.p_v.flexiblecalendar.view.BaseCellView.VACANCY_NOT_AVAILABLE;

/**
 * Created by apple on 03/01/18.
 */

public class VacancyDay implements Event {
    public int vacancyDayType;

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
}
