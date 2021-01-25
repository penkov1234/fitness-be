package com.fitness.demo.Services.GlobalServices;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.Relations.DayExercises;
import com.fitness.demo.Entities.User;
import com.fitness.demo.Entities.WorkoutPlan;
import com.fitness.demo.POJOs.DailyWorkoutDTO;
import com.fitness.demo.POJOs.DayExerciseDTO;
import com.fitness.demo.POJOs.FilteredWorkoutPlansDTO;
import com.fitness.demo.POJOs.WorkoutPlanDTO;
import com.fitness.demo.Repositories.WorkoutPlanRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    private final DailyWorkoutService dailyWorkoutService;

    private final EntityManager entityManager;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, DailyWorkoutService dailyWorkoutService, EntityManager entityManager) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.dailyWorkoutService = dailyWorkoutService;
        this.entityManager = entityManager;
    }

    public WorkoutPlan saveWorkout(User user, WorkoutPlanDTO workoutPlan){
        return createWorkoutPlan(user, workoutPlan);
    }

    private WorkoutPlan createWorkoutPlan(User user, WorkoutPlanDTO workoutPlan){
        List<DailyWorkout> dailyWorkoutList = new ArrayList<>();
        WorkoutPlan wp = new WorkoutPlan();
        wp.setAuthor(user);
        wp.setDifficulty(workoutPlan.getDifficulty());
        wp.setFrequency(workoutPlan.getFrequency());
        wp.setName(workoutPlan.getName());
        wp.setIsPublished(workoutPlan.getIsPublished());
        workoutPlanRepository.saveAndFlush(wp);

        for (DailyWorkoutDTO dwt: workoutPlan.getDailyWorkouts()){
            dailyWorkoutList.add(dailyWorkoutService.createDailyWorkout(dwt, wp));
        }

        wp.setDailyWorkouts(dailyWorkoutList);
        return wp;
    }

    public WorkoutPlan getById(Long workoutPlanId){
       Optional<WorkoutPlan> wp = workoutPlanRepository.findById(workoutPlanId);

       if (wp.isPresent()){
           return wp.get();
       }
       else {
           throw new EntityNotFoundException();
       }
    }

    public FilteredWorkoutPlansDTO getWorkoutPlansFiltered(User user, Integer frequency, String difficulty){
        List<WorkoutPlan> workoutPlans = workoutPlanRepository.findAllByFrequencyAndDifficultyAndIsPublishedIsTrueAndAuthorIsNot(frequency, difficulty, user);
        workoutPlans.addAll(workoutPlanRepository.findAllByAuthorAndFrequencyAndDifficulty(user, frequency, difficulty));

        List<WorkoutPlanDTO> others = new ArrayList<>();
        List<WorkoutPlanDTO> yours = new ArrayList<>();
        FilteredWorkoutPlansDTO dto = new FilteredWorkoutPlansDTO();
        for (WorkoutPlan wp : workoutPlans){
            WorkoutPlanDTO workoutPlanDTO = createFormattedWorkoutPlan(wp);
            if (workoutPlanDTO.getAuthorId().equals(user.getId())){
                yours.add(workoutPlanDTO);
            }
            else {
                others.add(workoutPlanDTO);
            }
        }
        dto.setOthers(others);
        dto.setYours(yours);
        return dto;
    }

    private WorkoutPlanDTO createFormattedWorkoutPlan(WorkoutPlan workoutPlan){
        WorkoutPlanDTO workoutPlanDTO = new WorkoutPlanDTO();
        List<DailyWorkoutDTO> dailyWorkoutDTOS = new ArrayList<>();
        workoutPlanDTO.setId(workoutPlan.getId());
        workoutPlanDTO.setAuthor(workoutPlan.getAuthor().getName() + " " + workoutPlan.getAuthor().getLastName());
        workoutPlanDTO.setAuthorId(workoutPlan.getAuthor().getId());
        workoutPlanDTO.setDifficulty(workoutPlan.getDifficulty());
        workoutPlanDTO.setFrequency(workoutPlan.getFrequency());
        workoutPlanDTO.setIsPublished(workoutPlan.getIsPublished());
        workoutPlanDTO.setName(workoutPlan.getName());

        for (DailyWorkout dw : workoutPlan.getDailyWorkouts()){
            DailyWorkoutDTO dailyWorkoutDTO = createFormattedDailyWorkout(dw);
            dailyWorkoutDTOS.add(dailyWorkoutDTO);
        }

        workoutPlanDTO.setDailyWorkouts(dailyWorkoutDTOS);

        return workoutPlanDTO;
    }
    private DailyWorkoutDTO createFormattedDailyWorkout(DailyWorkout dw){
        DailyWorkoutDTO dailyWorkoutDTO = new DailyWorkoutDTO();
        dailyWorkoutDTO.setDailyWorkoutName(dw.getDailyWorkoutName());
        dailyWorkoutDTO.setWeekNum(dw.getWeekNum());
        dailyWorkoutDTO.setDayNum(dw.getDayNum());
        dailyWorkoutDTO.setExercises(createFormattedDayExercises(dw.getExerciseList()));

        return dailyWorkoutDTO;
    }
    private List<DayExerciseDTO> createFormattedDayExercises(List<DayExercises> dayExercises){
        List<DayExerciseDTO> dayExerciseDTOS = new ArrayList<>();

        for (DayExercises de : dayExercises){
            DayExerciseDTO ded = new DayExerciseDTO();
            ded.setImageUrl(de.getExercise().getImageUrl());
            ded.setDescription(de.getExercise().getDescription());
            ded.setExerciseId(de.getExerciseId());
            ded.setNumReps(de.getNumReps());
            ded.setName(de.getExercise().getName());
            ded.setNumSets(de.getNumSets());

            dayExerciseDTOS.add(ded);
        }

        return dayExerciseDTOS;
    }
    public void updateWorkoutPlan(WorkoutPlan workoutPlan){
        workoutPlanRepository.saveAndFlush(workoutPlan);
    }

    public List<WorkoutPlan> getAllByUser(User user){
       return workoutPlanRepository.findAllByAuthor(user);
    }
}
