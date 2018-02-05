package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            setTextColor(getResources().getColor(R.color.previous_day_text_color));
        else if (state == STATE_REGISTERED_ABSENCE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));
        else if (state == STATE_REGISTERED_CARE)
            setTextColor(getResources().getColor(R.color.regular_day_text_color));
        else if (state == STATE_OUTSIDE_MONTH)
            setTextColor(getResources().getColor(R.color.previous_day_text_color));
        else if (state == STATE_REGULAR)
            setTextColor(getResources().getColor(R.color.regular_day_text_color));
    }

    @Override
    public void setEvents(List<? extends Event> colorList) {
       /* if (colorList != null) {
            Log.v("setEvents", "" + colorList.size());
            paintList = new ArrayList<>(colorList.size());
            for (Event e : colorList) {
                Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                eventPaint.setStyle(Paint.Style.FILL);
                eventPaint.setColor(getContext().getResources().getColor(e.getColor()));
                paintList.add(eventPaint);
            }
            invalidate();
            requestLayout();
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Set<Integer> stateSet = getStateSet();
        Log.v("color", "vac square " + getDATE_COLOR());
        // setTextColor(getDATE_COLOR());
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

