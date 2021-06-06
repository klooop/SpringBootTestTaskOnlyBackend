package com.example.springboottesttask.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "planets")
public class Planet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long planetId;

    @Column(name = "name")
    private String name;

    @Column(name="isowned")
    private Boolean isOwned;

    @Column(name="lord_id",insertable = false, updatable = false)
    private Long lordId;

    @ManyToOne
    @JoinColumn(name = "lord_id")
    private Lord lord;

    public Planet(String name) {
        this.name = name;
        this.isOwned = false;
    }

    public Planet(Long planetId, String name, Boolean isOwned, Long lordId) {
        this.planetId = planetId;
        this.name = name;
        this.isOwned = isOwned;
        this.lordId = lordId;
    }
}
