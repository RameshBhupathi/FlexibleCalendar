package com.p_v.flexiblecalendar;

import android.content.Context;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.entity.VacancyDay;
import com.p_v.flexiblecalendar.view.BaseCellView;
import com.p_v.flexiblecalendar.view.IDateCellViewDrawer;
import com.p_v.fliexiblecalendar.R;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author p-v
 */
class FlexibleCalendarGridAdapter extends BaseAdapter {

    private int year;
    private int month;
    private Context context;
    private MonthDisplayHelper monthDisplayHelper;
    private Calendar calendar;
    private OnDateCellItemClickListener onDateCellItemClickListener;
    private SelectedDateItem selectedItem;
    private SelectedDateItem userSelectedDateItem;
    private MonthEventFetcher monthEventFetcher;
    private MonthVacancyDayFetcher monthVacancyDayFetcher;
    private IDateCellViewDrawer cellViewDrawer;
    private boolean showDatesOutsideMonth;
    private boolean decorateDatesOutsideMonth;
    private boolean disableAutoDateSelection;
    private boolean disableTodaySelection;
    private boolean enableRangeSelection;
    public static boolean isRangeSelected;
    public static List<SelectedDateItem> userSelectedDateItems;
    private static final int SIX_WEEK_DAY_COUNT = 42;


    public FlexibleCalendarGridAdapter(Context context, int year, int month,
                                       boolean showDatesOutsideMonth, boolean decorateDatesOutsideMonth, int startDayOfTheWeek,
                                       boolean disableAutoDateSelection, boolean disableTodaySelection,
                                       boolean enableRangeSelection) {
        this.context = context;
        this.showDatesOutsideMonth = showDatesOutsideMonth;
        this.decorateDatesOutsideMonth = decorateDatesOutsideMonth;
        this.disableAutoDateSelection = disableAutoDateSelection;
        this.disableTodaySelection = disableTodaySelection;
        this.enableRangeSelection = enableRangeSelection;
        userSelectedDateItems = new ArrayList<>();
        initialize(year, month, startDayOfTheWeek);
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

        //Cell State
        int cellState = BaseCellView.SELECTED;
        //day at the current row and col
        int day = monthDisplayHelper.getDayAt(row, col);

        if (isWithinCurrentMonth) {
            Calendar today = Calendar.getInstance();
            // yesterday.add(Calendar.DATE, -1);

            Calendar dayCalendar = Calendar.getInstance();
            dayCalendar.set(year, month, day);
            List<? extends VacancyDay> cellVacTypes = null;
            if (monthVacancyDayFetcher != null)
                cellVacTypes = monthVacancyDayFetcher.getVacancyday(year, month, day);

          /*  if (dayCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                cellType = BaseCellView.PREVIOUS_DATE;
            } else {*/
            if (cellVacTypes != null && cellVacTypes.size() > 0) {
                cellType = cellVacTypes.get(0).getVacDayType();
                cellState = cellVacTypes.get(0).getState();
                Log.v("cType", "" + cellType);
            } else {
                //set to REGULAR if is within current month
                if (today.getTime().compareTo(dayCalendar.getTime()) > 0) {
                    Log.v("date previous", dayCalendar.getTime().toString());
                    cellType = BaseCellView.PREVIOUS_DATE;
                } else {
                    Log.v("date REGULAR", dayCalendar.getTime().toString());
                    cellType = BaseCellView.REGULAR;
                    if (disableAutoDateSelection) {
                        if (userSelectedDateItems.size() > 0) {
                            SelectedDateItem selectedItem = new SelectedDateItem(year, month, day);
                            if (userSelectedDateItems.contains(selectedItem))
                                cellType = BaseCellView.SELECTED;

                        } else if (userSelectedDateItem != null && userSelectedDateItem.getYear() == year
                                && userSelectedDateItem.getMonth() == month
                                && userSelectedDateItem.getDay() == day) {
                            //selected
                            if (!enableRangeSelection)
                                cellType = BaseCellView.SELECTED;

                        }
                    } else {
                        if (selectedItem != null && selectedItem.getYear() == year
                                && selectedItem.getMonth() == month
                                && selectedItem.getDay() == day) {
                            //selected
                            cellType = BaseCellView.SELECTED;
                            Log.v("Cell Type", "selected" + selectedItem.getDateTime().toString());
                        }
                    }
                    if (calendar.get(Calendar.YEAR) == year
                            && calendar.get(Calendar.MONTH) == month
                            && calendar.get(Calendar.DAY_OF_MONTH) == day) {

                        if (!disableTodaySelection) {
                            if (userSelectedDateItem == null)
                                cellType = BaseCellView.SELECTED_TODAY;
                            else if (userSelectedDateItem.getDay() == day)
                                cellType = BaseCellView.SELECTED_TODAY;
                            else
                                cellType = BaseCellView.REGULAR;

                        } else {
                            SelectedDateItem selectedDateItem = new SelectedDateItem(year, month, day);
                            if (!userSelectedDateItems.contains(selectedDateItem))
                                cellType = BaseCellView.REGULAR;
                        }

                    }
                }
            }
        }
        // }
        BaseCellView cellView = cellViewDrawer.getCellView(position, convertView, parent, cellType);
        if (cellView == null) {
            cellView.clearAllStates();
            cellView = (BaseCellView) convertView;
            if (cellView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                cellView = (BaseCellView) inflater.inflate(R.layout.square_cell_layout, null);
            }
        }
        drawDateCell(cellView, day, cellType);
        Log.v("yesterday cellType", "" + cellType);
        return cellView;
    }

    private void drawDateCell(BaseCellView cellView, int day, int cellType) {
        cellView.clearAllStates();
        if (cellType != BaseCellView.OUTSIDE_MONTH) {
            cellView.setText(String.valueOf(day));
            cellView.setOnClickListener(new DateClickListener(cellView, cellType, day, month, year));
            SelectedDateItem currentCellDate = new SelectedDateItem(year, month, day);
            // add events
            if (monthEventFetcher != null) {
                cellView.setEvents(monthEventFetcher.getEventsForTheDay(year, month, day));
            }
            if (monthVacancyDayFetcher != null) {
                if (monthVacancyDayFetcher.getVacancyday(year, month, day) != null)
                    Log.v("Day Fetcher", "Size " + monthVacancyDayFetcher.getVacancyday(year, month, day).size());
                cellView.setVacancyDays(monthVacancyDayFetcher.getVacancyday(year, month, day));
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
                case BaseCellView.PREVIOUS_DATE:
                    Log.v("Case", "BaseCellView.PREVIOUS_DATE");
                    cellView.addState(BaseCellView.STATE_PREVIOUS_DATE);
                    break;
                case BaseCellView.REGISTERED_ABSENCE:
                    Log.v("Case", "BaseCellView.PREVIOUS_DATE");
                    cellView.addState(BaseCellView.STATE_REGISTERED_ABSENCE);
                    break;
                case BaseCellView.REGISTERED_CARE:
                    Log.v("Case", "BaseCellView.PREVIOUS_DATE");
                    if (userSelectedDateItems != null && userSelectedDateItems.size() > 0) {
                        if (userSelectedDateItems.contains(currentCellDate))
                            cellView.addState(BaseCellView.STATE_SELECTED);
                        else
                            cellView.addState(BaseCellView.STATE_REGISTERED_CARE);
                    } else
                        cellView.addState(BaseCellView.STATE_REGISTERED_CARE);
                    break;
                case BaseCellView.VACANCY_AVAILABLE:
                    Log.v("Case", "BaseCellView.PREVIOUS_DATE");
                    cellView.addState(BaseCellView.STATE_VACANCY_CARE_AVAILABLE);
                    break;
                case BaseCellView.VACANCY_NOT_AVAILABLE:
                    Log.v("Case", "BaseCellView.PREVIOUS_DATE");
                    cellView.addState(BaseCellView.STATE_VACANCY_CARE_NOT_AVAILABLE);
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
                    cellView.setOnClickListener(new DateClickListener(cellView, cellType, day, temp[1], temp[0]));
                } else {
                    FlexibleCalendarHelper.previousMonth(year, month, temp);
                    cellView.setOnClickListener(new DateClickListener(cellView, cellType, day, temp[1], temp[0]));
                }

                if (decorateDatesOutsideMonth && monthEventFetcher != null) {
                    cellView.setEvents(monthEventFetcher.getEventsForTheDay(temp[0], temp[1], day));
                }

                if (decorateDatesOutsideMonth && monthVacancyDayFetcher != null) {
                    cellView.setVacancyDays(monthVacancyDayFetcher.getVacancyday(temp[0], temp[1], day));
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

    private class DateClickListener implements View.OnClickListener {

        private int iDay;
        private int iMonth;
        private int iYear;
        private BaseCellView baseCellView;
        private int cellTYpe;

        public DateClickListener(BaseCellView cellView, int cellType, int day, int month, int year) {
            this.iDay = day;
            this.iMonth = month;
            this.iYear = year;
            this.baseCellView = cellView;
            this.cellTYpe = cellType;
        }

        @Override
        public void onClick(final View v) {
            selectedItem = new SelectedDateItem(iYear, iMonth, iDay);
            if (cellTYpe == BaseCellView.PREVIOUS_DATE) {
            } else if (cellTYpe == BaseCellView.REGISTERED_ABSENCE) {
                Log.v("Selected", "" + cellTYpe);
                if (onDateCellItemClickListener != null) {
                    Tooltip.Builder builder = new Tooltip.Builder(baseCellView)
                            .setCornerRadius(10f)
                            .setGravity(Gravity.BOTTOM)
                            .setCancelable(true)
                            .setBackgroundColor(context.getResources().getColor(R.color.date_color))
                            .setText(String.valueOf("It is yet another very simple tool tip!"));
                    builder.show();
                    //onDateCellItemClickListener.onDateClick(selectedItem,baseCellView);
                }
            } else {
                Calendar today = Calendar.getInstance();
                //yesterday.add(Calendar.DATE, -1);
                if (today.getTime().compareTo(selectedItem.getDateTime()) <= 0) {
                    userSelectedDateItem = selectedItem;
                    if (disableAutoDateSelection) {
                        if (enableRangeSelection) {
                            if (userSelectedDateItems.contains(selectedItem)) {
                                userSelectedDateItems.remove(selectedItem);
                            } else {
                                if (userSelectedDateItems.size() >= 2 && isRangeSelected) {
                                    if (!isDateInBetween(userSelectedDateItems.get(0),
                                            userSelectedDateItems.get(userSelectedDateItems.size() - 1), selectedItem)) {
                                        userSelectedDateItems.clear();
                                    }
                                }
                                userSelectedDateItems.add(selectedItem);
                                Collections.sort(userSelectedDateItems, comparator);
                            }
                            if (userSelectedDateItems.size() < 2)
                                isRangeSelected = false;

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
                                    datesBetween = getDatesBetween(calender2.getTime(), calender1.getTime());
                                else
                                    datesBetween = getDatesBetween(calender1.getTime(), calender2.getTime());

                                if (datesBetween.size() > 0) {
                                    for (int i = 0; i < datesBetween.size(); i++) {
                                        SelectedDateItem selectedDateItem = new SelectedDateItem(datesBetween.get(i).get(Calendar.YEAR),
                                                datesBetween.get(i).get(Calendar.MONTH),
                                                datesBetween.get(i).get(Calendar.DAY_OF_MONTH));
                                        userSelectedDateItems.add((i + 1), selectedDateItem);
                                        Log.v("selected Dates", selectedDateItem.getDateTime().toString());
                                    }
                                }
                                Collections.sort(userSelectedDateItems, comparator);
                                isRangeSelected = true;
                            }
                        }
                    }
                    notifyDataSetChanged();

                    if (onDateCellItemClickListener != null) {
                        onDateCellItemClickListener.onDateClick(selectedItem, baseCellView);
                    }
                }
            }
        }
    }

    public interface OnDateCellItemClickListener {
        void onDateClick(SelectedDateItem selectedItem, BaseCellView baseCellView);
    }

    interface MonthEventFetcher {
        List<? extends Event> getEventsForTheDay(int year, int month, int day);
    }

    interface MonthVacancyDayFetcher {
        List<? extends VacancyDay> getVacancyday(int year, int month, int day);

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

    void setMonthVacDayFetcher(MonthVacancyDayFetcher monthVacancyDayFetcher) {
        this.monthVacancyDayFetcher = monthVacancyDayFetcher;
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

    public static List<Calendar> getDatesBetween(
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

    public static boolean isDateInBetween(final SelectedDateItem min, final SelectedDateItem max,
                                          final SelectedDateItem date) {
        //Minimum date
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(min.getYear(), min.getMonth(), min.getDay());
        //Maximum date
        Calendar maxCalendar = Calendar.getInstance();
        maxCalendar.set(max.getYear(), max.getMonth(), max.getDay());
        //date
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.set(date.getYear(), date.getMonth(), date.getDay());

        // final Date min, final Date max, final Date date
        return dateCalendar.after(minCalendar) && dateCalendar.before(maxCalendar);
    }

    Comparator comparator = new Comparator<SelectedDateItem>() {
        @Override
        public int compare(SelectedDateItem selectedDateItem, SelectedDateItem t1) {
            return selectedDateItem.getDateTime().compareTo(t1.getDateTime());
        }
    };
}
