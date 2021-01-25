package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayExerciseDTO {

    public Long exerciseId;
    public String name;
    public Integer numSets;
    public Integer numReps;
    public String imageUrl;
    public String description;
}
