package com.projectrefocus.service.geography.entity;

import com.projectrefocus.service.geography.dto.CityDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private CountyEntity county;

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

    public void setCounty(CountyEntity county) {
        this.county = county;
    }

    public CountyEntity getCounty() {
        return county;
    }

    public CityDto toDto() {
        CityDto dto = new CityDto();
        dto.setId(id.toString());
        dto.setName(name);

        return dto;
    }
}
