package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;

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
        /*TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircularEventCellView);
        try {
            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    radius = (int) a.getDimension(R.styleable.CircularEventCellView_event_radius, 8);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    radius = (int) a.getDimension(R.styleable.CircularEventCellView_event_radius, 6);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    radius = (int) a.getDimension(R.styleable.CircularEventCellView_event_radius, 4);
                    break;
                default:
                    radius = (int) a.getDimension(R.styleable.CircularEventCellView_event_radius, 5);
            }
            padding = (int) a.getDimension(R.styleable.CircularEventCellView_event_circle_padding, 1);
        } finally {
            a.recycle();
        }*/
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

