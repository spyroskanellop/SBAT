package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.AreaPercentageDTO;
import com.example.springbootapplicationtask.dto.response.FileUploadResponse;
import com.example.springbootapplicationtask.model.Area;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AreaService {

    AreaDTO addArea(Area area);
    List<AreaDTO> addAllAreas(List<Area> list);
    AreaDTO findArea(int areaId);
    void deleteArea(int areaId);
    List<AreaDTO> findAllAreas();
//    void saveAreaData(String filename);
    List<AreaDTO> findAreasByName(String areaName);
    List<Area> uploadAreaFromFile(MultipartFile file);
    FileUploadResponse uploadAreaToFile();

    List<AreaPercentageDTO> getTotalVaccinationsPerRegion();
}
