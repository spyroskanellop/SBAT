package com.example.springbootapplicationtask.model;

import com.example.springbootapplicationtask.converters.LocalDateTimeConverter;
import com.example.springbootapplicationtask.dto.AreaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Area {
    @Field
    @Indexed
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Id
    private int id;
    @Field
    @NotNull
    @Size(min = 1)
    private int dailyDose1;
    @Field
    @NotNull
    @Size(min = 1)
    private int dailyDose2;
    @Field
    @NotNull
    @Size(min = 1)
    private int dayDiff;
    @Field
    @NotNull
    @Size(min = 1)
    private int dayTotal;
    @Field
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS]")
    private LocalDateTime referenceDate;
    @Field
    @NotNull
    @Size(min = 1)
    private int totalDistinctPersons;
    @Field
    @NotNull
    @Size(min = 1)
    private int totalDose1;
    @Field
    @NotNull
    @Size(min = 1)
    private int totalDose2;
    @Field
    @NotNull
    @Size(min = 1)
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

}



