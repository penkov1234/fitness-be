package com.fitness.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.demo.Entities.Relations.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name ="frequency")
    private Integer frequency;

    @Column(name = "difficulty")
    private String difficulty;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "total_subscriptions")
    private Integer totalSubscriptions;

    @OneToMany(mappedBy = "workoutPlan")
    @JsonIgnore
    private List<UserSubscription> userSubscriptionList;

    @OneToMany(mappedBy = "workoutPlan")
    @JsonIgnore
    private List<DailyWorkout> dailyWorkouts;

}
