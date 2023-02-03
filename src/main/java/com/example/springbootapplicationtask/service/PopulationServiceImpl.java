package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.PopulationDTO;
import com.example.springbootapplicationtask.exception.ResourceNotFoundException;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.model.Population;
import com.example.springbootapplicationtask.repository.AreaRepository;
import com.example.springbootapplicationtask.repository.PopulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PopulationServiceImpl implements PopulationService {

    @Autowired
    PopulationRepository populationRepository;

    String line = "";
    public PopulationDTO addPopulation(Population population) {
        log.info("Population with id: {} added.", population.getId());
        return populationRepository.save(population).toDTO();
    }

    public PopulationDTO findPopulation(int populationId) {
        log.info("Searching Population with id: {} ", populationId);
        return populationRepository.findById(populationId).orElseThrow(() -> new ResourceNotFoundException("No Population found with id "+populationId)).toDTO();
    }
    public List<PopulationDTO> findPopulationByName(String populationName) {
        log.info("Searching Population with name: {} ", populationName);
        List<PopulationDTO> list = populationRepository.findByRegionalUnit(populationName).stream()
                .map(area -> area.toDTO()).collect(Collectors.toList());
        return list;
    }

    public void deletePopulation(int populationId) {
        log.info("Area with id {} is deleted", populationId);
        populationRepository.deleteById(populationId);
    }

    public List<PopulationDTO> findAllPopulations() {
        log.info("Fetching Populations list");
        List<PopulationDTO> list = populationRepository.findAll().stream()
                .map(area -> area.toDTO()).collect(Collectors.toList());
        return list;
    }


    /**
     * Read csv file and fill Population.java class with each row.
     * If Id is not found in db, entry is inserted
     */
    public List<Population> uploadPopulationFromFile(MultipartFile file) {
        List<Population> populationList = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Stream<String> lines = br.lines().skip(1);
            lines.forEach(entry -> {
                entry = entry.replace(".","");
                String[] text = entry.split(",");
                Population population = new Population();
                population.setId(Integer.parseInt(text[0]));
                population.setRegionalUnit(text[1]);
                population.setPopulation(Long.parseLong(text[2]));
                if (!findAllPopulations().contains(population.toDTO())) {
                    populationList.add(population);
                    addPopulation(population);
                }
            });
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error processing file: "+file+"\n"+"e");
        }
        return populationList;
    }
}
