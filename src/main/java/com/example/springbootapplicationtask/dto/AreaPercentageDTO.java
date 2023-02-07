package com.example.springbootapplicationtask.dto;

import lombok.Data;

//Created dto for method getTotalVaccinationsPerRegion inside areaController
@Data
public class AreaPercentageDTO {
    private String name;
    private String percentage;
}
