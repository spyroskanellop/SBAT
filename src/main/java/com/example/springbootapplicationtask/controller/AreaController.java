package com.example.springbootapplicationtask.controller;

import com.example.springbootapplicationtask.dto.AreaDTO;
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

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
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
//        Optional<AreaDTO> areaDTOtoBeUpdated = Optional.ofNullable(areaService.findArea(area.getId()));
        try {
            Optional<AreaDTO> areaDTOtoBeUpdated = Optional.ofNullable(areaService.findArea(area.getId()));
            area.setId(areaDTOtoBeUpdated.get().getId());
            map.put("area: "+area.getId(), area);
        }catch(Exception e){
//            AreaDTO areaDTO = areaService.addArea(area);
//            map.put("area: "+areaDTO.getId(), areaDTO);
        }
        AreaDTO areaDTO = areaService.addArea(area);
        map.put("area: "+areaDTO.getId(), areaDTO);
//        !areaDTOtoBeUpdated.ifPresent(() -> {
//            area.setId(areaDTOtoBeUpdated.get().getId());
//        });
        // use update method
//        areaDTOtoBeUpdated.ifPresent(areaDTO -> area.setId(areaDTO.getId()));
//        map.put("area: "+area.getId(), area);
//
//        if(areaDTOtoBeUpdated.isEmpty()){
//            AreaDTO areaDTO = areaService.addArea(area);
//            map.put("area: "+areaDTO.getId(), areaDTO);
//        }
//        map.put("area: "+areaDTO.getId(), areaDTO);
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

}
