package com.example.springboottesttask.repositories;

import com.example.springboottesttask.entities.Lord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LordRepository  extends CrudRepository<Lord, Long>, JpaSpecificationExecutor<Lord> {

//    @Query("SELECT l FROM Lord l WHERE NOT EXISTS(select p from Planet p where p.lordId = l.lordId )")
    @Query("SELECT l FROM Lord l WHERE l.planetList is empty")
    List<Lord> getLordsByPlanetList();

    @Query("Select l From Lord l order by l.age ")
    List<Lord> getLordsByAge();
}
