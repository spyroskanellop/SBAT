package com.example.springbootapplicationtask.repository;

import com.example.springbootapplicationtask.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends MongoRepository<Area, Integer> {
    public List<Area> findByName(String areaName);
}
