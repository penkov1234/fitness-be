package com.fitness.demo.Repositories;

import com.fitness.demo.Entities.IdClasses.DayExercisesPK;
import com.fitness.demo.Entities.Relations.DayExercises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayExerciseRepository extends JpaRepository<DayExercises, DayExercisesPK> {
}
