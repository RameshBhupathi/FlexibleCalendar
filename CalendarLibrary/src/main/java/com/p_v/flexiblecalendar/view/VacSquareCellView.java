package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 03/01/18.
 */

public class VacSquareCellView extends SquareCellView {

    private int leftMostPosition = Integer.MIN_VALUE;
    private List<Paint> paintList;
    private List<VacancyDay> vacancyDayList;

    public VacSquareCellView(Context context) {
        super(context);
    }

    public VacSquareCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setTextSize(16);
        //   init(attrs);
    }

    public VacSquareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // init(attrs);
    }

    @Override
    public void addState(int state) {
        super.addState(state);
        if (state == BaseCellView.STATE_SELECTED)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));
        else if (state == STATE_TODAY)
            setTextColor(getResources().getColor(R.color.regular_day_text_color));
        else if (state == STATE_PREVIOUS_DATE)
            setTextColor(getResources().getColor(R.color.gains_boro));

        else if (state == STATE_REGISTERED_ABSENCE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));

        else if (state == STATE_REGISTERED_CARE)
            setTextColor(getResources().getColor(R.color.gray_eclipse));

        else if (state == BaseCellView.VACANCY_AVAILABLE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));

        else if (state == BaseCellView.VACANCY_NOT_AVAILABLE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));
        else if (state == STATE_OUTSIDE_MONTH)
            setTextColor(getResources().getColor(R.color.gains_boro));
        else if (state == STATE_REGULAR)
            setTextColor(getResources().getColor(R.color.regular_day_text_color));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    @Override
    public void setVacancyDays(List<? extends VacancyDay> colorList) {
        if (colorList != null) {
            vacancyDayList = new ArrayList<>(colorList.size());
            vacancyDayList.addAll(colorList);
            invalidate();
            requestLayout();
        }
    }
}

