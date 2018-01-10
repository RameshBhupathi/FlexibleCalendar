package com.p_v.flexiblecalendarexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.p_v.flexiblecalendar.FiniteFlexibleCalendarView;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity6 extends AppCompatActivity {

    private TextView monthTextView;
    private FiniteFlexibleCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar6);

        calendarView = (FiniteFlexibleCalendarView) findViewById(R.id.calendar_view);
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
        calendarView.setOnMonthChangeListener(new FiniteFlexibleCalendarView.OnMonthChangeListener() {
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
        calendarView.setEnableRangeSelection(false);
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity6.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar6_date_cell_view, null);
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

        calendarView.setOnDateClickListener(new FiniteFlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day, BaseCellView baseCellView) {

               /* Tooltip.Builder builder = new Tooltip.Builder(baseCellView)
                        .setCornerRadius(10f)
                        .setGravity(Gravity.BOTTOM)

                        .setText(String.valueOf("It is yet another very simple tool tip!"));
                builder.show();*/
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
       calendarView.setMaxValue(10);
        calendarView.setVacancyDataProvider(new FiniteFlexibleCalendarView.VacancyDataProvider() {
            @Override
            public List<? extends VacancyDay> getVacancyForTheDay(int year, int month, int day) {
                List<VacancyDay> eventList = new ArrayList<>();
                Log.v("get Vac Day", year + " " + month + " " + day);
                if (month == 0 && day > 10 && day < 15) {
                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(BaseCellView.VACANCY_AVAILABLE);
                    eventList.add(vacancyDay);

                }
                if (month == 0 && day > 20 && day <= 25) {

                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(BaseCellView.VACANCY_NOT_AVAILABLE);
                    eventList.add(vacancyDay);

                }
                if (eventList != null && eventList.size() > 0)
                    return eventList;

                return null;
            }
        });

    }

    public void showSelectedDates(View view) {
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