package com.projectrefocus.service.geography.entity;

import com.projectrefocus.service.geography.dto.CityDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "city")
public class CityEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name")
    private String name;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "county_cities", inverseJoinColumns = @JoinColumn(name = "county_id"), joinColumns = @JoinColumn(name = "city_id"))
    private List<CountyEntity> counties;

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

    public void setCounties(List<CountyEntity> counties) {
        this.counties = counties;
    }

    public List<CountyEntity> getCounties() {
        return counties;
    }

    public CityDto toDto() {
        CityDto dto = new CityDto();
        dto.setId(id.toString());
        dto.setName(name);

        return dto;
    }
}
