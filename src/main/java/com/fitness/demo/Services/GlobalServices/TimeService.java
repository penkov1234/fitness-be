package com.fitness.demo.Services.GlobalServices;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.*;

@Service
public class TimeService {

    Calendar cal = Calendar.getInstance();

    public static enum TIME_UNIT {
        MILLISECONDS,
        SECONDS,
        MINUTES,
        HOURS,
    }

    /**
     * Simple method to get current date in a timestamp
     * @return Timestamp with the generation date
     */
    public Timestamp getCurrentTimeAsTimestamp(){
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public long getFirstDayOfThisWeek(){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        // start of the next week
        // cal.add(Calendar.WEEK_OF_YEAR, 1);

        return cal.getTimeInMillis();
    }


    public long getLastDayOfWeek(Long currentTime){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal.setTimeInMillis(currentTime);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        cal.set(Calendar.HOUR_OF_DAY, 23); // ! clear would not reset the hour of day !
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        // start of the next week
        // cal.add(Calendar.WEEK_OF_YEAR, 1);

        return cal.getTimeInMillis();
    }

    public long getFirstDayOfThisMonth(){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(DAY_OF_MONTH, 1);

        // get start of the next month
        // cal.add(Calendar.MONTH, 1);
        return cal.getTimeInMillis();
    }
    public String getNameOfFirstDayOfThisMonth(){
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(DAY_OF_MONTH, 1);

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(new Date(cal.getTimeInMillis()));
    }
    public String getNameOfFirstDayOfCustomMonth(int month, int year){
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(MONTH, month -1);
        cal.set(YEAR, year);
        cal.set(DAY_OF_MONTH, 1);

        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(new Date(cal.getTimeInMillis()));
    }
    public long getStartOfNextWeek(Long currentTime){
        cal.setTimeInMillis(currentTime);

        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            cal.add(Calendar.DATE, 1);
        }

        return cal.getTimeInMillis();
    }
    public long getLastDayOfThisMonth(){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(DAY_OF_MONTH, cal.getActualMaximum(DAY_OF_MONTH));

        // get start of the next month
        // cal.add(Calendar.MONTH, 1);
        return cal.getTimeInMillis();
    }

    public long getFirstDayOfThisYear(){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_YEAR, 1);

        return cal.getTimeInMillis();
    }

    public boolean isTuesday(Long timestamp){
        cal.setTimeInMillis(timestamp);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
            return true;
        }
        else {
            return false;
        }
    }
    public Date getDateOfCustomMonthAndYear(int month, int year){
        Calendar today = Calendar.getInstance();

        cal.clear();
        cal.set(YEAR, year);
        cal.set(MONTH, month - 1);
        cal.set(DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    public long getStartOfNextMonth(){
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(System.currentTimeMillis());

        cal.clear();
        cal.set(YEAR, today.get(YEAR));
        cal.set(MONTH, today.get(MONTH) + 1);
        cal.set(DAY_OF_MONTH, 1);

        return cal.getTimeInMillis();
    }
    public long getLastDayOfThisYear(){
        //CLEARS THE VALUES OF THE HOUR, MINUTES, SECONDS AND MILLISECONDS IN ORDER TO GET JUST THE DAY WITHOUT TIME
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

        return cal.getTimeInMillis();
    }
    public long getStartOfCurrentDay(){
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTimeInMillis();
    }
    public long getEndOfCurrentDay(){
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTimeInMillis();
    }

    public long getYesterday(){
        cal.add(Calendar.DATE, -1);

        return cal.getTimeInMillis();
    }
    public long getNextDayFromToday(Integer skipDays){
        //if skip days = 0, then the function returns the very next day.
        //if skip days > 0, then the function returns tomorrow + skip days
        cal.add(Calendar.DATE,  1 + skipDays);

        return cal.getTimeInMillis();
    }

    public long getNextDayFromSpecificDay(Long dayInMills, Integer skipDays){
        cal.setTimeInMillis(dayInMills);

        cal.add(Calendar.DATE,  1 + skipDays);
        cal.set(HOUR_OF_DAY, 0);
        return cal.getTimeInMillis();
    }

    public long getPreviousDayFromTimestamp(Timestamp timestamp){
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DATE, -1);

        return cal.getTimeInMillis();
    }

    public int getCurrentMonth(){
        cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);

        return month + 1;
    }
    public int getCurrentYear(){
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        return year;
    }
    public List<Timestamp> initializeTimestampWithPeriod(String period) throws ParseException {

        List<Timestamp> timestampList = new ArrayList<>();

        switch (period) {
            case "DAY":
                timestampList.add(new Timestamp(getStartOfCurrentDay()));
                timestampList.add(new Timestamp(getEndOfCurrentDay()));
                break;
            case "WEEK":
                timestampList.add(new Timestamp(getFirstDayOfThisWeek()));
                timestampList.add(new Timestamp(getLastDayOfWeek(System.currentTimeMillis())));
                break;
            case "MONTH":
                timestampList.add(new Timestamp(getFirstDayOfThisMonth()));
                timestampList.add(new Timestamp(getLastDayOfThisMonth()));
                break;
            case "YEAR":
                timestampList.add(new Timestamp(getFirstDayOfThisYear()));
                timestampList.add(new Timestamp(getLastDayOfThisYear()));
                break;
            default:
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse("01-01-2000");
                long time = date.getTime();
                timestampList.add(new Timestamp(time));
                timestampList.add(new Timestamp(System.currentTimeMillis()));
                break;
        }
        return timestampList;
    }

    public Long getDifferenceBetweenTwoTimestamps(Timestamp start, Timestamp end, TIME_UNIT unit){
        Long firstTimestampInSeconds = start.getTime() / 1000;
        Long secondTimestampInSeconds = end.getTime() / 1000;

        //result in milliseconds
        Long res = secondTimestampInSeconds - firstTimestampInSeconds;

        //if unit is other than milliseconds
        if (unit.equals(TIME_UNIT.HOURS)){
            res = res / (1000 * 60 * 60 * 24);
        }
        else if (unit.equals(TIME_UNIT.MINUTES)){
            res = res / (60000);
        }
        else if (unit.equals(TIME_UNIT.SECONDS)){
            res = res / (1000);
        }

        return res;
    }


    public String createDateFromTimestamp(Timestamp timestamp){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDateTime date = timestamp.toLocalDateTime();
        String dateString = timestamp.toLocalDateTime().format(formatter);
        String[] temp = dateString.split(" ");
        String month = date.getMonth().name();
        month = (month.substring(0,1) + month.substring(1).toLowerCase()).substring(0,3);
        dateString = temp[0] + " " + month + " ";
        dateString += temp[2].replace("20", "'");

        return dateString;

    }
}

