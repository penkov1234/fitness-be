package com.fitness.demo.Repositories;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.IdClasses.UserDailyWorkoutPK;
import com.fitness.demo.Entities.Relations.UserDailyWorkout;
import com.fitness.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UserDailyWorkoutRepository extends JpaRepository<UserDailyWorkout, UserDailyWorkoutPK> {

    @Query(value = "select * from user_daily_workout as udw where udw.user_id = ?1 and udw.daily_workout_id in ?2  and month(udw.date_trained) =?3 and year(udw.date_trained)=?4", nativeQuery = true)
    List<UserDailyWorkout> findAllByUserAndDailyWorkoutInAndDateTrained_MonthAndDateTrained_Year(Long user, List<Long> dailyWorkout, int month, int year);

    UserDailyWorkout findByUserAndDailyWorkoutAndDateTrained(User user, DailyWorkout dailyWorkout, Date date);
    UserDailyWorkout findByUserIdAndDailyWorkoutIdAndDateTrained(Long userId, Long dailyWorkoutId, Date date);

    List<UserDailyWorkout> findAllByUser(User user);
}
