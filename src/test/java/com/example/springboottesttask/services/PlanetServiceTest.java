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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PlanetService.class})
@ExtendWith(SpringExtension.class)
public class PlanetServiceTest {
    @MockBean
    private LordRepository lordRepository;

    @MockBean
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetService planetService;

    @Test
    public void testCreatePlanet() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        when(this.planetRepository.save(any())).thenReturn(planet);
        assertEquals(123L, this.planetService.createPlanet("Name").longValue());
        verify(this.planetRepository).save(any());
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

    private Lord getLord() {
        Lord lord = new Lord();
        lord.setPlanetList(new ArrayList<>());
        lord.setName("Name");
        lord.setLordId(123L);
        lord.setAge(1);
        return lord;
    }

    @Test
    public void testCreatePlanet2() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        when(this.planetRepository.save(any())).thenReturn(planet);
        assertEquals(123L, this.planetService.createPlanet("Name").longValue());
        verify(this.planetRepository).save(any());
    }

    @Test
    public void testSetLordToPlanet() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);

        Lord lord1 = getLord();
        Optional<Lord> ofResult1 = Optional.of(lord1);
        when(this.lordRepository.findById(any())).thenReturn(ofResult1);
        assertEquals("set successfully", this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet2() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.of(planet);

        Lord lord1 = getLord();

        Planet planet1 = getPlanet(lord1, true);
        when(this.planetRepository.save(any())).thenReturn(planet1);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);

        Lord lord2 = getLord();
        Optional<Lord> ofResult1 = Optional.of(lord2);
        when(this.lordRepository.findById(any())).thenReturn(ofResult1);
        assertEquals("set successfully", this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.planetRepository).save(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet3() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        when(this.planetRepository.save(any())).thenReturn(planet);
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());

        Lord lord1 = getLord();
        Optional<Lord> ofResult = Optional.of(lord1);
        when(this.lordRepository.findById(any())).thenReturn(ofResult);
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet4() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.of(planet);

        Lord lord1 = getLord();

        Planet planet1 = getPlanet(lord1, true);
        when(this.planetRepository.save(any())).thenReturn(planet1);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        when(this.lordRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class, () -> this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet5() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);

        Lord lord1 = getLord();
        Optional<Lord> ofResult1 = Optional.of(lord1);
        when(this.lordRepository.findById(any())).thenReturn(ofResult1);
        assertEquals("set successfully", this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet6() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.of(planet);

        Lord lord1 = getLord();

        Planet planet1 = getPlanet(lord1, true);
        when(this.planetRepository.save(any())).thenReturn(planet1);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);

        Lord lord2 = getLord();
        Optional<Lord> ofResult1 = Optional.of(lord2);
        when(this.lordRepository.findById(any())).thenReturn(ofResult1);
        assertEquals("set successfully", this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.planetRepository).save(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet7() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        when(this.planetRepository.save(any())).thenReturn(planet);
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());

        Lord lord1 = getLord();
        Optional<Lord> ofResult = Optional.of(lord1);
        when(this.lordRepository.findById(any())).thenReturn(ofResult);
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testSetLordToPlanet8() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, false);
        Optional<Planet> ofResult = Optional.of(planet);

        Lord lord1 = getLord();

        Planet planet1 = getPlanet(lord1, true);
        when(this.planetRepository.save(any())).thenReturn(planet1);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        when(this.lordRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(LordNotFoundException.class, () -> this.planetService.setLordToPlanet(123L, 123L));
        verify(this.planetRepository).findById(any());
        verify(this.lordRepository).findById(any());
    }

    @Test
    public void testDeletePlanet() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        doNothing().when(this.planetRepository).deleteById(any());
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        assertSame(planet, this.planetService.deletePlanet(123L));
        verify(this.planetRepository).deleteById(any());
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testDeletePlanet2() {
        doNothing().when(this.planetRepository).deleteById(any());
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.deletePlanet(123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testDeletePlanet3() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        doNothing().when(this.planetRepository).deleteById(any());
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        assertSame(planet, this.planetService.deletePlanet(123L));
        verify(this.planetRepository).deleteById(any());
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testDeletePlanet4() {
        doNothing().when(this.planetRepository).deleteById(any());
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.deletePlanet(123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testFindPlanetById() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        assertSame(planet, this.planetService.findPlanetById(123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testFindPlanetById2() {
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.findPlanetById(123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testFindPlanetById3() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord, true);
        Optional<Planet> ofResult = Optional.of(planet);
        when(this.planetRepository.findById(any())).thenReturn(ofResult);
        assertSame(planet, this.planetService.findPlanetById(123L));
        verify(this.planetRepository).findById(any());
    }

    @Test
    public void testFindPlanetById4() {
        when(this.planetRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(PlanetNotFoundException.class, () -> this.planetService.findPlanetById(123L));
        verify(this.planetRepository).findById(any());
    }
}

