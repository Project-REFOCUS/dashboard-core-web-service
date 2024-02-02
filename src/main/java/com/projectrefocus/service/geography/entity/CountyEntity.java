package com.projectrefocus.service.geography.entity;

import com.projectrefocus.service.geography.dto.CountyDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

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

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

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

    public void setState(StateEntity state) {
        this.state = state;
    }

    public StateEntity getState() {
        return state;
    }

    public CountyDto toDto() {
        CountyDto dto = new CountyDto();
        dto.setId(id.toString());
        dto.setName(name);

        return dto;
    }
}
