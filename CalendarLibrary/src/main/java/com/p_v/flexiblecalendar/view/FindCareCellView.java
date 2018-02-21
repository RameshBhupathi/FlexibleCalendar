package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by apple on 19/02/18.
 */

public class FindCareCellView extends SquareCellView {

    private int leftMostPosition = Integer.MIN_VALUE;
    private List<Paint> paintList;
    private List<VacancyDay> vacancyDayList;
    private int eventCircleY;
    private int eventCircleX;
    private int mTextY;
    private Paint mPaint;
    private int radius;
    private int eventTextColor;
    private int eventBackground;
    private int eventTextSize;

    public FindCareCellView(Context context) {
        super(context);
    }

    public FindCareCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setTextSize(16);
        //   init(attrs);
    }

    public FindCareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        else if (state == BaseCellView.STATE_VACANCY_CARE_AVAILABLE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));
        else if (state == BaseCellView.STATE_VACANCY_CARE_NOT_AVAILABLE)
            setTextColor(getResources().getColor(R.color.selected_day_text_color));
        else if (state == STATE_OUTSIDE_MONTH)
            setTextColor(getResources().getColor(R.color.gains_boro));
        else if (state == STATE_REGULAR)
            setTextColor(getResources().getColor(R.color.regular_day_text_color));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Set<Integer> stateSet = getStateSet();

        //initialize paint objects only if there is no state or just one state i.e. the regular day state
        if ((stateSet == null || stateSet.isEmpty()
                || (stateSet.size() == 1 && stateSet.contains(STATE_SELECTED)))) {
            int num = paintList.size();

            Paint p = new Paint();
            p.setTextSize(getTextSize());

            Rect rect = new Rect();
            p.getTextBounds("31", 0, 1, rect); // measuring using fake text

            eventCircleY = (3 * getHeight() + rect.height()) / 4;

            //calculate left most position for the circle
            if (leftMostPosition == Integer.MIN_VALUE) {
                leftMostPosition = (getWidth() / 2) - (num / 2) * 2 * (5 + radius);
                if (num % 2 == 0) {
                    leftMostPosition = leftMostPosition + radius + 5;
                }
            }

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Set<Integer> stateSet = getStateSet();

        // draw only if there is no state or just one state i.e. the regular day state
        if ((stateSet == null || stateSet.isEmpty() || (stateSet.size() == 1))) {
            Log.v("STATE SET",""+stateSet.iterator().hasNext());
          /*  int num = paintList.size();
            for (int i = 0; i < num; i++) {*/
            Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            eventPaint.setStyle(Paint.Style.FILL);
            eventPaint.setColor(getContext().getResources().getColor(R.color.green));
                canvas.drawCircle(calculateStartPoint(0), eventCircleY, radius, eventPaint);
         //   }
        }
    }

    @Override
    public void setEvents(List<? extends Event> colorList) {
        paintList = new ArrayList<>();

            Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            eventPaint.setStyle(Paint.Style.FILL);
            eventPaint.setColor(getContext().getResources().getColor(R.color.green));
            paintList.add(eventPaint);

        invalidate();
        requestLayout();
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

    private int calculateStartPoint(int offset) {
        return leftMostPosition + offset * (2 * (radius + 5));
    }

}
