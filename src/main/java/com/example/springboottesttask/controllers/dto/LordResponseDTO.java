package com.example.springboottesttask.controllers.dto;

import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LordResponseDTO {
    private Long lordId;
    private String name;
    private int age;
    private Collection<PlanetResponseDTO> planetList;

    public LordResponseDTO(Lord lord) {
        this.lordId = lord.getLordId();
        this.name = lord.getName();
        this.age = lord.getAge();
        this.planetList=lord.getPlanetList().stream().map(PlanetResponseDTO::new).collect(Collectors.toList());
    }
}
