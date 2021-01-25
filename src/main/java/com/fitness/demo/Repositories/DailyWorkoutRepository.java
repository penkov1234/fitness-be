package com.fitness.demo.Repositories;

import com.fitness.demo.Entities.DailyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyWorkoutRepository extends JpaRepository<DailyWorkout, Long> {
}
