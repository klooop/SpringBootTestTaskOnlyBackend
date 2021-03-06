package com.example.springboottesttask.controllers;

import com.example.springboottesttask.controllers.dto.*;
import com.example.springboottesttask.entities.Lord;
import com.example.springboottesttask.entities.Planet;
import com.example.springboottesttask.repositories.LordRepository;
import com.example.springboottesttask.repositories.PlanetRepository;
import com.example.springboottesttask.services.LordService;
import com.example.springboottesttask.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

//import org.junit.jupiter.api.Assertions;


@ContextConfiguration(classes = {MainController.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)

class MainControllerTest {

    @Autowired
    private MainController mainController;


    @MockBean
    private PlanetService planetService;

    @MockBean
    private LordService lordService;

    private Planet getPlanet(Lord lord) {
        Planet planet = new Planet();
        planet.setLord(lord);
        planet.setPlanetId(123L);
        planet.setLordId(123L);
        planet.setName("Name");
        planet.setIsOwned(true);
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
    public void testCreatePlanet() throws Exception {
        when(this.planetService.createPlanet(anyString())).thenReturn(1L);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/createPlanet")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new PlanetRequestDTO()));
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("0")));
    }

    @Test
    public void testGetPlanetById() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord);
        PlanetRepository planetRepository = mock(PlanetRepository.class);
        when(planetRepository.findById(any())).thenReturn(Optional.of(planet));
        PlanetService planetService = new PlanetService(planetRepository, mock(LordRepository.class));
        MainController mainController = new MainController(planetService,
                new LordService(mock(LordRepository.class), mock(PlanetRepository.class)));
        PlanetResponseDTO actualPlanetById = mainController.getPlanetById(123L);
        assertTrue(actualPlanetById.getIsOwned());
        assertEquals(123L, actualPlanetById.getPlanetId().longValue());
        assertEquals("Name", actualPlanetById.getName());
        assertEquals(123L, actualPlanetById.getLordId().longValue());
        verify(planetRepository).findById(any());
        assertTrue(mainController.getLordsWithoutPlanets().isEmpty());
    }



    @Test
    public void testCreateLord() {
        Lord lord = getLord();
        LordRepository lordRepository = mock(LordRepository.class);
        when(lordRepository.save(any())).thenReturn(lord);
        LordService lordService = new LordService(lordRepository, mock(PlanetRepository.class));
        MainController mainController = new MainController(
                new PlanetService(mock(PlanetRepository.class), mock(LordRepository.class)), lordService);
        assertEquals(123L, mainController.createLord(new LordRequestDTO("Name", 1)).longValue());
        verify(lordRepository).save(any());
        assertTrue(mainController.getLordsWithoutPlanets().isEmpty());
    }

    @Test
    public void testGetLordById() {
        Lord lord = getLord();
        LordRepository lordRepository = mock(LordRepository.class);
        when(lordRepository.findById(any())).thenReturn(Optional.of(lord));
        LordService lordService = new LordService(lordRepository, mock(PlanetRepository.class));
        MainController mainController = new MainController(
                new PlanetService(mock(PlanetRepository.class), mock(LordRepository.class)), lordService);
        LordResponseDTO actualLordById = mainController.getLordById(123L);
        assertEquals(1, actualLordById.getAge());
        assertTrue(actualLordById.getPlanetList().isEmpty());
        assertEquals("Name", actualLordById.getName());
        assertEquals(123L, actualLordById.getLordId().longValue());
        verify(lordRepository).findById(any());
        assertTrue(mainController.getLordsWithoutPlanets().isEmpty());
    }

    @Test
    public void testDeletePlanetById() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord);
        Optional<Planet> ofResult = Optional.of(planet);
        PlanetRepository planetRepository = mock(PlanetRepository.class);
        doNothing().when(planetRepository).deleteById(any());
        when(planetRepository.findById(any())).thenReturn(ofResult);
        PlanetService planetService = new PlanetService(planetRepository, mock(LordRepository.class));
        MainController mainController = new MainController(planetService,
                new LordService(mock(LordRepository.class), mock(PlanetRepository.class)));
        assertEquals(
                "Successfully destroyed Planet(planetId=123, name=Name, isOwned=true, lordId=123, lord=Lord(lordId=123,"
                        + " name=Name, age=1, planetList=[]))",
                mainController.deletePlanetById(123L));
        verify(planetRepository).deleteById(any());
        verify(planetRepository).findById(any());
        assertTrue(mainController.getLordsWithoutPlanets().isEmpty());
    }



    @Test
    public void testAppointLordToRuleThePlanet2() {
        Lord lord = getLord();

        Planet planet = getPlanet(lord);
        PlanetRepository planetRepository = mock(PlanetRepository.class);
        when(planetRepository.findById(any())).thenReturn(Optional.of(planet));

        Lord lord1 = getLord();
        LordRepository lordRepository = mock(LordRepository.class);
        when(lordRepository.findById(any())).thenReturn(Optional.of(lord1));
        PlanetService planetService = new PlanetService(planetRepository, lordRepository);

        Lord lord2 = getLord();
        LordRepository lordRepository1 = mock(LordRepository.class);
        when(lordRepository1.findById(any())).thenReturn(Optional.of(lord2));

        Lord lord3 = getLord();

        Planet planet1 = getPlanet(lord3);
        PlanetRepository planetRepository1 = mock(PlanetRepository.class);
        when(planetRepository1.findById(any())).thenReturn(Optional.of(planet1));
        MainController mainController = new MainController(planetService,
                new LordService(lordRepository1, planetRepository1));
        LordResponseDTO actualAppointLordToRuleThePlanetResult = mainController
                .appointLordToRuleThePlanet(new LordAndPlanetRequestDTO(123L, 123L));
        assertEquals(1, actualAppointLordToRuleThePlanetResult.getAge());
        assertTrue(actualAppointLordToRuleThePlanetResult.getPlanetList().isEmpty());
        assertEquals("Name", actualAppointLordToRuleThePlanetResult.getName());
        assertEquals(123L, actualAppointLordToRuleThePlanetResult.getLordId().longValue());
        verify(planetRepository).findById(any());
        verify(lordRepository).findById(any());
        verify(lordRepository1).findById(any());
        verify(planetRepository1).findById(any());
        assertTrue(mainController.getLordsWithoutPlanets().isEmpty());
    }



    @Test
    public void testGetLordsWithoutPlanets() throws Exception {
        when(this.lordService.getLordsWithoutPlanets()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getLordsWithoutPlanets");
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetLordsWithoutPlanets2() throws Exception {
        when(this.lordService.getLordsWithoutPlanets()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/getLordsWithoutPlanets");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }


    @Test
    public void testGetLordsOrderedByAge() throws Exception {
        when(this.lordService.getLordsByAge()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getLordsByAge");
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }

    @Test
    public void testGetLordsOrderedByAge2() throws Exception {
        when(this.lordService.getLordsByAge()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/getLordsByAge");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.mainController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("[]")));
    }
}