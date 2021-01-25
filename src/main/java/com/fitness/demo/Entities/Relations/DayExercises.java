package com.fitness.demo.Entities.Relations;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.Exercise;
import com.fitness.demo.Entities.IdClasses.DayExercisesPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(DayExercisesPK.class)
public class DayExercises {

    @Id
    @Column(name = "daily_workout_id")
    private Long dailyWorkoutId;

    @Id
    @Column(name = "exercise_id")
    private Long exerciseId;

    @ManyToOne
    @JoinColumn(name = "daily_workout_id", insertable = false, updatable = false)
    private DailyWorkout dailyWorkout;

    @ManyToOne
    @JoinColumn(name = "exercise_id", insertable = false, updatable = false)
    private Exercise exercise;

    @Column(name = "num_sets")
    private Integer numSets;

    @Column(name = "num_reps")
    private Integer numReps;


}
