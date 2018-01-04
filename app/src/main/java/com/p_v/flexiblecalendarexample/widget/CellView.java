package com.p_v.flexiblecalendarexample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendarexample.R;

import java.util.List;


/**
 * @author p-v
 */
public class CellView extends BaseCellView {

    private boolean hasEvents;

    public CellView(Context context) {
        super(context);
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!getStateSet().contains(STATE_SELECTED) && !getStateSet().contains(SELECTED_TODAY) &&
                getStateSet().contains(STATE_REGULAR) && hasEvents){
//            this.setBackgroundColor(Color.GREEN);
            this.setBackgroundResource(R.drawable.drawable_bg_cell);

        }
        if(getStateSet().contains(STATE_SELECTED) && hasEvents){
            this.setBackgroundResource(R.drawable.drawable_bg_cell);
        }
    }

    @Override
    public void setEvents(List<? extends Event> colorList) {
        this.hasEvents = colorList !=null && !colorList.isEmpty();
        invalidate();
        requestLayout();
    }

    @Override
    public void setVacancyDays(List<? extends VacancyDay> colorList) {

    }

}