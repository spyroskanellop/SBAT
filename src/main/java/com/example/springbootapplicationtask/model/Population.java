package com.example.springbootapplicationtask.model;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.PopulationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Population {
    @Id
    private int id;
    @Field
    @NotBlank
    private String regionalUnit;
    @Field
    @NotNull
    private long population;

    /**
     * Create entity to DTO method for easy convert
     * @return DTO
     */
    public PopulationDTO toDTO() {
        PopulationDTO populationDTO = new PopulationDTO();
        populationDTO.setId(this.id);
        populationDTO.setPopulation(this.population);
        populationDTO.setRegionalUnit(this.regionalUnit);
        return populationDTO;
    }

}
