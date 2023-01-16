package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.model.Area;

import java.io.InputStream;
import java.util.List;

public interface AreaService {

    AreaDTO addArea(Area area);
    AreaDTO findArea(int areaId);
    void deleteArea(int areaId);
    List<AreaDTO> findAllAreas();

//    void importCSV(InputStream inputStream);
    void saveAreaData();
    List<AreaDTO> findAreasByName(String areaName);
}
