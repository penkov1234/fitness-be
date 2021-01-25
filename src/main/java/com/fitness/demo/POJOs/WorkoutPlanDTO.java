package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlanDTO {

    public Long id;

    public String name;

    public Integer frequency;

    public String difficulty;

    public String author;

    public Long authorId;

    public CalendarDTO calendarDTO;

    public Boolean isPublished;

    public List<DailyWorkoutDTO> dailyWorkouts;

}
