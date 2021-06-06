package com.example.springboottesttask.services;

import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import com.example.springboottesttask.exception.LordNotFoundException;
import com.example.springboottesttask.exception.PlanetNotFoundException;
import com.example.springboottesttask.repositories.LordRepository;
import com.example.springboottesttask.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = {LordService.class})
@ExtendWith(SpringExtension.class)
class LordServiceTest {

    @MockBean
    private LordRepository lordRepository;

    @Autowired
    private LordService lordService;

    @MockBean
    private PlanetRepository planetRepository;

    @Test
    public void testFindLordById() {
        Lord lord = getLord();
        Optional<Lord> ofResult = Optional.<Lord>of(lord);
        when(this.lordRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(lord, this.lordService.findLordById(123L));
        verify(this.lordRepository).findById((Long) any());
        assertTrue(this.lordService.getLordsWithoutPlanets().isEmpty());
    }

    private Lord getLord() {
        Lord lord = new Lord();
        lord.setPlanetList(new ArrayList<Planet>());
        lord.setName("Name");
        lord.setLordId(123L);
        lord.setAge(1);
        return lord;
    }

    @Test
    public void testFindLordById2() {
        when(this.lordRepository.findById((Long) any())).thenReturn(Optional.<Lord>empty());
        assertThrows(LordNotFoundException.class, () -> this.lordService.findLordById(123L));
        verify(this.lordRepository).findById((Long) any());
    }

    @Test
    public void testCreateLord() {
        Lord lord = getLord();
        when(this.lordRepository.save((Lord) any())).thenReturn(lord);
        assertEquals(123L, this.lordService.createLord("Name", 1).longValue());
        verify(this.lordRepository).save((Lord) any());
        assertTrue(this.lordService.getLordsWithoutPlanets().isEmpty());
    }

    @Test
    public void testSetPlanetToLord() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.<Planet>of(planet);
        when(this.planetRepository.findById((Long) any())).thenReturn(ofResult);

        Lord lord1 = getLord();
        Optional<Lord> ofResult1 = Optional.<Lord>of(lord1);
        when(this.lordRepository.findById((Long) any())).thenReturn(ofResult1);
        assertSame(lord1, this.lordService.setPlanetToLord(123L, 123L));
        verify(this.planetRepository).findById((Long) any());
        verify(this.lordRepository).findById((Long) any());
        assertTrue(this.lordService.getLordsWithoutPlanets().isEmpty());
    }

    private Planet getPlanet(Lord lord, boolean b) {
        Planet planet = new Planet();
        planet.setLord(lord);
        planet.setPlanetId(123L);
        planet.setLordId(123L);
        planet.setName("Name");
        planet.setIsOwned(b);
        return planet;
    }

    @Test
    public void testSetPlanetToLord2() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.<Planet>of(planet);
        when(this.planetRepository.findById((Long) any())).thenReturn(ofResult);

        Lord lord1 = getLord();
        Optional<Lord> ofResult1 = Optional.<Lord>of(lord1);

        Lord lord2 = getLord();
        when(this.lordRepository.save((Lord) any())).thenReturn(lord2);
        when(this.lordRepository.findById((Long) any())).thenReturn(ofResult1);
        Lord actualSetPlanetToLordResult = this.lordService.setPlanetToLord(123L, 123L);
        assertSame(lord1, actualSetPlanetToLordResult);
        assertEquals(
                "Lord(lordId=123, name=Name, age=1, planetList=[Planet(planetId=123, name=Name, isOwned=false, lordId=123,"
                        + " lord=Lord(lordId=123, name=Name, age=1, planetList=[]))])",
                actualSetPlanetToLordResult.toString());
        assertEquals(1, actualSetPlanetToLordResult.getPlanetList().size());
        verify(this.planetRepository).findById((Long) any());
        verify(this.lordRepository).findById((Long) any());
        verify(this.lordRepository).save((Lord) any());
        assertTrue(this.lordService.getLordsWithoutPlanets().isEmpty());
    }

    @Test
    public void testSetPlanetToLord3() {
        when(this.planetRepository.findById((Long) any())).thenReturn(Optional.<Planet>empty());

        Lord lord = getLord();
        Optional<Lord> ofResult = Optional.<Lord>of(lord);

        Lord lord1 = getLord();
        when(this.lordRepository.save((Lord) any())).thenReturn(lord1);
        when(this.lordRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(PlanetNotFoundException.class, () -> this.lordService.setPlanetToLord(123L, 123L));
        verify(this.planetRepository).findById((Long) any());
    }

    @Test
    public void testSetPlanetToLord4() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.<Planet>of(planet);
        when(this.planetRepository.findById((Long) any())).thenReturn(ofResult);

        Lord lord1 = getLord();
        when(this.lordRepository.save((Lord) any())).thenReturn(lord1);
        when(this.lordRepository.findById((Long) any())).thenReturn(Optional.<Lord>empty());
        assertThrows(LordNotFoundException.class, () -> this.lordService.setPlanetToLord(123L, 123L));
        verify(this.planetRepository).findById((Long) any());
        verify(this.lordRepository).findById((Long) any());
    }

    @Test
    public void testGetLordsWithoutPlanets() {
        ArrayList<Lord> lordList = new ArrayList<Lord>();
        when(this.lordRepository.getLordsByPlanetList()).thenReturn(lordList);
        List<Lord> actualLordsWithoutPlanets = this.lordService.getLordsWithoutPlanets();
        assertSame(lordList, actualLordsWithoutPlanets);
        assertTrue(actualLordsWithoutPlanets.isEmpty());
        verify(this.lordRepository).getLordsByPlanetList();
        assertTrue(this.lordService.getLordsByAge().isEmpty());
    }

    @Test
    public void testGetLordsByAge() {
        when(this.lordRepository.getLordsByAge()).thenReturn(new ArrayList<Lord>());
        assertTrue(this.lordService.getLordsByAge().isEmpty());
        verify(this.lordRepository).getLordsByAge();
        assertTrue(this.lordService.getLordsWithoutPlanets().isEmpty());
    }


}