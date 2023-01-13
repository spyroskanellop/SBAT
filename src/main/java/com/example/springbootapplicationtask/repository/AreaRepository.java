package com.example.springbootapplicationtask.repository;

import com.example.springbootapplicationtask.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AreaRepository extends MongoRepository<Area, Integer> {
}
