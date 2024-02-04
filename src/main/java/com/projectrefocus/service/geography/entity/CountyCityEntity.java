package com.projectrefocus.service.geography.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "county_cities")
public class CountyCityEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id", referencedColumnName = "id")
    private List<CountyEntity> counties;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private List<CityEntity> cities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CountyEntity> getCounties() {
        return counties;
    }

    public void setCounties(List<CountyEntity> counties) {
        this.counties = counties;
    }

    public List<CityEntity> getCities() {
        return cities;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }
}