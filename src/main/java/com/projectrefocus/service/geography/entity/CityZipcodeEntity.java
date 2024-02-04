package com.projectrefocus.service.geography.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "city_zipcodes")
public class CityZipcodeEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private List<CityEntity> cities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "zipcode_id", referencedColumnName = "id")
    private List<ZipcodeEntity> zipcodes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CityEntity> getCities() {
        return cities;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }

    public List<ZipcodeEntity> getZipcodes() {
        return zipcodes;
    }

    public void setZipcodes(List<ZipcodeEntity> zipcodes) {
        this.zipcodes = zipcodes;
    }
}