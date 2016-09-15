package com.lukechenshui.timelapse;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by luke on 9/12/16.
 */
public class Action implements Parcelable {

    public static final Parcelable.Creator<Action> CREATOR
            = new Parcelable.Creator<Action>() {
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        public Action[] newArray(int size) {
            return new Action[size];
        }
    };
    DateTime startTime;
    DateTime endTime;
    Seconds duration;
    String name;

    public Action() {
        startTime = new DateTime();
        endTime = new DateTime();
    }

    public Action(String name) {
        this();
        this.name = name;
    }

    private Action(Parcel source) {
        setName(source.readString());
        setStartTime(source.readLong());
        setEndTime(source.readLong());
        setDuration(source.readInt());
    }

    public void incrementByOneSecond(){
        endTime = endTime.plusSeconds(1);
    }

    public void calculateDuration(){
        duration = Seconds.secondsBetween(startTime, endTime);
    }

    public String getFormattedDate(DateTime date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
        return formatter.print(date);
    }

    public String getFormattedStartDate() {
        return getFormattedDate(startTime);
    }

    public String getFormattedEndDate() {
        return getFormattedDate(endTime);
    }

    public String getFullyFormattedDate(DateTime date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy H:mm:ss");
        return formatter.print(date);
    }

    public String getFullyFormattedStartDate() {
        return getFullyFormattedDate(startTime);
    }

    public String getFullyFormattedEndDate() {
        return getFullyFormattedDate(endTime);
    }

    public String getFormattedDifference() {
        Period period = new Period(startTime, endTime);
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix("d")
                .appendHours().appendSuffix("h")
                .appendMinutes().appendSuffix("m")
                .appendSeconds().appendSuffix("s")
                .toFormatter();
        return formatter.print(period.normalizedStandard());
    }

    public long getStartTime() {
        return startTime.getMillis();
    }

    public void setStartTime(long millis) {
        this.startTime = new DateTime(millis);
    }

    public long getEndTime() {
        return endTime.getMillis();
    }

    public void setEndTime(long millis) {
        this.endTime = new DateTime(millis);
    }

    public int getDuration() {
        return duration.getSeconds();
    }

    public void setDuration(int duration) {
        this.duration = Seconds.seconds(duration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Action action = (Action) o;

        return name != null ? name.equals(action.name) : action.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeLong(getStartTime());
        dest.writeLong(getEndTime());
        dest.writeInt(getDuration());
    }



}
