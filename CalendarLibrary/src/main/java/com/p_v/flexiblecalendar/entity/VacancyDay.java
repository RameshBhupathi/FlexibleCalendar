package com.p_v.flexiblecalendar.entity;

import android.support.annotation.StringDef;

import com.p_v.fliexiblecalendar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by apple on 03/01/18.
 */

public class VacancyDay implements Event {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({VAC_ABSENCE, VAC_CONFIRM})
    public @interface VacancyDayType {
    }

    public static final String VAC_CONFIRM = "Vac_Confirm";
    public static final String VAC_ABSENCE = "Absence_Confirm";


    public String vacancyDayType;

    @Override
    public int getColor() {
        if (vacancyDayType.equalsIgnoreCase(VAC_ABSENCE))
            return R.color.vac_absent_color;
        else
            return R.color.vac_registered_color;
    }

    public void setVacDayType(@VacancyDayType String dayType) {
        this.vacancyDayType = dayType;
    }

    @VacancyDayType
    public String getVacDayType() {
        return vacancyDayType;
    }
}
