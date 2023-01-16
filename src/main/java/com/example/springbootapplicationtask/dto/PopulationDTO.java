package com.example.springbootapplicationtask.dto;

import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.model.Population;
import lombok.Data;


@Data
public class PopulationDTO {
    private int id;
    private String regionalUnit;
    private long population;

    /**
     * Create DTO to entity method for easy convert
     * @return entity
     */
    public Population toEntity(){
        Population population = new Population();
        population.setId(this.id);
        population.setPopulation(this.population);
        population.setRegionalUnit(this.regionalUnit);
        return population;
    }
}
