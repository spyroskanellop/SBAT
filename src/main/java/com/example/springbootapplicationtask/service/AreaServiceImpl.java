package com.example.springbootapplicationtask.service;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.AreaPercentageDTO;
import com.example.springbootapplicationtask.dto.response.FileUploadResponse;
import com.example.springbootapplicationtask.exception.NoDataFoundException;
import com.example.springbootapplicationtask.exception.ResourceNotFoundException;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.repository.AreaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AreaServiceImpl implements AreaService{

    @Autowired
    AreaRepository areaRepository;

    String line = "";
    public AreaDTO addArea(Area area) {
        log.info("Area with name: {} added.", area.getName());
        return areaRepository.save(area).toDTO();
    }

    public List<AreaDTO> addAllAreas(List<Area> list) {
        List<AreaDTO> list2 = areaRepository.saveAll(list)
                .stream().map(area -> area.toDTO()).collect(Collectors.toList());
        return list2;
    }

    public AreaDTO findArea(int areaId) {
        log.info("Searching Area with id: {} ", areaId);
        return areaRepository.findById(areaId).orElseThrow(() -> new ResourceNotFoundException("No Area found with id "+areaId)).toDTO();
    }
    public List<AreaDTO> findAreasByName(String areaName) {
        log.info("Searching Areas with name: {} ", areaName);
        List<AreaDTO> list = areaRepository.findByName(areaName).stream()
                .map(area -> area.toDTO()).collect(Collectors.toList());
        return list;
    }

    public List<Area> uploadAreaFromFile(MultipartFile file) {
        List<Area> areaList = new ArrayList<>();

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            Stream<String> lines = br.lines().skip(1);
            lines.forEach(entry -> {
                String[] text = entry.split(",");
                Area area = new Area();
                area.setId(Integer.parseInt(text[1]));
                area.setName(text[0]);
                area.setDailyDose1(Integer.parseInt(text[2]));
                area.setTotalDose2(Integer.parseInt(text[3]));
                area.setDayDiff(Integer.parseInt(text[4]));
                area.setDayTotal(Integer.parseInt(text[5]));
                area.setReferenceDate(LocalDateTime.parse(text[6]));

                area.setTotalDistinctPersons(Integer.parseInt(text[7]));
                area.setTotalDose1(Integer.parseInt(text[8]));
                area.setTotalDose2(Integer.parseInt(text[9]));
                area.setTotalVaccinations(Integer.parseInt(text[10]));

                if (!findAllAreas().contains(area.toDTO())) {
                    areaList.add(area);
                    addArea(area);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return areaList;
    }

    public FileUploadResponse uploadAreaToFile() {
        FileUploadResponse fileUploadResponse;
        List<AreaDTO> list = findAllAreas();
        try{
            writeToCsv(list, "area,areaid,dailydose1,dailydose2,daydiff,daytotal,referencedate,totaldistinctpersons,totaldose1,totaldose2,totalvaccinations");
            ClassPathResource res = new ClassPathResource("src/main/resources/static/area.csv");
            File file = new File(res.getPath());

            String[] fileSplit = file.getName().split("\\.");   //get the extension of file

            fileUploadResponse = FileUploadResponse.builder()
                    .fileType(fileSplit[1])
                    .filename(file.getName())
                    .fileDownloadUri(file.getPath().replace("\\","/"))
                    .size(file.length())
                    .build();

            return fileUploadResponse;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void writeToCsv(List<AreaDTO> areaList, String header) throws IOException {
        PrintWriter writer = new PrintWriter("src/main/resources/static/area.csv");
        writer.println(header);

        for (AreaDTO areaDTO : areaList) {
            writer.println(areaDTO.toString());
        }
        writer.close();
    }

        public List<AreaPercentageDTO> getTotalVaccinationsPerRegion() {
        List<AreaDTO> list = findAllAreas();
        List<AreaPercentageDTO> percentages = new ArrayList<>();
        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found");
        }

        list.forEach(areaDTO -> {
            AreaPercentageDTO areaPercentageDTO = new AreaPercentageDTO();
            areaPercentageDTO.setName(areaDTO.getName());
            BigDecimal percentage = BigDecimal.valueOf(areaDTO.getTotalVaccinations())
                    .divide(BigDecimal.valueOf(areaDTO.getTotalDistinctPersons() * 2L), 2, RoundingMode.HALF_UP);
            areaPercentageDTO.setPercentage(String.valueOf(percentage)+"%");

            percentages.add(areaPercentageDTO);
        });

        return percentages;
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
