package com.example.springboottesttask.repositories;

import com.example.springboottesttask.entities.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet,Long> {
}
