package com.p_v.flexiblecalendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.fliexiblecalendar.R;

import java.util.List;

/**
 * Cell view with the event count
 *
 * @author p-v
 */
public class EventCountCellView extends BaseCellView {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mEventCount;
    private int eventCircleY;
    private int eventCircleX;
    private int mTextY;
    private CalendarEvent calendarEvent;
    private int radius;
    private int eventTextColor;
    private int eventBackground;
    private int eventTextSize;
    private List<VacancyDay> vacancyDayList;

    public EventCountCellView(Context context) {
        super(context);
    }

    public EventCountCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setTypeface(Typeface.DEFAULT_BOLD);
    }

    public EventCountCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EventCountCellView);
        try {
            radius = (int) a.getDimension(R.styleable.EventCountCellView_event_count_radius, 15);
            eventBackground = a.getColor(R.styleable.EventCountCellView_event_background,
                    getResources().getColor(android.R.color.black));
            eventTextColor = a.getColor(R.styleable.EventCountCellView_event_count_text_color,
                    getResources().getColor(android.R.color.white));
            eventTextSize = (int) a.getDimension(R.styleable.EventCountCellView_event_text_size, -1);
        } finally {
            a.recycle();
        }
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
        if (mEventCount > 0) {
            Paint p = new Paint();
            p.setTextSize(getTextSize());

            Rect rect = new Rect();
            p.getTextBounds("31", 0, 1, rect); // measuring using fake text

           /* eventCircleY = (getHeight() - rect.height()) / 4;
            eventCircleX = (3 * getWidth() + rect.width()) / 4;*/

            eventCircleY = (getHeight() / 2);
            eventCircleX = (getWidth() / 2);

            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(eventTextSize == -1 ? getTextSize() / 2 : eventTextSize);
            mTextPaint.setColor(eventTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            mTextY = eventCircleY + radius / 2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v("mEventCount  before ", " " + mEventCount);
        if (getStateSet() != null && getStateSet().size() >1) {
            if (getStateSet().contains(STATE_SELECTED) && getStateSet().contains(STATE_VACANCY_CARE_AVAILABLE) ||
                    getStateSet().contains(STATE_VACANCY_CARE_NOT_AVAILABLE)) {
                if (mEventCount > 0 && mPaint != null && mTextPaint != null) {
                    canvas.drawCircle(eventCircleX, eventCircleY, radius, mPaint);
                    canvas.drawText(String.valueOf(calendarEvent.getDay()), eventCircleX, mTextY, mTextPaint);
                }
            }
        }

    }

    @Override
    public void setEvents(List<? extends Event> colorList) {
        if (colorList != null && !colorList.isEmpty()) {
            mEventCount = colorList.size();
            calendarEvent = (CalendarEvent) colorList.get(0);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(eventBackground);
            invalidate();
            requestLayout();
        }else {
            mEventCount=0;
            calendarEvent=null;
        }
    }

    @Override
    public void setVacancyDays(List<? extends VacancyDay> vacancyList) {
    }
}
