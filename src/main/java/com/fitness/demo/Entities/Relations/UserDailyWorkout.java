package com.fitness.demo.Entities.Relations;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.IdClasses.UserDailyWorkoutPK;
import com.fitness.demo.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserDailyWorkoutPK.class)
public class UserDailyWorkout {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "daily_workout_id")
    private Long dailyWorkoutId;

    @Id
    @Column(name = "date_trained")
    private Date dateTrained;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "hours_spent")
    private Float hoursSpent;



    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "daily_workout_id", insertable = false, updatable = false)
    private DailyWorkout dailyWorkout;
}
