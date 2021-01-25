package com.fitness.demo.Services.GlobalServices;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.Relations.DayExercises;
import com.fitness.demo.Entities.Relations.UserDailyWorkout;
import com.fitness.demo.Entities.WorkoutPlan;
import com.fitness.demo.POJOs.DailyWorkoutDTO;
import com.fitness.demo.POJOs.DayExerciseDTO;
import com.fitness.demo.Repositories.DailyWorkoutRepository;
import com.fitness.demo.Repositories.UserDailyWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyWorkoutService {

    private final DailyWorkoutRepository dailyWorkoutRepository;

    private final DayExercisesService dayExercisesService;

    private final UserDailyWorkoutRepository userDailyWorkoutRepository;


    public DailyWorkoutService(DailyWorkoutRepository dailyWorkoutRepository, DayExercisesService dayExercisesService, UserDailyWorkoutRepository userDailyWorkoutRepository) {
        this.dailyWorkoutRepository = dailyWorkoutRepository;
        this.dayExercisesService = dayExercisesService;
        this.userDailyWorkoutRepository = userDailyWorkoutRepository;
    }

    public DailyWorkout createDailyWorkout(DailyWorkoutDTO dailyWorkoutDTO, WorkoutPlan workoutPlan){
        List<DayExercises> dayExercisesList = new ArrayList<>();

        DailyWorkout dailyWorkout = new DailyWorkout();
        dailyWorkout.setDailyWorkoutName(dailyWorkoutDTO.getDailyWorkoutName());
        dailyWorkout.setDayNum(dailyWorkoutDTO.getDayNum());
        dailyWorkout.setWeekNum(dailyWorkoutDTO.getWeekNum());
        dailyWorkout.setWorkoutPlan(workoutPlan);

        dailyWorkout = dailyWorkoutRepository.saveAndFlush(dailyWorkout);

        for (DayExerciseDTO det : dailyWorkoutDTO.getExercises()){
            dayExercisesList.add(dayExercisesService.createDayExercises(det, dailyWorkout.getId()));
        }

        dailyWorkout.setExerciseList(dayExercisesList);

        return dailyWorkout;
    }

    public DailyWorkoutDTO updateDailyWorkout(Long userId, Long dailyWorkoutId, Date date, Float hoursSpent, Boolean isCompleted) {
        UserDailyWorkout udw = userDailyWorkoutRepository.findByUserIdAndDailyWorkoutIdAndDateTrained(userId, dailyWorkoutId, date);

        if (hoursSpent != null){
            udw.setHoursSpent(hoursSpent);
        }
        if (isCompleted != null){
            udw.setIsCompleted(isCompleted);
        }

        udw = userDailyWorkoutRepository.save(udw);

        return createFormattedDailyWorkout(udw);
    }
    public DailyWorkoutDTO createFormattedDailyWorkout(UserDailyWorkout udw){
        DailyWorkout dw = udw.getDailyWorkout();

        DailyWorkoutDTO dailyWorkoutDTO = new DailyWorkoutDTO();
        dailyWorkoutDTO.setDailyWorkoutName(dw.getDailyWorkoutName());
        dailyWorkoutDTO.setWeekNum(dw.getWeekNum());
        dailyWorkoutDTO.setDayNum(dw.getDayNum());
        dailyWorkoutDTO.setDailyWorkoutId(dw.getId());
        dailyWorkoutDTO.setDateTrained(udw.getDateTrained());
        dailyWorkoutDTO.setIsCompleted(udw.getIsCompleted());
        dailyWorkoutDTO.setHoursSpent(udw.getHoursSpent());
        dailyWorkoutDTO.setDayInMonth(udw.getDateTrained().toLocalDate().getDayOfMonth());
        dailyWorkoutDTO.setExercises(dayExercisesService.createFormattedDayExercises(dw.getExerciseList()));

        return dailyWorkoutDTO;
    }

}

