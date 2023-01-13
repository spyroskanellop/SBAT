package com.example.springbootapplicationtask.model;

import com.example.springbootapplicationtask.dto.AreaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Area {
    @Field
    private String name;
    @Id
    private int id;
    @Field
    private int dailyDose1;
    @Field
    private int dailyDose2;
    @Field
    private int dayDiff;
    @Field
    private int dayTotal;
    @Field
    private Date referenceDate;
    @Field
    private int totalDistinctPersons;
    @Field
    private int totalDose1;
    @Field
    private int totalDose2;
    @Field
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