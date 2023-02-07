package com.example.springbootapplicationtask.dto;

import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.view.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
public class AreaDTO {
    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp="[α-ωΑ-Ω]+", message="Name must contain only Greek letters")
    @JsonView(View.Base.class)
    private String name;
    private int id;
    @NotNull(message = "Daily Dose is required")
    @Range(min = 1, message = "Daily dose must be positive number")
    private int dailyDose1;
    @NotNull(message = "Daily Dose is required")
    @Range(min = 1, message = "Daily dose must be positive number")
    private int dailyDose2;
    @NotNull
    @Range(min = 1, message = "Day diff must be positive number")
    private int dayDiff;
    @NotNull
    @Range(min = 1, message = "Day total must be positive number")
    private int dayTotal;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS]")
    private LocalDateTime referenceDate;
    @NotNull
    @Range(min = 1, message = "Total distinct persons must be positive number")
    private int totalDistinctPersons;
    @NotNull
    @NotNull
    @Range(min = 1, message = "Total dose must be positive number")
    private int totalDose1;
    @NotNull
    @Range(min = 1, message = "Total dose2 must be positive number")
    private int totalDose2;
    @NotNull
    @Range(min = 1, message = "Total vaccinations must be positive number")
    private int totalVaccinations;

    /**
     * Create DTO to entity method for easy convert
     * @return entity
     */
    public Area toEntity(){
        Area area = new Area();
        area.setId(this.id);
        area.setName(this.name);
        area.setDailyDose1(this.dailyDose1);
        area.setDailyDose2(this.dailyDose2);
        area.setDayDiff(this.dayDiff);
        area.setDayTotal(this.dayTotal);
        area.setReferenceDate(this.referenceDate);
        area.setTotalDistinctPersons(this.totalDistinctPersons);
        area.setTotalDose1(this.totalDose1);
        area.setTotalDose2(this.totalDose2);
        area.setTotalVaccinations(this.totalVaccinations);

        return area;
    }

    public String getTotalVaccinationsPerRegion(){
        return String.valueOf(BigDecimal.valueOf(this.getTotalVaccinations())
                .divide(BigDecimal.valueOf(this.getTotalDistinctPersons() * 2L), 2, RoundingMode.HALF_UP) +"%");
    }
}
