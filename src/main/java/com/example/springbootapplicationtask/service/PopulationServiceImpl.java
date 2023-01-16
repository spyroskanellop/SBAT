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

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
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

//    public void importCSV(InputStream inputStream) {
//        try {
//            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//            InputStreamReader inputStreamReader = new InputStreamReader(zipInputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            bufferedReader.lines()
//                    .skip(1)
//                    .map(Area::parse)
//                    .forEach(areaRepository::save);
//        } catch( Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void savePopulationData(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\resources\\populationRegionalUnits.csv"));

            Stream<String> data = bufferedReader.lines().skip(1).limit(5); //placed a limit cause of lots of entries and delay of time
            data.forEachOrdered(line -> {
                String[] text = line.split(",");
                Population population = new Population();
                population.setId(Integer.parseInt(text[0]));
                population.setRegionalUnit(text[1]);
                text[2] = text[2].replace(".", "");
                population.setPopulation(Long.parseLong(text[2]));

                if(populationRepository.findById(Integer.parseInt(text[0])).isEmpty()){
                    populationRepository.save(population);
                }
            });
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}
