package com.example.springbootapplicationtask.controller;

import com.example.springbootapplicationtask.dto.AreaDTO;
import com.example.springbootapplicationtask.dto.AreaPercentageDTO;
import com.example.springbootapplicationtask.dto.response.FileUploadResponse;
import com.example.springbootapplicationtask.exception.NoDataFoundException;
import com.example.springbootapplicationtask.exception.ResourceNotFoundException;
import com.example.springbootapplicationtask.model.Area;
import com.example.springbootapplicationtask.repository.AreaRepository;
import com.example.springbootapplicationtask.service.AreaService;
import com.example.springbootapplicationtask.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

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
                "?? ?? ?? ?? ?? ?? ?????.???.???", "?? ?? ?? ?? ?? ?? ????????????? 0 }.....???.", "?? ?? ?? ?? ?? ????????????????????????? o\")", "?? ?? ?? ?? ?? ??????????????(????????????.???^'''",
                "?? ?? ?? ?? ?? ???????????????? ??", ")\\?? ?? ?? ?? ??????????????????? ", ")\\?? ?? ?? ?? ??????????????????? ", ");;\\ ?? ?? ????????.???.???????????", ");;;\\?? ??????????(??????)\\____//",
                ");;;;\\?????????????????????????__//", ");;;;;;;\\???????????????????????????((("
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
    public ResponseEntity saveArea(@Valid @RequestBody Area area){
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
    public ResponseEntity getArea(@PathVariable @NotNull @Min(value = 1, message = "Id should be positive number") int id){
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
    public ResponseEntity deleteArea(@PathVariable @NotNull @Min(value = 1, message = "Id should be positive number") int id){
        areaService.deleteArea(id);
        return new ResponseEntity("Area with id: "+id+ " Deleted", HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity uploadData(@RequestParam MultipartFile file) throws Exception{
        log.info("File name: "+file.getOriginalFilename());
        log.info("File size: "+file.getSize());
        HashMap<String, Object> map = new HashMap<>();

        List<Area> list = areaService.uploadAreaFromFile(file);
        map.put("AreaList", list);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/uploadToFile")
    public ResponseEntity uploadToFile() throws Exception{
        FileUploadResponse file = areaService.uploadAreaToFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileDownloadUri() + "\"")
                .body(file);
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
    public ResponseEntity getAreaByName(@RequestParam @Size(min = 1) @NotNull @Pattern(regexp="[??-????-??]+", message="Name must contain only Greek letters") String name){
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
    public ResponseEntity getTotalVaccinationsByAreaName(@RequestParam @Size(min = 1) @NotNull @Pattern(regexp="[??-????-??]+", message="Name must contain only Greek letters") String name){
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
        HashMap<String, Object> map = new HashMap<>();
        List<AreaPercentageDTO> list = areaService.getTotalVaccinationsPerRegion();
        map.put("Percentage of vaccinations per region", list);
        return new ResponseEntity(map, HttpStatus.OK);
    }


}
