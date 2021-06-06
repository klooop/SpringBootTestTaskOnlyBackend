package com.example.springboottesttask.controllers;

import com.example.springboottesttask.controllers.dto.*;
import com.example.springboottesttask.services.LordService;
import com.example.springboottesttask.services.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private final PlanetService planetService;
    private final LordService lordService;
    @Autowired
    public MainController(PlanetService planetService, LordService lordService) {
        this.planetService = planetService;
        this.lordService = lordService;
    }
    @PostMapping("/createPlanet")
    public Long createPlanet(@RequestBody PlanetRequestDTO planetRequestDTO) {
        return planetService.createPlanet(planetRequestDTO.getName());
    }

    @GetMapping("/getPlanet/{planetId}")
    public PlanetResponseDTO getPlanetById(@PathVariable("planetId") Long planetId) {
        return new PlanetResponseDTO(planetService.findPlanetById(planetId));
    }

    @PostMapping("/createLord")
    public Long createLord(@RequestBody LordRequestDTO lordRequestDTO) {
        return lordService.createLord(lordRequestDTO.getName(), lordRequestDTO.getAge());
    }

    @GetMapping("/getLord/{lordId}")
    public LordResponseDTO getLordById(@PathVariable("lordId") Long id) {
        return new LordResponseDTO(lordService.findLordById(id));
    }

    @DeleteMapping("/destroyPlanet/{planetId}")
    public String deletePlanetById(@PathVariable("planetId") Long planetId) {
        return ("Successfully destroyed "+planetService.deletePlanet(planetId).toString());
    }

    @PutMapping("/appoint")
    public LordResponseDTO appointLordToRuleThePlanet(@RequestBody LordAndPlanetRequestDTO lordAndPlanetRequestDTO) {
        planetService.setLordToPlanet(lordAndPlanetRequestDTO.getLordId(),lordAndPlanetRequestDTO.getPlanetId());
        return new LordResponseDTO(lordService.setPlanetToLord(lordAndPlanetRequestDTO.getLordId(),lordAndPlanetRequestDTO.getPlanetId()));
    }
    @GetMapping("/getLordsWithoutPlanets")
    public List<LordResponseDTO> getLordsWithoutPlanets(){
        return lordService.getLordsWithoutPlanets().stream().map(LordResponseDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/getLordsByAge")
    public List<LordResponseDTO> getLordsOrderedByAge() {
        return lordService.getLordsByAge().stream().map(LordResponseDTO::new).collect(Collectors.toList());
    }



}
