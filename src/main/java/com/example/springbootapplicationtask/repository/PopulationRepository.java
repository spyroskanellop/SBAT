package com.example.springbootapplicationtask.repository;

import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.model.Population;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopulationRepository extends MongoRepository<Population, Integer> {
    public List<Population> findByRegionalUnit(String populationName);

}
