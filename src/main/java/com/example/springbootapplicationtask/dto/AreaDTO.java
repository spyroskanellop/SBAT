package com.example.springbootapplicationtask.dto;

import com.example.springbootapplicationtask.model.Area;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class AreaDTO {
    private String name;
    private int id;
    private int dailyDose1;
    private int dailyDose2;
    private int dayDiff;
    private int dayTotal;
    private Date referenceDate;
    private int totalDistinctPersons;
    private int totalDose1;
    private int totalDose2;
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
}
