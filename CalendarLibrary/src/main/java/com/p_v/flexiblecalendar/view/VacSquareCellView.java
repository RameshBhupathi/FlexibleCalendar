package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

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
        init(attrs);
    }

    public VacSquareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FlexibleCalendarView);
        try {
            int color = a.getColor(R.styleable.FlexibleCalendarView_day_text_color, 0);

        } finally {
            a.recycle();
        }
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

        setTextColor(getDATE_COLOR());
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

