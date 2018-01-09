package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.TextView;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author p-v
 */
public abstract class BaseCellView extends TextView {

    public static final int TODAY = 0;
    public static final int SELECTED = 1;
    public static final int REGULAR = 3;
    public static final int SELECTED_TODAY = 4;
    public static final int OUTSIDE_MONTH = 5;
    public static final int PREVIOUS_DATE = 6;
    public static final int REGISTERED_CARE = 7;
    public static final int REGISTERED_ABSENCE = 8;
    public static final int VACANCY_AVAILABLE = 9;
    public static final int VACANCY_NOT_AVAILABLE = 10;

    @IntDef({TODAY, SELECTED, REGULAR, SELECTED_TODAY, OUTSIDE_MONTH, PREVIOUS_DATE,
            REGISTERED_CARE, REGISTERED_ABSENCE,VACANCY_AVAILABLE,VACANCY_NOT_AVAILABLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CellType {
    }

    public static final int STATE_TODAY = R.attr.state_date_today;
    public static final int STATE_REGULAR = R.attr.state_date_regular;
    public static final int STATE_SELECTED = R.attr.state_date_selected;
    public static final int STATE_OUTSIDE_MONTH = R.attr.state_date_outside_month;
    public static final int STATE_PREVIOUS_DATE = R.attr.state_date_previous;

    public static final int STATE_REGISTERED_CARE = R.attr.state_registered_care;
    public static final int STATE_REGISTERED_ABSENCE = R.attr.state_registered_absence;

    public static final int STATE_VACANCY_CARE_AVAILABLE = R.attr.state_vacancy_care_available;
    public static final int STATE_VACANCY_CARE_NOT_AVAILABLE = R.attr.state_vacancy_care_not_available;

    public static final int DATE_COLOR = R.attr.day_text_color;

    private Set<Integer> stateSet;
    private Context context;

    public BaseCellView(Context context) {
        super(context);
        stateSet = new HashSet<>(3);
        this.context = context;
    }

    public BaseCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        stateSet = new HashSet<>(3);
    }


    public BaseCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        stateSet = new HashSet<>(3);
    }

    public void addState(int state) {
        stateSet.add(state);
    }

    public void clearAllStates() {
        stateSet.clear();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        if (stateSet == null) stateSet = new HashSet<>(3);
        if (!stateSet.isEmpty()) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + stateSet.size());
            int[] states = new int[stateSet.size()];
            int i = 0;
            for (Integer s : stateSet) {
                states[i++] = s;
            }
            mergeDrawableStates(drawableState, states);
            return drawableState;
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }

    public abstract void setEvents(List<? extends Event> colorList);


    public abstract void setVacancyDays(List<? extends VacancyDay> vacancyDaysList);

    public Set<Integer> getStateSet() {
        return stateSet;
    }

}
