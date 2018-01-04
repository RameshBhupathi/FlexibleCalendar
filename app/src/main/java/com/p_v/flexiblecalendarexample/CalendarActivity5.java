package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity5 extends AppCompatActivity {

    private TextView monthTextView;
    private FlexibleCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar5);

        calendarView = (FlexibleCalendarView) findViewById(R.id.calendar_view);
        ImageView leftArrow = (ImageView) findViewById(R.id.left_arrow);
        ImageView rightArrow = (ImageView) findViewById(R.id.right_arrow);

        monthTextView = (TextView) findViewById(R.id.month_text_view);

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
               /* if (day % 3 == 0) {

                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(VacancyDay.VAC_CONFIRM);
                    eventList.add(vacancyDay);

                }*/
                if (day % 7 == 0) {

                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(VacancyDay.VAC_ABSENCE);
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
