package com.example.springboottesttask.services;

import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import com.example.springboottesttask.exception.LordNotFoundException;
import com.example.springboottesttask.exception.PlanetNotFoundException;
import com.example.springboottesttask.repositories.LordRepository;
import com.example.springboottesttask.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final LordRepository lordRepository;
    @Autowired
    public PlanetService(PlanetRepository planetRepository, LordRepository lordRepository) {
        this.planetRepository = planetRepository;
        this.lordRepository = lordRepository;
    }

    public Long createPlanet(String name) {
        Planet planet = new Planet(name);
        return planetRepository.save(planet).getPlanetId();
    }

    public String setLordToPlanet(long lordId, long planetId) {
        Planet planet = findPlanetById(planetId);
        Lord lord = lordRepository.findById(lordId)
                .orElseThrow(()->new LordNotFoundException("Unable to find lord with id: "+lordId));
        if (!planet.getIsOwned()){
        planet.setIsOwned(true);
        planet.setLord(lord);
        planet.setLordId(lord.getLordId());
        planetRepository.save(planet);
        } else System.out.println("This planet has lord");
        return "set successfully";
    }

    public Planet deletePlanet(long planetId) {
        Planet planet = findPlanetById(planetId);
        planetRepository.deleteById(planetId);
        return planet;
    }

    public Planet findPlanetById(long planetId) {
        return planetRepository.findById(planetId)
        .orElseThrow(()->new PlanetNotFoundException("Unable to find planet with id: "+ planetId));
    }

}
