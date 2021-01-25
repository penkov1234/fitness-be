package com.fitness.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.demo.Entities.Relations.DayExercises;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "exercise")
    @JsonIgnore
    private List<DayExercises> dailyWorkoutList;
}
