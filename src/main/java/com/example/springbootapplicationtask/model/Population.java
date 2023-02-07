package com.example.springbootapplicationtask.model;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.PopulationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Population {
    @Id
    @Range(min = 1, message = "Id must be positive number")
    private int id;
    @Field
    @NotBlank
    @Pattern(regexp="[α-ωΑ-Ω]+", message="Regional Unit must contain only Greek letters")
    private String regionalUnit;
    @Field
    @NotNull
    @Range(min = 1, message = "Id must be positive number")
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
