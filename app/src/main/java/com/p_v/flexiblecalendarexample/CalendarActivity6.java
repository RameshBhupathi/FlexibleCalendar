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
import com.p_v.flexiblecalendar.entity.CalendarEvent;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
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

        calendarView.setStartDayOfTheWeek(Calendar.SUNDAY);
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
        calendarView.setShowDatesOutsideMonth(true);
        calendarView.setDisableAutoDateSelection(false);
        calendarView.setDisableTodaySelection(false);
        calendarView.setEnableRangeSelection(false);
        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, @BaseCellView.CellType int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(CalendarActivity6.this);
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar6_date_cell_view, null);
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
            public void onDateClick(final int year, final int month, final int day, BaseCellView baseCellView) {

                calendarView.setEventDataProvider(new FiniteFlexibleCalendarView.EventDataProvider() {
                    @Override
                    public List<? extends Event> getEventsForTheDay(int eventYear, int eventMonth, int eventDay) {

                        return createEvent(eventYear, eventMonth, eventDay);
                    }
                });
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
                SelectedDateItem selectedDateItem = new SelectedDateItem(year, month, day);

                String weekday = selectedDateItem.getDayOfWeek();
                Log.v(" weekday", weekday);
                if (weekday.equals("Sunday") || weekday.equals("Saturday")) {
                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(BaseCellView.PREVIOUS_DATE);
                    eventList.add(vacancyDay);
                }
                if (month == 2 && day > 10 && day < 15) {
                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(BaseCellView.VACANCY_AVAILABLE);
                    eventList.add(vacancyDay);

                }
                if (month == 2 && day > 20 && day <= 25) {

                    VacancyDay vacancyDay = new VacancyDay();
                    vacancyDay.setVacDayType(BaseCellView.VACANCY_NOT_AVAILABLE);
                    eventList.add(vacancyDay);

                }
                if (eventList != null && eventList.size() > 0)
                    return eventList;

                return null;
            }
        });
        final Calendar todayCalendar=Calendar.getInstance();
        calendarView.setEventDataProvider(new FiniteFlexibleCalendarView.EventDataProvider() {
            @Override
            public List<? extends Event> getEventsForTheDay(int eventYear, int eventMonth, int eventDay) {

                return createEvent(todayCalendar.get(Calendar.YEAR),
                        todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DAY_OF_MONTH));
            }
        });

    }

    private List<CalendarEvent> createEvent(int selectedYear, int selectedMonthOfYear, int selectedDayOfMonth) {
        List<CalendarEvent> colorLst = new ArrayList<>();
        CalendarEvent event = (new CalendarEvent(selectedYear, selectedMonthOfYear, selectedDayOfMonth));
        event.setColor(android.R.color.holo_red_dark);
        colorLst.add(event);
        return colorLst;
    }

    public void showSelectedDates(View view) {
    }

    public static class EventW implements Event {
        public EventW() {

        }

        @Override
        public int getColor() {
            return R.color.black;
        }
    }
}