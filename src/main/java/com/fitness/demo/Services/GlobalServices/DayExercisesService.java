package com.fitness.demo.Services.GlobalServices;

import com.fitness.demo.Entities.Relations.DayExercises;
import com.fitness.demo.POJOs.DayExerciseDTO;
import com.fitness.demo.Repositories.DayExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DayExercisesService {

    private final DayExerciseRepository dayExerciseRepository;

    public DayExercisesService(DayExerciseRepository dayExerciseRepository) {
        this.dayExerciseRepository = dayExerciseRepository;
    }

    public DayExercises createDayExercises(DayExerciseDTO dayExerciseDTO, Long dailyWorkoutId){
        DayExercises dayExercises = new DayExercises();
        dayExercises.setExerciseId(dayExerciseDTO.getExerciseId());
        dayExercises.setDailyWorkoutId(dailyWorkoutId);
        dayExercises.setNumReps(dayExerciseDTO.getNumReps());
        dayExercises.setNumSets(dayExerciseDTO.getNumSets());

        dayExerciseRepository.saveAndFlush(dayExercises);
        return dayExercises;
    }

    public List<DayExerciseDTO> createFormattedDayExercises(List<DayExercises> dayExercises){
        List<DayExerciseDTO> dayExerciseDTOS = new ArrayList<>();

        for (DayExercises de : dayExercises){
            DayExerciseDTO ded = new DayExerciseDTO();

            ded.setExerciseId(de.getExerciseId());
            ded.setNumReps(de.getNumReps());
            ded.setName(de.getExercise().getName());
            ded.setNumSets(de.getNumSets());
            ded.setImageUrl(de.getExercise().getImageUrl());
            ded.setDescription(de.getExercise().getDescription());
            dayExerciseDTOS.add(ded);
        }

        return dayExerciseDTOS;
    }
}
