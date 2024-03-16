package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.geography.entity.CityEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "fatal_police_shootings")
public class PoliceShootingEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "name")
    private String name;

    @Column(name = "armed")
    private String armed;

    @Column(name = "age")
    private Byte age;

    @Column(name = "gender")
    private Byte gender;

    @Column(name = "mental")
    private Boolean mental;

    @Column(name = "threat_level")
    private String threatLevel;

    @Column(name = "body_camera")
    private Boolean bodyCamera;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArmed() {
        return armed;
    }

    public void setArmed(String armed) {
        this.armed = armed;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Boolean getMental() {
        return mental;
    }

    public void setMental(Boolean mental) {
        this.mental = mental;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public Boolean getBodyCamera() {
        return bodyCamera;
    }

    public void setBodyCamera(Boolean bodyCamera) {
        this.bodyCamera = bodyCamera;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public CalendarDateEntity getDate() {
        return date;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }
}