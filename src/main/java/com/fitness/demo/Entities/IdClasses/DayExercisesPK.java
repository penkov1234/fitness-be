package com.fitness.demo.Entities.IdClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DayExercisesPK implements Serializable {

    public Long dailyWorkoutId;

    public Long exerciseId;
}
