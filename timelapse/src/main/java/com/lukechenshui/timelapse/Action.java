package com.lukechenshui.timelapse;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.Duration;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by luke on 9/12/16.
 */
public class Action extends RealmObject{
    @Ignore
    DateTime startTime;
    @Ignore
    DateTime endTime;
    @Ignore
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

    public void incrementByOneSecond(){
        endTime = endTime.plusSeconds(1);
    }

    public void calculateDuration(){
        duration = Seconds.secondsBetween(startTime, endTime);
    }

    public String getDifferenceInSeconds(){
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
}
