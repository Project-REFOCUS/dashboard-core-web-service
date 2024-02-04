package com.projectrefocus.service.geography.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "zipcode")
public class ZipcodeEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Fetch(FetchMode.JOIN)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "city_zipcodes", inverseJoinColumns = @JoinColumn(name = "city_id"), joinColumns = @JoinColumn(name = "zipcode_id"))
    private List<CityEntity> cities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }

    public List<CityEntity> getCities() {
        return cities;
    }
}