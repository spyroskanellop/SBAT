package com.example.springbootapplicationtask.controller;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.exception.NoDataFoundException;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.repository.AreaRepository;
import com.example.springbootapplicationtask.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/area")
@Slf4j
public class AreaController {
    @Autowired
    AreaService areaService;


    @GetMapping("/")
    @Operation(summary = "Get All Areas", responses = {
            @ApiResponse(description = "Get All Areas", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getAreas(){
        List<AreaDTO> areas = areaService.findAllAreas();
        if(areas.isEmpty()){
            throw new NoDataFoundException("No Areas found");
        }
        HashMap<String, Object> map = new HashMap<>();
        String[] yoshiArray = new String[] {
                "¨ ¨ ¨ ¨ ¨ ¨ ¨▲.︵.▲", "¨ ¨ ¨ ¨ ¨ ¨ ¨◄ƒ░░ 0 }.....︵.", "¨ ¨ ¨ ¨ ¨ ¨◄ƒ░░░░░░ o\")", "¨ ¨ ¨ ¨ ¨ ◄ƒ░░░(────.•^'''",
                "¨ ¨ ¨ ¨ ¨ ◄ƒ░░░§ ´", ")\\¨ ¨ ¨ ¨ ◄ƒ░░░░§ ", ")\\¨ ¨ ¨ ¨ ◄ƒ░░░░§ ", ");;\\ ¨ ¨ ◄ƒ░.︵.░░░§", ");;;\\¨ ¨◄ƒ░(░░)\\____//",
                ");;;;\\¨◄ƒ░░░░░░__//", ");;;;;;;\\░░░░░░░░░((("
        };
        map.put("Yoshi", yoshiArray);
        map.put("Areas", areas);
        return new ResponseEntity(map, HttpStatus.OK);
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insert - Update Area", responses = {
            @ApiResponse(description = "Add new Area", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity saveArea(@RequestBody Area area){
        log.info("Inside controller layer: ");
        HashMap<String, Object> map = new HashMap<>();
        //check if record exists to update it
        try {
            Optional<AreaDTO> areaDTOtoBeUpdated = Optional.ofNullable(areaService.findArea(area.getId()));
            area.setId(areaDTOtoBeUpdated.get().getId());
            map.put("area: "+area.getId(), area);
        }catch(Exception e){
            System.out.println(e);
        }

        AreaDTO areaDTO = areaService.addArea(area);
        map.put("area: "+areaDTO.getId(), areaDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get Area", responses = {
            @ApiResponse(description = "Get Area", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getArea(@PathVariable int id){
        AreaDTO areaDTO = areaService.findArea(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Area", areaDTO);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Area", responses = {
            @ApiResponse(description = "Delete Area", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity deleteArea(@PathVariable int id){
        areaService.deleteArea(id);
        return new ResponseEntity("Area with id: "+id+ " Deleted", HttpStatus.OK);
    }

//    @PostMapping(params = "action=import")
//    public ResponseEntity importCSV(@RequestParam MultipartFile csvFile) {
//        log.info("File name: " + csvFile.getOriginalFilename());
//        log.info("File size: " + csvFile.getSize());
//
//        try {
//            areaService.importCSV(csvFile.getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @GetMapping("/readData")
    @Operation(summary = "Upload csv data", responses = {
            @ApiResponse(description = "Upload csv data", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity uploadDataInDb(){
        areaService.saveAreaData();
        return new ResponseEntity("Data Inserted", HttpStatus.OK);
    }

    @GetMapping("/findByName")
    @Operation(summary = "Get Area By name", responses = {
            @ApiResponse(description = "Get Area By name", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getAreaByName(@RequestParam String name){
        List<AreaDTO> list = areaService.findAreasByName(name);
        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Areas with name: "+name, list);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/findTotalVaccinations")
    @Operation(summary = "Get Total Vaccinations Per region", responses = {
            @ApiResponse(description = "Get Total Vaccinations Per region", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getTotalVaccinationsByAreaName(@RequestParam String name){
        List<AreaDTO> list = areaService.findAreasByName(name);
        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("TotalVaccinations for area: "+name, list.stream().map(area1 -> area1.getTotalVaccinations()));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/findTotalVaccinationsPerRegion")
    @Operation(summary = "Get Total Vaccinations for all regions", responses = {
            @ApiResponse(description = "Get Total Vaccinations for all regions", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getTotalVaccinationsPerRegion(){
        List<AreaDTO> list = areaService.findAllAreas();

        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found");
        }
//        List<Integer> personsList = list.stream().map(area1 -> (area1.getTotalDistinctPersons() *2)).collect(Collectors.toList());
        List<Double> personsList = list.stream().map(area1 -> ((double)area1.getTotalDistinctPersons() *2)/area1.getTotalVaccinations()).collect(Collectors.toList());
        list.stream().map(area -> area.getName());
        HashMap<String, Object> map = new HashMap<>();
        map.put("Vaccination Percentage : ", personsList);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
