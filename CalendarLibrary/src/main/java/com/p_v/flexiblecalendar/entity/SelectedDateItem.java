package com.p_v.flexiblecalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author p-v
 */
public class SelectedDateItem implements Parcelable {

    private int day;
    private int month;
    private int year;

    public SelectedDateItem(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public SelectedDateItem clone() {
        return new SelectedDateItem(year, month, day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectedDateItem that = (SelectedDateItem) o;

        if (day != that.day) return false;
        if (month != that.month) return false;
        return year == that.year;

    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    public Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(getDateTime());
    }

    public String getToolTipDateFormatString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        return simpleDateFormat.format(getDateTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.day);
        dest.writeInt(this.month);
        dest.writeInt(this.year);
    }

    protected SelectedDateItem(Parcel in) {
        this.day = in.readInt();
        this.month = in.readInt();
        this.year = in.readInt();
    }

    public static final Parcelable.Creator<SelectedDateItem> CREATOR = new Parcelable.Creator<SelectedDateItem>() {
        @Override
        public SelectedDateItem createFromParcel(Parcel source) {
            return new SelectedDateItem(source);
        }

        @Override
        public SelectedDateItem[] newArray(int size) {
            return new SelectedDateItem[size];
        }
    };

    public String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateTime());
        return DateFormatSymbols.getInstance().getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];
    }
}
