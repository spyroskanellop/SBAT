package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.model.Area;

import java.util.List;

public interface AreaService {

    AreaDTO addArea(Area area);
    AreaDTO findArea(int areaId);
    void deleteArea(int areaId);
    List<AreaDTO> findAllAreas();

}
