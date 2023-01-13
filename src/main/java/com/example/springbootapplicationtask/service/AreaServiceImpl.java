package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService{

    @Autowired
    AreaRepository areaRepository;

    public AreaDTO addArea(Area area) {
        log.info("Area with name: {} added.", area.getName());
        return areaRepository.save(area).toDTO();
    }

    public AreaDTO findArea(int areaId) {
        log.info("Searching Area with id: {} ", areaId);
        return areaRepository.findById(areaId).orElseThrow().toDTO();
    }

    public void deleteArea(int areaId) {
        log.info("Area with id {} is deleted", areaId);
        areaRepository.deleteById(areaId);
    }

    public List<AreaDTO> findAllAreas() {
        log.info("Fetching Areas list");
        List<AreaDTO> list = areaRepository.findAll().stream()
                                .map(area -> area.toDTO()).collect(Collectors.toList());
        return list;
    }
}
