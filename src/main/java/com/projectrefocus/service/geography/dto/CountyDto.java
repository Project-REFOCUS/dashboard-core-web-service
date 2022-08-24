package com.projectrefocus.service.geography.dto;

public class CountyDto {

    private Short id;
    private String name;
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
}
