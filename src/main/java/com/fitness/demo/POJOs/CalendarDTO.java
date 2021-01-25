package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDTO {

    public String monthName;
    public Integer monthNum;
    public Integer currentDay;
    public String firstDayName;
    public Integer numOfDays;
    public Integer year;
}
