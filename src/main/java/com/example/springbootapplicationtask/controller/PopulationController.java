package com.example.springbootapplicationtask.controller;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.PopulationDTO;
import com.example.springbootapplicationtask.exception.NoDataFoundException;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.model.Population;
import com.example.springbootapplicationtask.service.AreaService;
import com.example.springbootapplicationtask.service.PopulationService;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/population")
@Slf4j
public class PopulationController {

    @Autowired
    PopulationService populationService;


    @GetMapping("/")
    @Operation(summary = "Get All Populations", responses = {
            @ApiResponse(description = "Get All Areas", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Area.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getPopulations(){
        List<PopulationDTO> list = populationService.findAllPopulations();
        if(list.isEmpty()){
            throw new NoDataFoundException("No Populations found");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Populations", list);
        return new ResponseEntity(map, HttpStatus.OK);
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Insert - Update Population", responses = {
            @ApiResponse(description = "Add new Population", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity savePopulation(@Valid @RequestBody Population population){
        log.info("Inside controller layer: ");
        HashMap<String, Object> map = new HashMap<>();
        //check if record exists to update it
        try {
            Optional<PopulationDTO> populationDTOtoBeUpdated = Optional.ofNullable(populationService.findPopulation(population.getId()));
            population.setId(populationDTOtoBeUpdated.get().getId());
            map.put("area: "+population.getId(), population);
        }catch(Exception e){
            System.out.println(e);
        }

        PopulationDTO populationDTO = populationService.addPopulation(population);
        map.put("area: "+populationDTO.getId(), populationDTO);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get Population", responses = {
            @ApiResponse(description = "Get Population", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getPopulation(@PathVariable @Size(min = 1) @NotNull int id){
        PopulationDTO populationDTO = populationService.findPopulation(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("Population", populationDTO);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Population", responses = {
            @ApiResponse(description = "Delete Population", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity deletePopulation(@PathVariable @Size(min = 1) @NotNull int id){
        populationService.deletePopulation(id);
        return new ResponseEntity("Population with id: "+id+ " Deleted", HttpStatus.OK);
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload csv data", responses = {
            @ApiResponse(description = "Upload csv data", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity uploadData(@RequestParam @NotNull MultipartFile file) throws Exception{
        log.info("File name: "+file.getOriginalFilename());
        log.info("File size: "+file.getSize());
        HashMap<String, Object> map = new HashMap<>();

        List<Population> list = populationService.uploadPopulationFromFile(file);
        map.put("PopulationList", list);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    @Operation(summary = "Get Population By name", responses = {
            @ApiResponse(description = "Get population By name", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getPopulationByName(@RequestParam String regionalUnit){
        List<PopulationDTO> list = populationService.findPopulationByName(regionalUnit);
        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found for regional Unit: "+regionalUnit);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Population with name: "+regionalUnit, list);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/getPopulation")
    @Operation(summary = "Get Population By name", responses = {
            @ApiResponse(description = "Get population By name", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Population.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
    })
    public ResponseEntity getPopulationByRegion(@RequestParam @Size(min = 1) @NotNull @Pattern(regexp="[α-ωΑ-Ω]+", message="Regional Unit must contain only Greek letters") String regionalUnit){
        if(regionalUnit.isEmpty()){
            throw new NoDataFoundException("This Regional Unit is not valid: "+regionalUnit);
        }
        List<PopulationDTO> list = populationService.findPopulationByName(regionalUnit);
        if(list.isEmpty()){
            throw new NoDataFoundException("No Areas found for regional Unit: "+regionalUnit);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Population from regional Unit: "+regionalUnit, list.stream().map(population1 ->
            population1.getPopulation()
        ).collect(Collectors.toList()));
        return new ResponseEntity(map, HttpStatus.OK);
    }



}
