package com.projectrefocus.service.geography.dto;

public class GeographyDto {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeographyDto geographyDto) {
            return id.equals(geographyDto.getId()) && name.equals(geographyDto.getName());
        }
        return false;
    }
}
