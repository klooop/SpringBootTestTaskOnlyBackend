package com.example.springboottesttask.services;

import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import com.example.springboottesttask.exception.LordNotFoundException;
import com.example.springboottesttask.exception.PlanetNotFoundException;
import com.example.springboottesttask.repositories.LordRepository;
import com.example.springboottesttask.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LordService {

    private final LordRepository lordRepository;
    private final PlanetRepository planetRepository;
    @Autowired
    public LordService(LordRepository lordRepository, PlanetRepository planetRepository) {
        this.lordRepository = lordRepository;
        this.planetRepository = planetRepository;
    }

    public Lord findLordById(Long lordId) {
        return lordRepository.findById(lordId)
                .orElseThrow(()->new LordNotFoundException("Unable to find lord with id: "+lordId));
    }

    public Long createLord(String name, int age) {
        Lord lord = new Lord(name, age);
        return lordRepository.save(lord).getLordId();
    }

    public Lord setPlanetToLord(long lordId, long planetId) {
        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(()->new PlanetNotFoundException("Unable to find planet with id: "+planetId));
        Lord lord = findLordById(lordId);
        if (!planet.getIsOwned()){
//            lord.setPlanetList(lord.getPlanetList().add(planet));
            Collection<Planet> planetList = lord.getPlanetList();
            planetList.add(planet);
            lord.setPlanetList(planetList);
            lordRepository.save(lord);
        }
        return lord;
    }

    public List<Lord> getLordsWithoutPlanets() {
        return lordRepository.getLordsByPlanetList();
    }
    public List<Lord> getLordsByAge() {
        return lordRepository.getLordsByAge().stream().limit(10).collect(Collectors.toList());
    }


}
