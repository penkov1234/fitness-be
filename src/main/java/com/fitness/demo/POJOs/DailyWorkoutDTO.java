package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutDTO {

    public Long dailyWorkoutId;

    public String dailyWorkoutName;

    public Date dateTrained;

    public Integer weekNum;

    public Integer dayNum;

    public Float hoursSpent;

    public Boolean isCompleted;

    public Integer dayInMonth;

    public List<DayExerciseDTO> exercises;
}
