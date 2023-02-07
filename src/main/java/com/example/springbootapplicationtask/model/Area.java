package com.example.springbootapplicationtask.model;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Area {
    @Field
    @Indexed
    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp="[α-ωΑ-Ω]+", message="Name must contain only Greek letters")
    private String name;
    @Id
    @Range(min = 1, message = "Id must be positive number")
    private int id;
    @Field
    @NotNull(message = "Daily Dose is required")
    @Range(min = 1, message = "Daily dose must be positive number")
    private int dailyDose1;
    @Field
    @NotNull(message = "Daily Dose is required")
    @Range(min = 1, message = "Daily dose must be positive number")
    private int dailyDose2;
    @Field
    @NotNull
    @Range(min = 1, message = "Day diff must be positive number")
    private int dayDiff;
    @Field
    @NotNull
    @Range(min = 1, message = "Day total must be positive number")
    private int dayTotal;
    @Field
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS]")
    private LocalDateTime referenceDate;
    @Field
    @NotNull
    @Range(min = 1, message = "Total distinct persons must be positive number")
    private int totalDistinctPersons;
    @Field
    @NotNull
    @Range(min = 1, message = "Total dose must be positive number")
    private int totalDose1;
    @Field
    @NotNull
    @Range(min = 1, message = "Total dose2 must be positive number")
    private int totalDose2;
    @Field
    @NotNull
    @Range(min = 1, message = "Total vaccinations must be positive number")
    private int totalVaccinations;


    /**
     * Create entity to DTO method for easy convert
     * @return DTO
     */
    public AreaDTO toDTO() {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(this.id);
        areaDTO.setName(this.name);
        areaDTO.setDailyDose1(this.dailyDose1);
        areaDTO.setDailyDose2(this.dailyDose2);
        areaDTO.setDayDiff(this.dayDiff);
        areaDTO.setDayTotal(this.dayTotal);
        areaDTO.setReferenceDate(this.referenceDate);
        areaDTO.setTotalDistinctPersons(this.totalDistinctPersons);
        areaDTO.setTotalDose1(this.totalDose1);
        areaDTO.setTotalDose2(this.totalDose2);
        areaDTO.setTotalVaccinations(this.totalVaccinations);
        return areaDTO;
    }

    public String getTotalVaccinationsPerRegion(){
        return String.valueOf(BigDecimal.valueOf(this.getTotalVaccinations())
                            .divide(BigDecimal.valueOf(this.getTotalDistinctPersons() * 2L), 2, RoundingMode.HALF_UP) +"%");
    }

}



