package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity5 extends AppCompatActivity {

    private TextView monthTextView;
    private FlexibleCalendarView calendarView;
    private String[] vacancydays = {"Thursday", "Monday"};
    private ArrayList<String> absenceDays = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar5);
        Calendar calendar = Calendar.getInstance();

      //  absenceDays.add(simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 2);
        absenceDays.add(simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, 1);
        absenceDays.add(simpleDateFormat.format(calendar.getTime()));

        Log.v("absence Days", Arrays.toString(absenceDays.toArray()));

        calendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        ImageView leftArrow = (ImageView) findViewById(R.id.left_arrow);
        ImageView rightArrow = (ImageView) findViewById(R.id.right_arrow);

        monthTextView = (TextView) findViewById(R.id.month_text_view);

        calendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day, BaseCellView baseCellView) {

            }
        });
        calendarView.setStartDayOfTheWeek(Calendar.MONDAY);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToPreviousMonth();
            }
        });
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1);
                monthTextView.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                        getResources().getConfiguration().locale) + " " + year);

            }
        });
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToNextMonth();
            }
        });

        final Calendar cal = Calendar.getInstance();
        cal.set(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth(), 1);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                Calendar.LONG, Locale.ENGLISH) + " " + calendarView.getSelectedDateItem().getYear());
        calendarView.setShowDatesOutsideMonth(false);
        calendarView.setDisableAutoDateSelection(true);
        calendarView.setDisableTodaySelection(true);
        calendarView.setEnableRangeSelection(true);
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity5.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar5_date_cell_view, null);
                    //cellView.setTextColor(getResources().getColor(R.color.date_color));
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        calendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day, BaseCellView baseCellView) {
            }
        });

       /* calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<? extends Event> getEventsForTheDay(int year, int month, int day) {
                if (day % 3 == 0) {
                    List<EventW> eventList = new ArrayList<>();
                    eventList.add(new EventW());
                    return eventList;
                }
                return null;
            }
        });
*/
        calendarView.setVacancyDataProvider(new FlexibleCalendarView.VacancyDataProvider() {
            @Override
            public List<? extends VacancyDay> getVacancyForTheDay(int year, int month, int day) {
                List<VacancyDay> eventList = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                DateFormatSymbols dfs = new DateFormatSymbols();
                String weekDay = dfs.getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];


                if (Arrays.asList(vacancydays).contains(weekDay)) {
                    Log.v("containnn", weekDay);
                    VacancyDay vacancyDay = new VacancyDay(year,month,day);
                    vacancyDay.setVacDayType(BaseCellView.REGISTERED_CARE);
                    eventList.add(vacancyDay);
                }

                String currentDay =simpleDateFormat.format(calendar.getTime());
                Log.v("Formatted currentday ", currentDay);
                if (absenceDays.contains(currentDay)) {
                    Log.v("AbsenceDay Matched ", currentDay);
                    VacancyDay vacancyDay = new VacancyDay(year,month,day);
                    if (eventList.contains(vacancyDay)) {
                        eventList.remove(vacancyDay);
                        vacancyDay.setState(BaseCellView.SELECTED);
                    }
                    else
                        vacancyDay.setState(BaseCellView.REGULAR);
                    vacancyDay.setVacDayType(BaseCellView.REGISTERED_ABSENCE);
                    eventList.add(vacancyDay);
                }
                if (eventList != null && eventList.size() > 0)
                    return eventList;

                return null;
            }
        });
    }

    public void showSelectedDates(View view) {
        Toast.makeText(this, "selected Dates " + calendarView.getUserSelectedDates().size(), Toast.LENGTH_SHORT).show();
    }

    public static class EventW implements Event {
        public EventW() {

        }

        @Override
        public int getColor() {
            return R.color.vac_absent_color;
        }
    }
}
