package com.fitness.demo.POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucceededAndFailedPOJO {

    public int succeeded;
    public int failed;
}
