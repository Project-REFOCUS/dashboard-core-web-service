package com.projectrefocus.service.ethnicity.entity;

import com.projectrefocus.service.ethnicity.dto.RaceEthnicityDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "race_ethnicity")
public class RaceEthnicityEntity {

    @Id
    @Column(name = "id")
    private Byte id;

    @Column(name = "name")
    private String name;

    public void setId(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RaceEthnicityDto toDto() {
        RaceEthnicityDto dto = new RaceEthnicityDto();
        dto.setId(id);
        dto.setName(name);

        return dto;
    }
}
