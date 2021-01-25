package com.fitness.demo.Entities.Relations;

import com.fitness.demo.Entities.IdClasses.UserSubscriptionPK;
import com.fitness.demo.Entities.User;
import com.fitness.demo.Entities.WorkoutPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserSubscriptionPK.class)
public class UserSubscription {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "workout_plan_id")
    private Long workoutPlanId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_plan_id", insertable = false, updatable = false)
    private WorkoutPlan workoutPlan;

    @Id
    @Column(name = "date_subscribed")
    private Date dateSubscribed;

    @Column(name = "is_completed")
    private Boolean isCompleted;


}
