/*
package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.IDateCellViewDrawer;
import com.p_v.fliexiblecalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

*/
/**
 * @author p-v
 *//*

class FlexibleCalendarGridAdapterDummy extends BaseAdapter {

    private static final int SIX_WEEK_DAY_COUNT = 42;
    private int year;
    private int month;
    private Context context;
    private MonthDisplayHelper monthDisplayHelper;
    private Calendar calendar;
    private OnDateCellItemClickListener onDateCellItemClickListener;
    private SelectedDateItem selectedItem;
    private SelectedDateItem userSelectedDateItem;
    private MonthEventFetcher monthEventFetcher;
    private IDateCellViewDrawer cellViewDrawer;
    private boolean showDatesOutsideMonth;
    private boolean decorateDatesOutsideMonth;
    private boolean disableAutoDateSelection;
    private boolean disableTodaySelection;
    private boolean enableRangeSelection;
    private static boolean isRangeSelected;
    public static List<SelectedDateItem> userSelectedDateItems;


    public FlexibleCalendarGridAdapterDummy(Context context, int year, int month,
                                            boolean showDatesOutsideMonth, boolean decorateDatesOutsideMonth,
                                            int startDayOfTheWeek,
                                            boolean disableAutoDateSelection, boolean disableTodaySelection,
                                            boolean enableRangeSelection) {
        this.context = context;
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
        this.disableAutoDateSelection = disableAutoDateSelection;
        this.disableTodaySelection = disableTodaySelection;
        this.enableRangeSelection = enableRangeSelection;
        initialize(year, month, startDayOfTheWeek);
        userSelectedDateItems = new ArrayList<>();
    }

    public void initialize(int year, int month, int startDayOfTheWeek) {
        this.year = year;
        this.month = month;
        this.monthDisplayHelper = new MonthDisplayHelper(year, month, startDayOfTheWeek);
        this.calendar = FlexibleCalendarHelper.getLocalizedCalendar(context);
    }

    @Override
    public int getCount() {
        int weekStartDay = monthDisplayHelper.getWeekStartDay();
        int firstDayOfWeek = monthDisplayHelper.getFirstDayOfMonth();
        int diff = firstDayOfWeek - weekStartDay;
        int toAdd = diff < 0 ? (diff + 7) : diff;
        return showDatesOutsideMonth ? SIX_WEEK_DAY_COUNT
                : monthDisplayHelper.getNumberOfDaysInMonth()
                + toAdd;
    }

    @Override
    public Object getItem(int position) {
        //TODO implement
        int row = position / 7;
        int col = position % 7;
        return monthDisplayHelper.getDayAt(row, col);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int row = position / 7;
        int col = position % 7;

        //checking if is within current month
        boolean isWithinCurrentMonth = monthDisplayHelper.isWithinCurrentMonth(row, col);

        //compute cell type
        int cellType = BaseCellView.OUTSIDE_MONTH;
        //day at the current row and col
        int day = monthDisplayHelper.getDayAt(row, col);
        if (isWithinCurrentMonth) {
            //set to REGULAR if is within current month
            cellType = BaseCellView.REGULAR;
            if (disableAutoDateSelection) {
                if (userSelectedDateItems.size() > 0) {
                    SelectedDateItem selectedItem = new SelectedDateItem(year, month, day);
                    if (userSelectedDateItems.contains(selectedItem)) {
                        Log.v("SelectedDays ", "Day " + selectedItem.getDay());
                        cellType = BaseCellView.SELECTED;
                    }

                } else if (userSelectedDateItem != null && userSelectedDateItem.getYear() == year
                        && userSelectedDateItem.getMonth() == month
                        && userSelectedDateItem.getDay() == day) {
                    //selected
                    cellType = BaseCellView.SELECTED;
                }


            } else {
                if (selectedItem != null && selectedItem.getYear() == year
                        && selectedItem.getMonth() == month
                        && selectedItem.getDay() == day) {
                    //selected
                    cellType = BaseCellView.SELECTED;
                }
            }
            if (calendar.get(Calendar.YEAR) == year
                    && calendar.get(Calendar.MONTH) == month
                    && calendar.get(Calendar.DAY_OF_MONTH) == day) {
                if (!disableTodaySelection) {
                    if (cellType == BaseCellView.SELECTED) {
                        //today and selected
                        cellType = BaseCellView.SELECTED_TODAY;
                    } else {
                        //today
                        cellType = BaseCellView.TODAY;
                    }
                } else
                    Log.i("TodaySelection", "Disabled");
            }
        }

        BaseCellView cellView = cellViewDrawer.getCellView(position, convertView, parent, cellType);
        if (cellView == null) {
            cellView = (BaseCellView) convertView;
            if (cellView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                cellView = (BaseCellView) inflater.inflate(R.layout.square_cell_layout, null);
            }
        }
        drawDateCell(cellView, day, cellType);
        return cellView;
    }

    private void drawDateCell(BaseCellView cellView, int day, int cellType) {
        cellView.clearAllStates();
        if (cellType != BaseCellView.OUTSIDE_MONTH) {
            cellView.setText(String.valueOf(day));
            cellView.setOnClickListener(new DateClickListener(day, month, year));
            // add events
            if (monthEventFetcher != null) {
                cellView.setEvents(monthEventFetcher.getEventsForTheDay(year, month, day));
            }
            switch (cellType) {
                case BaseCellView.SELECTED_TODAY:
                    cellView.addState(BaseCellView.STATE_TODAY);
                    cellView.addState(BaseCellView.STATE_SELECTED);
                    break;
                case BaseCellView.TODAY:
                    cellView.addState(BaseCellView.STATE_TODAY);
                    break;
                case BaseCellView.SELECTED:
                    cellView.addState(BaseCellView.STATE_SELECTED);
                    break;
                default:
                    cellView.addState(BaseCellView.STATE_REGULAR);
            }
        } else {
            if (showDatesOutsideMonth) {
                cellView.setText(String.valueOf(day));
                int[] temp = new int[2];
                //date outside month and less than equal to 12 means it belongs to next month otherwise previous
                if (day <= 12) {
                    FlexibleCalendarHelper.nextMonth(year, month, temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0]));
                } else {
                    FlexibleCalendarHelper.previousMonth(year, month, temp);
                    cellView.setOnClickListener(new DateClickListener(day, temp[1], temp[0]));
                }

                if (decorateDatesOutsideMonth && monthEventFetcher != null) {
                    cellView.setEvents(monthEventFetcher.getEventsForTheDay(temp[0], temp[1], day));
                }

                cellView.addState(BaseCellView.STATE_OUTSIDE_MONTH);
            } else {
                cellView.setBackgroundResource(android.R.color.transparent);
                cellView.setText(null);
                cellView.setOnClickListener(null);
            }
        }
        cellView.refreshDrawableState();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setOnDateClickListener(OnDateCellItemClickListener onDateCellItemClickListener) {
        this.onDateCellItemClickListener = onDateCellItemClickListener;
    }

    public void setSelectedItem(SelectedDateItem selectedItem, boolean notify, boolean isUserSelected) {
        this.selectedItem = selectedItem;
        if (disableAutoDateSelection && isUserSelected) {
            this.userSelectedDateItem = selectedItem;
        }
        if (notify) notifyDataSetChanged();
    }

    public SelectedDateItem getSelectedItem() {
        return selectedItem;
    }

    void setMonthEventFetcher(MonthEventFetcher monthEventFetcher) {
        this.monthEventFetcher = monthEventFetcher;
    }

    public void setCellViewDrawer(IDateCellViewDrawer cellViewDrawer) {
        this.cellViewDrawer = cellViewDrawer;
    }

    public void setShowDatesOutsideMonth(boolean showDatesOutsideMonth) {
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        this.notifyDataSetChanged();
    }

    public void setDecorateDatesOutsideMonth(boolean decorateDatesOutsideMonth) {
        this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
        this.notifyDataSetChanged();
    }

    public void setDisableAutoDateSelection(boolean disableAutoDateSelection) {
        this.disableAutoDateSelection = disableAutoDateSelection;
        this.notifyDataSetChanged();
    }

    public void setFirstDayOfTheWeek(int firstDayOfTheWeek) {
        monthDisplayHelper = new MonthDisplayHelper(year, month, firstDayOfTheWeek);
        this.notifyDataSetChanged();
    }

    public SelectedDateItem getUserSelectedItem() {
        return userSelectedDateItem;
    }

    public List<SelectedDateItem> getUserSelectedDateItems() {
        return userSelectedDateItems;
    }

    public void setUserSelectedDateItem(SelectedDateItem selectedItem) {
        this.userSelectedDateItem = selectedItem;
        notifyDataSetChanged();
    }

    public void setDisableTodaySelection(boolean disableTodaySelection) {
        this.disableTodaySelection = disableTodaySelection;
        this.notifyDataSetChanged();
    }

    public void setEnableRangeSelection(boolean enableRangeSelection) {
        this.enableRangeSelection = enableRangeSelection;
        this.notifyDataSetChanged();
    }

    public interface OnDateCellItemClickListener {
        void onDateClick(SelectedDateItem selectedItem);

        void selectedDates(List<SelectedDateItem> selectedDateItems);
    }

    interface MonthEventFetcher {
        List<? extends Event> getEventsForTheDay(int year, int month, int day);
    }

    private class DateClickListener implements View.OnClickListener {

        private int iDay;
        private int iMonth;
        private int iYear;

        public DateClickListener(int day, int month, int year) {
            this.iDay = day;
            this.iMonth = month;
            this.iYear = year;
        }

        @Override
        public void onClick(final View v) {
            selectedItem = new SelectedDateItem(iYear, iMonth, iDay);
            Log.v("selected dates", "" + selectedItem.getDay());

            if (disableAutoDateSelection) {
                userSelectedDateItem = selectedItem;
                if (enableRangeSelection) {
                    Log.v("selected dates", "" + userSelectedDateItem.getDay());
                    if (userSelectedDateItems.contains(selectedItem)) {
                        userSelectedDateItems.remove(selectedItem);
                    } else {
                        if (userSelectedDateItems.size() > 2 && isRangeSelected) {
                            userSelectedDateItems.clear();
                        }
                        userSelectedDateItems.add(selectedItem);
                    }
                    if (userSelectedDateItems.size() < 2)
                        isRangeSelected = false;

                    Log.v("selected dates", "" + userSelectedDateItems.size());
                    if (userSelectedDateItems.size() == 2 && !isRangeSelected) {
                        Calendar calender1 = Calendar.getInstance();
                        SelectedDateItem selectedDateItemOne = userSelectedDateItems.get(0);
                        calender1.set(selectedDateItemOne.getYear(), selectedDateItemOne.getMonth(),
                                selectedDateItemOne.getDay());

                        Calendar calender2 = Calendar.getInstance();
                        SelectedDateItem selectedDateItemTwo = userSelectedDateItems.get(1);
                        calender2.set(selectedDateItemTwo.getYear(), selectedDateItemTwo.getMonth(),
                                selectedDateItemTwo.getDay());
                        List<Calendar> datesBetween = null;
                        if (calender1.after(calender2))
                            datesBetween = getDatesBetweenUsingJava7(calender2.getTime(), calender1.getTime());
                        else
                            datesBetween = getDatesBetweenUsingJava7(calender1.getTime(), calender2.getTime());
                        Log.v("selected datesBetween", "datesBetween " + datesBetween.size());

                        if (datesBetween.size() > 0) {
                            for (int i = 0; i < datesBetween.size(); i++) {
                                SelectedDateItem selectedDateItem = new SelectedDateItem(datesBetween.get(i).get(Calendar.YEAR),
                                        datesBetween.get(i).get(Calendar.MONTH),
                                        datesBetween.get(i).get(Calendar.DAY_OF_MONTH));
                                userSelectedDateItems.add((i + 1), selectedDateItem);
                            }
                        }
                        isRangeSelected = true;
                    }
                }
            }

            notifyDataSetChanged();

            if (onDateCellItemClickListener != null) {
                onDateCellItemClickListener.onDateClick(selectedItem);
                onDateCellItemClickListener.selectedDates(userSelectedDateItems);
            }

        }
    }

    public void setUserSelectedDateItems(List<SelectedDateItem> selectedDateItems) {
        if (userSelectedDateItems.size() > 0)
            userSelectedDateItems.clear();
        userSelectedDateItems.addAll(selectedDateItems);
        notifyDataSetChanged();
    }

    public static List<Calendar> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        List<Calendar> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            calendar.add(Calendar.DATE, 1);
            Date result = calendar.getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
            if (!calendar.equals(endCalendar))
                datesInRange.add(cal);
        }


        return datesInRange;
    }

    public List<SelectedDateItem> getUserSelectedDates() {
        return userSelectedDateItems;
    }
}
*/
