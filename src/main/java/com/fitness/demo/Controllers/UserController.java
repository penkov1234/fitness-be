package com.fitness.demo.Controllers;

import com.fitness.demo.Entities.User;
import com.fitness.demo.Entities.WorkoutPlan;
import com.fitness.demo.POJOs.*;
import com.fitness.demo.Services.GlobalServices.*;
import com.fitness.demo.Services.SecurityServices.CustomUserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final WorkoutPlanService workoutPlanService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final DailyWorkoutService dailyWorkoutService;

    private final TimeService timeService;

    public UserController(WorkoutPlanService workoutPlanService, CustomUserDetailsService userDetailsService, UserService userService, DailyWorkoutService dailyWorkoutService, TimeService timeService) {
        this.workoutPlanService = workoutPlanService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.dailyWorkoutService = dailyWorkoutService;
        this.timeService = timeService;
    }

    @PostMapping("/workouts/new")
    public void createWorkout(@RequestBody WorkoutPlanDTO workoutPlan){
        User user = userDetailsService.getAuthenticatedUser();
        workoutPlan.setAuthor(user.getName() + " " + user.getLastName());

        workoutPlanService.saveWorkout(user, workoutPlan);
    }
    @PostMapping("/workouts/subscribe")
    public void subscribeToWorkoutPlan(@RequestParam("workoutPlanId")Long workoutPlanId){
        User user = userDetailsService.getAuthenticatedUser();

        WorkoutPlan wp = workoutPlanService.getById(workoutPlanId);
//        userService.subscribeTo(user, wp, 1588284000000L);
        userService.subscribeTo(user, wp, System.currentTimeMillis());
    }
    @GetMapping("/workouts/subscribe/check_for_subscription")
    public Boolean checkForSubscription(){
        User user = userDetailsService.getAuthenticatedUser();
        return userService.checkForSubscription(user);
    }

    @GetMapping("/workouts/get/all")
    public FilteredWorkoutPlansDTO getWorkoutsFiltered(@RequestParam("frequency")Integer frequency, @RequestParam("difficulty")String difficulty){
        User user = userDetailsService.getAuthenticatedUser();
        return workoutPlanService.getWorkoutPlansFiltered(user,frequency, difficulty);
    }

    @GetMapping("/workouts/get/subscribed")
    public WorkoutPlanDTO getWorkoutPlan(@RequestParam(value="year",required=false)Integer year,@RequestParam(value = "month", required = false)Integer month){
        User user = userDetailsService.getAuthenticatedUser();

        return userService.getWorkoutPlan(user, month, year);
    }

    @PostMapping("/workouts/update")
    public DailyWorkoutDTO updateWorkout(@RequestParam("dailyWorkoutId")Long dailyWorkoutId,
                                         @RequestParam("dateTrained") String dateTrained,
                                         @RequestParam(value = "hoursSpent", required = false)Float hoursSpent,
                                         @RequestParam(value = "isCompleted", required = false) Boolean isCompleted){
        User user = userDetailsService.getAuthenticatedUser();
        return dailyWorkoutService.updateDailyWorkout(user.getId(), dailyWorkoutId, Date.valueOf(dateTrained), hoursSpent, isCompleted);
    }
    @PostMapping("/info/update")
    public User updateInfo(@RequestParam(value = "name", required = false)String name, @RequestParam(value = "surname", required = false)String surname, @RequestParam(value = "image", required = false) MultipartFile image){
        return userDetailsService.updateInfo(name,surname,image);
    }
    @GetMapping("/info/get")
    public User getInfo(){
        return userDetailsService.getAuthenticatedUser();
    }

}
