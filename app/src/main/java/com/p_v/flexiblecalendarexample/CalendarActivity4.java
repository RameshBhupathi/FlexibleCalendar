package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity4 extends ActionBarActivity {

    private TextView monthTextView;
    FlexibleCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendary_activity4);

        calendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        calendarView.setStartDayOfTheWeek(Calendar.MONDAY);

        ImageView leftArrow = (ImageView) findViewById(R.id.left_arrow);
        ImageView rightArrow = (ImageView) findViewById(R.id.right_arrow);

        monthTextView = (TextView) findViewById(R.id.month_text_view);

        final Calendar cal = Calendar.getInstance();
        cal.set(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth(), 1);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                Calendar.LONG, Locale.ENGLISH) + " " + calendarView.getSelectedDateItem().getYear());

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToPreviousMonth();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToNextMonth();
            }
        });
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1);
                monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                        Calendar.LONG, Locale.ENGLISH) + " " + year);
            }
        });
        calendarView.setShowDatesOutsideMonth(true);
        calendarView.setDisableAutoDateSelection(true);
        /*calendarView.setDisableTodaySelection(false);
        calendarView.setEnableRangeSelection(true);*/

        calendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {

            }

            @Override
            public void onSelectedDates(List<SelectedDateItem> selectedDateItems) {
                Log.v("onSelectedDates", "" + selectedDateItems.size());
                /*if (selectedDateItems.size() == 2) {
                    Calendar calender1 = Calendar.getInstance();
                    SelectedDateItem selectedDateItemOne = selectedDateItems.get(0);
                    calender1.set(selectedDateItemOne.getYear(), selectedDateItemOne.getMonth(),
                            selectedDateItemOne.getDay());

                    Calendar calender2 = Calendar.getInstance();
                    SelectedDateItem selectedDateItemTwo = selectedDateItems.get(1);
                    calender1.set(selectedDateItemTwo.getYear(), selectedDateItemTwo.getMonth(),
                            selectedDateItemTwo.getDay());

                    List<Date> datesBetween = getDatesBetweenUsingJava7(calender2.getTime(), calender1.getTime());
                    Log.v("selected datesBetween", "datesBetween " + datesBetween.size());
                    if (datesBetween.size() > 0) {
                        for (Date selectedDate : datesBetween) {
                            Log.v("selected datetime", "" + selectedDate.toString());
                            //calendarView.setSelectedDates(selectedDate);
                            calendarView.setUserSelectedDate(selectedDate);
                        }
                    }

                }*/
            }
        });

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity4.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar4_date_cell_view, null);
                }
                if (cellType == BaseCellView.OUTSIDE_MONTH) {
                    cellView.setTextColor(getResources().getColor(R.color.white));
                    cellView.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity4.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_week_cell_view, null);
                    cellView.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
                    cellView.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                    cellView.setTextSize(18);
                }

                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.goToCurrentMonth();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendary_activity4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static List<Date> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public void showSelectedDates(View view) {
        for (SelectedDateItem selectedDateItem : calendarView.getUserSelectedDates()) {
            Log.v("date", selectedDateItem.getDay() + " " + selectedDateItem.getMonth() + " " + selectedDateItem.getDay());
        }
    }
}
