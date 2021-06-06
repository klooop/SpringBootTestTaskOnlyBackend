package com.example.springboottesttask.controllers.dto;

import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PlanetResponseDTO {
    private Long planetId;
    private String name;
    private Boolean isOwned;
    private Long lordId;

    public PlanetResponseDTO(Planet planet){
        this.planetId=planet.getPlanetId();
        this.name= planet.getName();
        this.isOwned=planet.getIsOwned();
        this.lordId=planet.getLordId();
    }
}
