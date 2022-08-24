package com.projectrefocus.service.geography.entity;

import com.projectrefocus.service.geography.dto.CountyDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "county")
public class CountyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @Column(name = "fips")
    private String fips;

    public void setId(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public String getFips() {
        return fips;
    }

    public CountyDto toDto() {
        CountyDto dto = new CountyDto();
        dto.setId(id);
        dto.setName(name);
        dto.setFips(fips);

        return dto;
    }
}
