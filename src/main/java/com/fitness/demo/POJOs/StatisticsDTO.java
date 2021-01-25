package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {

    public List<GraphDTO> graph;
    public List<WorkoutNameAndSubsPOJO> totalSubsPerWorkout;
    public Float averageWorkoutTime;
    public Integer totalWorkoutPlansWithCompletionRate;
    public Integer totalFinishedDailyWorkouts;
    public Integer totalFailedDailyWorkouts;
}
