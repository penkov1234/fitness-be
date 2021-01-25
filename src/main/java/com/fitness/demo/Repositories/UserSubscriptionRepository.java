package com.fitness.demo.Repositories;

import com.fitness.demo.Entities.IdClasses.UserSubscriptionPK;
import com.fitness.demo.Entities.Relations.UserSubscription;
import com.fitness.demo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionPK> {

    @Query(value = "select * from user_subscription us where us.user_id =?1 and MONTH(us.date_subscribed) =?2  and year(us.date_subscribed) =?3 order by us.date_subscribed DESC limit 1 ", nativeQuery = true)
    Optional<UserSubscription> findByUserAndMonthAndYear(Long userId, int month, int year);

    @Query(value = "select * from user_subscription us where us.user_id =?1 and year(us.date_subscribed) =?2", nativeQuery = true)
    List<UserSubscription> findAllByUserAndYear(Long userId, int year);

    List<UserSubscription> findAllByUser(User user);
}
