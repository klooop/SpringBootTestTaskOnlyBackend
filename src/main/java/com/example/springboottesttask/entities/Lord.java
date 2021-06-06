package com.example.springboottesttask.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lords")
public class Lord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long lordId;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "lord")
    private Collection<Planet> planetList;

    public Lord(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Lord(Long lordId, String name, int age) {
        this.lordId = lordId;
        this.name = name;
        this.age = age;
    }

    public Lord(String name, int age, Collection<Planet> planetList) {
        this.name = name;
        this.age = age;
        this.planetList = planetList;
    }
}
