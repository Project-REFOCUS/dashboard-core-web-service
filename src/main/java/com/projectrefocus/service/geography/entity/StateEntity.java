package com.projectrefocus.service.geography.entity;

import com.projectrefocus.service.geography.dto.StateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "state")
public class StateEntity {

    @Id
    @Column(name = "id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public StateDto toDto() {
        StateDto dto = new StateDto();
        dto.setId(id);
        dto.setName(name);
        dto.setShortName(shortName);

        return dto;
    }
}
