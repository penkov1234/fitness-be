package com.fitness.demo.Entities.IdClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDailyWorkoutPK implements Serializable {

    public Long userId;

    public Long dailyWorkoutId;

    public Date dateTrained;
}
