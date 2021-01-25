package com.fitness.demo.Repositories;

import com.fitness.demo.Entities.User;
import com.fitness.demo.Entities.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    List<WorkoutPlan> findAllByFrequencyAndDifficultyAndIsPublishedIsTrueAndAuthorIsNot(int frequency, String difficulty, User author);

    List<WorkoutPlan> findAllByAuthor(User author);
    List<WorkoutPlan> findAllByAuthorAndFrequencyAndDifficulty(User author,int frequency, String difficulty);

}
