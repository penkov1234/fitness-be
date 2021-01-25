package com.fitness.demo.Entities;

import com.fitness.demo.Entities.Relations.DayExercises;
import com.fitness.demo.Entities.Relations.UserDailyWorkout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  DailyWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "daily_workout_name")
    private String dailyWorkoutName;

    @Column(name = "week_num")
    private Integer weekNum;

    @Column(name = "day_num")
    private Integer dayNum;

    @OneToMany(mappedBy = "dailyWorkout")
    private List<DayExercises> exerciseList;

    @OneToMany(mappedBy = "dailyWorkout")
    private List<UserDailyWorkout> userDailyWorkouts;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

}
