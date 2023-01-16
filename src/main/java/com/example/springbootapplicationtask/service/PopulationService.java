package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.PopulationDTO;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.model.Population;

import java.util.List;

public interface PopulationService{
    PopulationDTO addPopulation(Population population);
    PopulationDTO findPopulation(int populationId);
    void deletePopulation(int populationId);
    List<PopulationDTO> findAllPopulations();
    List<PopulationDTO> findPopulationByName(String populationName);
    void savePopulationData();
}
