# FlexibleCalendar With Range Selection
A customizable calendar for android with customizable events and Range Selection Of dates.

# Video Demo

[![IMAGE ALT TEXT](http://img.youtube.com/vi/sVyBzVyupds/0.jpg)](http://www.youtube.com/watch?v=sVyBzVyupds "Range Selection of Dates in Android")



**Customizations:**

Display events in different styles.

![Circular Multiple Events](demo/screenshot-1.jpg) &nbsp; ![Events with count](demo/screenshot-4.jpg)

Customize the cells and events by extending the class **BaseCellView**. 

Existing cells include **CircularEventCellView**, **SquareCellView** and **EventCountCellView**.

Choose whether to show dates outside month or not by setting the `showDatesOutsideMonth` flag,
 using `FlexibleCalendarView#showDatesOutsideMonth()` method.

Set the first day of the week using `FlexibleCalendarView#setStartDayOfTheWeek` method or through xml
 by setting the attribute `startDayOfTheWeek`.

Disable auto date selection on month change by using `FlexibleCalendarView#disableAutoDateSelection` method or through xml by using `disableAutoDateSelection`.

![Outside Month](demo/screenshot-2.jpg) &nbsp; ![Without Outside Month](demo/screenshot-3.jpg)

**Navigate the calendar:**

`selectDate` - Scroll and select the provided date
`goToCurrentDay` - Go the the current day <br/>
`goToCurrentMonth` - Go the the current month <br/>
`moveToNextDate` - Select the date after the current selection <br/>
`moveToPreviousDate` - Select the date before the current selection <br/>
`moveToNextMonth` - Move the view to the next month from the current visible month <br/>
`moveToPreviousMonth` - Move the view to the previous month from the current visible month <br/>

**Customizable display cells with different states:**

`state_date_regular` - Regular date<br/>
`state_date_today` - Today's date<br/>
`state_date_selected` - Selected date<br/>
`state_date_outside_month` - Date lying outside month but in current month view<br/>

**Sample cell background:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:flexible="http://schemas.android.com/apk/res-auto">
    <item flexible:state_date_regular="true"
        android:drawable="@drawable/cell_purple_background"/>
    <item flexible:state_date_today="true"
        android:drawable="@drawable/cell_gray_background"/>
    <item flexible:state_date_selected="true"
        android:drawable="@drawable/cell_red_background"/>
    <item flexible:state_date_outside_month="true"
        android:drawable="@drawable/cell_blue_background"/>
</selector>
```
