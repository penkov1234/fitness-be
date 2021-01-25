package com.fitness.demo.POJOs;

import com.fitness.demo.Entities.WorkoutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilteredWorkoutPlansDTO {

    public List<WorkoutPlanDTO> others;
    public List<WorkoutPlanDTO> yours;
}
