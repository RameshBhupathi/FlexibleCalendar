package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p-v on 15/07/15.
 */
public class SquareCellView extends CircularEventCellView {

    private List<Paint> paintList;

    public SquareCellView(Context context) {
        super(context);
    }

    public SquareCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getStateSet().contains(STATE_SELECTED))
            setTextColor(getResources().getColor(R.color.white));
        else
            setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //making sure the cell view is a square
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    public void setVacancyDays(List<? extends VacancyDay> colorList) {
        Log.v("Vacancy Days", "" + colorList.size());
        if (colorList != null) {
            paintList = new ArrayList<>(colorList.size());
            for (VacancyDay vacancyDay : colorList) {
                Paint eventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                eventPaint.setStyle(Paint.Style.FILL);

                if (vacancyDay.getVacDayType() == BaseCellView.REGISTERED_ABSENCE)
                    eventPaint.setColor(getContext().getResources().getColor(R.color.vac_absent_color));
                else
                    eventPaint.setColor(getContext().getResources().getColor(R.color.vac_registered_color));
                paintList.add(eventPaint);
            }
            invalidate();
            requestLayout();
        }
    }

}
