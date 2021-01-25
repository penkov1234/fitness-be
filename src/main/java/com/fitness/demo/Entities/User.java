package com.fitness.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.demo.Entities.Relations.DayExercises;
import com.fitness.demo.Entities.Relations.UserDailyWorkout;
import com.fitness.demo.Entities.Relations.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name= "last_name")
    public String lastName;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    @JsonIgnore
    public String password;

    @Column(name = "auth_role")
    @JsonIgnore
    public String authRole;

    @Column(name = "height")
    public float height;

    @Column(name = "profile_picture")
    public byte[] profile_picture;

    @Column(name = "weight")
    public float weight;

    @Column(name = "body_fat")
    public float bodyFat;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserSubscription> userSubscriptionList;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<WorkoutPlan> createdWorkoutPlans;

//    @OneToMany(mappedBy = "user")
//    private List<WorkoutPlan> createdWorkoutPlans;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserDailyWorkout> dailyWorkouts;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.authRole));
        System.out.println(authorities);
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
