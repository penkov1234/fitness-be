package com.fitness.demo.Services.GlobalServices;

import com.fitness.demo.Entities.Exercise;
import com.fitness.demo.Repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }
}
