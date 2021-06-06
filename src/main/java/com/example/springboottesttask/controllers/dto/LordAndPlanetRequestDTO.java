package com.example.springboottesttask.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LordAndPlanetRequestDTO {
    private Long lordId;
    private Long planetId;
}
