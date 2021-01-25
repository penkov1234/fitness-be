package com.fitness.demo.Services.GlobalServices;

import com.fitness.demo.Entities.DailyWorkout;
import com.fitness.demo.Entities.Relations.UserDailyWorkout;
import com.fitness.demo.Entities.Relations.UserSubscription;
import com.fitness.demo.Entities.User;
import com.fitness.demo.Entities.WorkoutPlan;
import com.fitness.demo.POJOs.CalendarDTO;
import com.fitness.demo.POJOs.DailyWorkoutDTO;
import com.fitness.demo.POJOs.WorkoutPlanDTO;
import com.fitness.demo.Repositories.UserDailyWorkoutRepository;
import com.fitness.demo.Repositories.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserSubscriptionRepository  userSubscriptionRepository;

    private final UserDailyWorkoutRepository userDailyWorkoutRepository;

    private final TimeService timeService;

    private final DailyWorkoutService dailyWorkoutService;

    private final WorkoutPlanService workoutPlanService;

    public UserService(UserSubscriptionRepository userSubscriptionRepository, UserDailyWorkoutRepository userDailyWorkoutRepository, TimeService timeService, DailyWorkoutService dailyWorkoutService, WorkoutPlanService workoutPlanService) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.userDailyWorkoutRepository = userDailyWorkoutRepository;
        this.timeService = timeService;
        this.dailyWorkoutService = dailyWorkoutService;
        this.workoutPlanService = workoutPlanService;
    }

    public void subscribeTo(User user, WorkoutPlan workoutPlan, Long startTime){

        if (checkForSubscription(user)){
            LocalDate localDate = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
            UserSubscription subscription = userSubscriptionRepository.findByUserAndMonthAndYear(user.getId(),localDate.getMonthValue(), localDate.getYear()).get();
            List<UserDailyWorkout> userDailyWorkouts = userDailyWorkoutRepository.findAllByUserAndDailyWorkoutInAndDateTrained_MonthAndDateTrained_Year(
                    user.getId(),
                    subscription.getWorkoutPlan().getDailyWorkouts().stream().map(DailyWorkout::getId).collect(Collectors.toList()),
                    localDate.getMonthValue(),
                    localDate.getYear());

            userSubscriptionRepository.delete(subscription);
            userDailyWorkoutRepository.deleteAll(userDailyWorkouts);
        }
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setDateSubscribed(new Date(startTime));
        userSubscription.setIsCompleted(false);
        userSubscription.setUserId(user.getId());
        userSubscription.setWorkoutPlanId(workoutPlan.getId());
        userSubscription = userSubscriptionRepository.save(userSubscription);

        //increase sub count
        workoutPlan.setTotalSubscriptions(workoutPlan.getTotalSubscriptions() == null ? 1 : workoutPlan.getTotalSubscriptions() + 1);
        workoutPlanService.updateWorkoutPlan(workoutPlan);

        createUserDailyWorkouts(user, workoutPlan.getDailyWorkouts(), workoutPlan.getFrequency(), startTime);
    }

    public Optional<UserSubscription> getSubscriptionForMonth(User user, int month, int year){

        return userSubscriptionRepository.findByUserAndMonthAndYear(user.getId(),month,year);
    }

    private void createUserDailyWorkouts(User user, List<DailyWorkout> dailyWorkoutList, Integer frequency, Long startTime){
        UserDailyWorkout userDailyWorkout;
        int workoutsInAWeekCounter = 0;
        Long currentDayInMills = startTime;
        Long lastDayOfTheWeekInMills = timeService.getLastDayOfWeek(currentDayInMills);
        Timestamp currentDay;
        Timestamp lastDayOfTheWeek;
        Timestamp firstDayOfNextMonth = new Timestamp(timeService.getStartOfNextMonth());

        for (DailyWorkout dw: dailyWorkoutList){
            currentDay = new Timestamp(currentDayInMills);
            lastDayOfTheWeek = new Timestamp(lastDayOfTheWeekInMills);

            //this week is filled
            if (!currentDay.before(lastDayOfTheWeek) || workoutsInAWeekCounter == frequency){
                workoutsInAWeekCounter = 0;

                if (!timeService.isTuesday(currentDayInMills)){
                    currentDayInMills = timeService.getStartOfNextWeek(lastDayOfTheWeekInMills);
                }

                lastDayOfTheWeekInMills = timeService.getLastDayOfWeek(currentDayInMills);
                currentDay = new Timestamp(currentDayInMills);

                //end of month, break
                if (!currentDay.before(firstDayOfNextMonth)){
                    break;
                }
                else {
                    workoutsInAWeekCounter ++;

                    saveDailyWorkout(currentDayInMills,user.getId(),dw);

                    currentDayInMills = timeService.getNextDayFromSpecificDay(currentDayInMills, frequency == 5 ? 0 : 1);
                }
            }
            else {
                workoutsInAWeekCounter ++;

                saveDailyWorkout(currentDayInMills,user.getId(),dw);

                currentDayInMills = timeService.getNextDayFromSpecificDay(currentDayInMills, frequency == 5 ? 0 : 1);

            }

        }
    }
    private void saveDailyWorkout(Long currentDayInMills,Long userId, DailyWorkout dw){
        Date date = new Date(currentDayInMills);
        date = Date.valueOf(date.toLocalDate());
        UserDailyWorkout userDailyWorkout = new UserDailyWorkout();
        userDailyWorkout.setDailyWorkoutId(dw.getId());
        userDailyWorkout.setDateTrained(date);
        userDailyWorkout.setHoursSpent(0F);
        userDailyWorkout.setIsCompleted(false);
        userDailyWorkout.setUserId(userId);

        userDailyWorkoutRepository.save(userDailyWorkout);
    }
    public WorkoutPlanDTO getWorkoutPlan(User user, Integer month, Integer year){

        Optional<UserSubscription> userSubscription = userSubscriptionRepository.findByUserAndMonthAndYear(user.getId(), month == null ? timeService.getCurrentMonth() : month, year == null ? timeService.getCurrentYear() : year);
        LocalDate localDate = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
        if (!userSubscription.isPresent()){
            return createEmptyWorkoutPlan(month, year);
        }
        else {
//            if (userSubscription.get().getDateSubscribed().toLocalDate().getMonthValue() != localDate.getMonthValue()){
////                //subscribe the user to the same workout plan in the current month
////                subscribeTo(user, userSubscription.get().getWorkoutPlan(), timeService.getFirstDayOfThisMonth());
////            }
            WorkoutPlan workoutPlan = userSubscription.get().getWorkoutPlan();
            List<DailyWorkout> dailyWorkouts = workoutPlan.getDailyWorkouts();

            List<UserDailyWorkout> userDailyWorkouts = userDailyWorkoutRepository.findAllByUserAndDailyWorkoutInAndDateTrained_MonthAndDateTrained_Year(user.getId(), dailyWorkouts.stream().map(DailyWorkout::getId).collect(Collectors.toList()), month == null ? timeService.getCurrentMonth() : month, year == null ? timeService.getCurrentYear() : year);

            return createFormattedWorkoutPlan(workoutPlan, userDailyWorkouts, month,  year);
        }
    }
    private WorkoutPlanDTO createEmptyWorkoutPlan(Integer month, Integer year){
        WorkoutPlanDTO workoutPlanDTO = new WorkoutPlanDTO();
        workoutPlanDTO.setCalendarDTO(getMonthInfo(month, year));

        return workoutPlanDTO;
    }
    private WorkoutPlanDTO createFormattedWorkoutPlan(WorkoutPlan workoutPlan, List<UserDailyWorkout> userDailyWorkouts, Integer month, Integer year){
        WorkoutPlanDTO workoutPlanDTO = new WorkoutPlanDTO();
        List<DailyWorkoutDTO> dailyWorkoutDTOS = new ArrayList<>();
        workoutPlanDTO.setId(workoutPlan.getId());
        workoutPlanDTO.setAuthor(workoutPlan.getAuthor().getName() + " " + workoutPlan.getAuthor().getLastName());
        workoutPlanDTO.setAuthorId(workoutPlan.getAuthor().getId());
        workoutPlanDTO.setDifficulty(workoutPlan.getDifficulty());
        workoutPlanDTO.setFrequency(workoutPlan.getFrequency());
        workoutPlanDTO.setIsPublished(workoutPlan.getIsPublished());
        workoutPlanDTO.setName(workoutPlan.getName());
        workoutPlanDTO.setCalendarDTO(getMonthInfo(month, year));

        for (UserDailyWorkout udw: userDailyWorkouts){
            DailyWorkoutDTO dailyWorkoutDTO = dailyWorkoutService.createFormattedDailyWorkout(udw);
            dailyWorkoutDTOS.add(dailyWorkoutDTO);
        }

        workoutPlanDTO.setDailyWorkouts(dailyWorkoutDTOS);

        return workoutPlanDTO;
    }
    private CalendarDTO getMonthInfo(Integer month, Integer year){
        CalendarDTO calendarDTO = new CalendarDTO();
        Date currentTime = new Date(System.currentTimeMillis());
        LocalDate currentLocalDate = currentTime.toLocalDate();
        Date date;
        LocalDate localDate;
        if (month == null || month == currentLocalDate.getMonthValue()){
            date = currentTime;
            localDate = date.toLocalDate();
            calendarDTO.setCurrentDay(localDate.getDayOfMonth());
        }
        else {
            date = new Date(timeService.getDateOfCustomMonthAndYear(month, year == null ? timeService.getCurrentYear() : year).getTime());
            localDate = date.toLocalDate();
            calendarDTO.setCurrentDay(localDate.lengthOfMonth());
        }

        calendarDTO.setFirstDayName(month == null ? timeService.getNameOfFirstDayOfThisMonth() : timeService.getNameOfFirstDayOfCustomMonth(month, year == null ? timeService.getCurrentYear() : year));
        calendarDTO.setMonthName(localDate.getMonth().name());
        calendarDTO.setMonthNum(localDate.getMonthValue());
        calendarDTO.setNumOfDays(localDate.lengthOfMonth());
        calendarDTO.setYear(localDate.getYear());

        return calendarDTO;
    }
    public Boolean checkForSubscription(User user){
        LocalDate localDate = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate();
        if (userSubscriptionRepository.findByUserAndMonthAndYear(user.getId(), localDate.getMonthValue(), localDate.getYear()).isPresent()){
            return true;
        }
        else {
            return false;
        }
    }

    public List<UserDailyWorkout> getUserDailyWorkouts(User user, List<Long> dailyWorkoutIds, int month, int year){
        return userDailyWorkoutRepository.findAllByUserAndDailyWorkoutInAndDateTrained_MonthAndDateTrained_Year(user.getId(),dailyWorkoutIds,month,year);
    }

    public List<UserDailyWorkout> getAllUserDailyWorkouts(User user){
        return userDailyWorkoutRepository.findAllByUser(user);
    }
    public List<UserSubscription> getAllUserSubscriptions(User user){
        return userSubscriptionRepository.findAllByUser(user);
    }
    public List<WorkoutPlan> getUsersWorkoutPlans(User user){
        return workoutPlanService.getAllByUser(user);
    }
}
